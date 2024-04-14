package concertpro.network.utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {

    private int port;
    private ServerSocket server = null;

    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() throws ServerException {
        try {
            this.server = new ServerSocket(this.port);

            while(true) {
                System.out.println("Waiting for clients ...");
                Socket client = this.server.accept();
                System.out.println("Client connected ...");
                this.processRequest(client);
            }
        }
        catch (IOException exception) {
            throw new ServerException("Starting server error ", exception);
        }
        finally {
            this.stop();
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop() throws ServerException {
        try {
            this.server.close();
        }
        catch (IOException exception) {
            throw new ServerException("Closing server error ", exception);
        }
    }

}
