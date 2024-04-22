package concertpro.network.rpc;

import concertpro.model.Order;
import concertpro.model.Spectacle;
import concertpro.model.User;
import concertpro.network.dto.DTOUtils;
import concertpro.network.dto.UserDTO;
import concertpro.network.rpc.request.Request;
import concertpro.network.rpc.request.RequestType;
import concertpro.network.rpc.response.Response;
import concertpro.network.rpc.response.ResponseType;
import concertpro.services.ChatException;
import concertpro.services.IChatObserver;
import concertpro.services.IChatService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServicesProxy implements IChatService {

    private String host;
    private int port;

    private IChatObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ChatServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingQueue<>();
    }

    @Override
    public void login(User user, IChatObserver client) throws ChatException {
        initializeConnection();
        UserDTO userDTO = DTOUtils.getDTO(user);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(userDTO).build();
        sendRequest(request);
        Response response = readResponse();
        if (response.type() == ResponseType.OK) {
            this.client = client;
            return;
        }
        if (response.type() == ResponseType.ERROR) {
            String error = response.data().toString();
            closeConnection();
            throw new ChatException(error);
        }
    }

    @Override
    public void logout(User user, IChatObserver client) throws ChatException {
        UserDTO userDTO = DTOUtils.getDTO(user);
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(userDTO).build();
        sendRequest(request);
        Response response = readResponse();

        if(response.type() == ResponseType.ERROR) {
            String responseError = response.data().toString();
            throw new ChatException(responseError);
        }
    }

    @Override
    public User[] getLoggedUsers(User user) throws ChatException {
        return new User[0];
    }

    @Override
    public Spectacle[] getAllSpectacles() throws ChatException {
        Request req=new Request.Builder().type(RequestType.GET_SPECTACLES).data(new Spectacle()).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
        Spectacle[] spectacles=(Spectacle[])response.data();
        return spectacles;
    }

    @Override
    public Spectacle[] getSpectaclesByDate(LocalDate date) throws ChatException {
        Request req = new Request.Builder().type(RequestType.GET_FILTERED_SPECTACLES).data(date).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
        Spectacle[] spectacles=(Spectacle[])response.data();
        return spectacles;
    }

    @Override
    public void addOrder(Order order) throws ChatException {
        Request req = new Request.Builder().type(RequestType.ADD_ORDER).data(order).build();
        sendRequest(req);
        Response response=readResponse();

        if(response.type() == ResponseType.ADDED_ORDER) {
            return;
        }

        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new ChatException(err);
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response readResponse() throws ChatException {
        Response response = null;

        try {
            response=qresponses.take();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void sendRequest(Request request) throws ChatException {
        try {
            output.writeObject(request);
            output.flush();
        }
        catch (IOException e) {
            throw new ChatException("Error sending object "+e);
        }
    }

    private void initializeConnection() throws ChatException {
        try {
            this.connection = new Socket(this.host, this.port);
            this.output = new ObjectOutputStream(this.connection.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(this.connection.getInputStream());
            this.finished = false;
            startReader();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void startReader() {
        Thread thread = new Thread(new ReaderThread());
        thread.start();
    }

    private boolean isUpdate(Response response) {
        return response.type()== ResponseType.FRIEND_LOGGED_OUT || response.type()== ResponseType.SPECTACLES_UPDATED;
    }

    private void handleUpdate(Response response) {
        if (response.type()== ResponseType.FRIEND_LOGGED_IN) {
            User user = DTOUtils.getFromDTO((UserDTO) response.data());
            System.out.println("User logged in " + user);

            try {
                client.userLoggedIn(user);
            }
            catch (ChatException exception) {
                exception.printStackTrace();
            }
        }
        if (response.type()== ResponseType.FRIEND_LOGGED_OUT) {
            User user = DTOUtils.getFromDTO((UserDTO) response.data());
            System.out.println("User logged out " + user);

            try {
                client.userLoggedOut(user);
            }
            catch (ChatException exception) {
                exception.printStackTrace();
            }
        }

        if(response.type() == ResponseType.SPECTACLES_UPDATED) {
            Spectacle[] spectacles = (Spectacle[]) response.data();
            System.out.println("Spectacles updated " + spectacles);

            try {
                client.updateSpectacles(spectacles);
            } catch (ChatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class ReaderThread implements Runnable {
        @Override
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    System.out.println("response received " + response);

                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {
                        try {
                            qresponses.put((Response) response);
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                catch (IOException | ClassNotFoundException exception) {
                    System.out.println("Reading error " + exception);
                }
            }
        }
    }
}
