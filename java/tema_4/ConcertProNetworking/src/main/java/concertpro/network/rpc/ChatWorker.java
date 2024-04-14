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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Arrays;

public class ChatWorker implements Runnable, IChatObserver {

    private IChatService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ChatWorker(IChatService server, Socket connection) {
        this.server = server;
        this.connection = connection;

        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userLoggedIn(User friend) throws ChatException {
        UserDTO userDTO = DTOUtils.getDTO(friend);
        Response response = new Response.Builder().type(ResponseType.FRIEND_LOGGED_IN).data(userDTO).build();
        System.out.println("Friend logged in " + friend);

        try {
            sendResponse(response);
        }
        catch (IOException exception) {
            throw new ChatException("Sending error: " + exception);
        }
    }

    @Override
    public void userLoggedOut(User friend) throws ChatException {
        UserDTO userDTO = DTOUtils.getDTO(friend);
        Response response = new Response.Builder().type(ResponseType.FRIEND_LOGGED_OUT).data(userDTO).build();
        System.out.println("Friend logged out " + friend);

        try {
            sendResponse(response);
        }
        catch (IOException exception) {
            throw new ChatException("Sending error: " + exception);
        }
    }

    @Override
    public void updateSpectacles(Spectacle[] spectacles) {
        Response response = new Response.Builder().type(ResponseType.SPECTACLES_UPDATED).data(spectacles).build();

        System.out.println("Spectacles: " + Arrays.toString(spectacles));

        try {
            sendResponse(response);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected) {
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);

                if (response != null) {
                    sendResponse(response);
                }
            }
            catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }


            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        Response response = null;

        String handlerName = "handle" + request.type();
        System.out.println("Handler name: " + handlerName);

        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response) method.invoke(this, request);
        }
        catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        return response;
    }

    private Response handleLOGIN(Request request) {
        System.out.println("Login request ..." + request.type());
        UserDTO userDTO = (UserDTO) request.data();
        User user = DTOUtils.getFromDTO(userDTO);

        try {
            server.login(user, this);
            return okResponse;
        }
        catch (ChatException exception) {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(exception.getMessage()).build();
        }
    }

    private Response handleGET_SPECTACLES(Request request) {
        System.out.println("Get Spectacles Request ...");

        try {
            Spectacle[] spectacles = server.getAllSpectacles();

            return new Response.Builder().type(ResponseType.GET_SPECTACLES).data(spectacles).build();
        }
        catch (ChatException exception) {
            return new Response.Builder().type(ResponseType.ERROR).data(exception.getMessage()).build();
        }
    }

    private Response handleADD_ORDER(Request request) {
        System.out.println("Add Order Request ...");

        try {
            this.server.addOrder((Order) request.data());
            return new Response.Builder().type(ResponseType.ADDED_ORDER).build();
        }
        catch (ChatException exception) {
            return new Response.Builder().type(ResponseType.ERROR).data(exception.getMessage()).build();
        }
    }

    private Response handleGET_FILTERED_SPECTACLES(Request request) {
        System.out.println("Get Spectacles Request ...");

        try {
//            User[] friends = server.getLoggedUsers(user);
            Spectacle[] spectacles = server.getSpectaclesByDate((LocalDate) request.data());
//            UserDTO[] friendDTOs = DTOUtils.getDTO(friends);

            return new Response.Builder().type(ResponseType.OK).data(spectacles).build();
        }
        catch (ChatException exception) {
            return new Response.Builder().type(ResponseType.ERROR).data(exception.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        UserDTO udto=(UserDTO)request.data();
        User user=DTOUtils.getFromDTO(udto);
        try {
            server.logout(user, this);
            connected=false;
            return okResponse;

        } catch (ChatException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("Sending response: " + response);

        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

}
