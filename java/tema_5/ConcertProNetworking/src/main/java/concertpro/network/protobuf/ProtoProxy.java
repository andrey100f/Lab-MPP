package concertpro.network.protobuf;

import concertpro.model.Order;
import concertpro.model.Spectacle;
import concertpro.model.User;
import concertpro.services.ChatException;
import concertpro.services.IChatObserver;
import concertpro.services.IChatService;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProxy implements IChatService {
    private String host;
    private int port;

    private IChatObserver client;
    private InputStream input;
    private OutputStream output;
    private Socket connection;
    private BlockingQueue<ConcertProProtocol.Response> qresponses;
    private volatile boolean finished;

    public ProtoProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingQueue<ConcertProProtocol.Response>();
    }

    @Override
    public void login(User user, IChatObserver client) throws ChatException {
        this.initializeConnection();
        System.out.println("Login request ...");
        this.sendRequest(ProtoUtils.createLoginRequest(user));
        ConcertProProtocol.Response response = this.readResponse();
        if (response.getType() == ConcertProProtocol.Response.Type.OK) {
            this.client = client;
            return;
        }
        if (response.getType() == ConcertProProtocol.Response.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            this.closeConnection();
            throw new ChatException(errorText);
        }
    }

    @Override
    public void logout(User user, IChatObserver client) throws ChatException {

    }

    @Override
    public User[] getLoggedUsers(User user) throws ChatException {
        return new User[0];
    }

    @Override
    public Spectacle[] getAllSpectacles() throws ChatException {
        this.sendRequest(ProtoUtils.createGetSpectaclesRequest());
        ConcertProProtocol.Response response = this.readResponse();
        if (response.getType() == ConcertProProtocol.Response.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            throw new ChatException(errorText);
        }
        Spectacle[] spectacles = ProtoUtils.getSpectaclesResponse(response);
        return spectacles;
    }

    @Override
    public Spectacle[] getSpectaclesByDate(LocalDate date) throws ChatException {
        this.sendRequest(ProtoUtils.createGetSpectaclesByDateRequest(date));
        ConcertProProtocol.Response response = this.readResponse();
        if (response.getType() == ConcertProProtocol.Response.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            throw new ChatException(errorText);
        }
        Spectacle[] spectacles = ProtoUtils.getSpectaclesResponse(response);
        return spectacles;
    }

    @Override
    public void addOrder(Order order) throws ChatException {
        this.sendRequest(ProtoUtils.createAddOrderRequest(order));
        ConcertProProtocol.Response response = this.readResponse();
        if (response.getType() == ConcertProProtocol.Response.Type.ERROR) {
            String errorText = ProtoUtils.getError(response);
            throw new ChatException(errorText);
        }
    }

    private void closeConnection() {
        this.finished = true;
        try {
            this.input.close();
            this.output.close();
            this.connection.close();
            this.client = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConcertProProtocol.Response readResponse() {
        ConcertProProtocol.Response response = null;

        try {
            response = this.qresponses.take();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void sendRequest(ConcertProProtocol.Request request) {
        try {
            System.out.println("Sending request ... " + request);
            request.writeDelimitedTo(this.output);
            this.output.flush();
            System.out.println("Request sent.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeConnection() {
        try {
            this.connection = new Socket(this.host, this.port);
            this.input = this.connection.getInputStream();
            this.output = this.connection.getOutputStream();
            this.finished = false;
            this.startReader();
        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void startReader() {
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private void handleUpdate(ConcertProProtocol.Response updateResponse) {

    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    ConcertProProtocol.Response response = ConcertProProtocol.Response.parseDelimitedFrom(input);
                    System.out.println("Response received: " + response);

                    if(isUpdateResponse(response.getType())) {
                        handleUpdate(response);
                    }
                    else {
                        try {
                            qresponses.put(response);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isUpdateResponse(ConcertProProtocol.Response.Type type) {
        return type == ConcertProProtocol.Response.Type.SPECTACLES_UPDATED;
    }
}
