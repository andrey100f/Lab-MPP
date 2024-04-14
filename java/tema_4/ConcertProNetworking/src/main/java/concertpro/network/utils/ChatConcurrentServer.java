package concertpro.network.utils;

import concertpro.network.rpc.ChatWorker;
import concertpro.services.IChatService;

import java.net.Socket;

public class ChatConcurrentServer extends AbstractConcurrentServer {

    private IChatService chatServer;

    public ChatConcurrentServer(int port, IChatService chatServer) {
        super(port);
        this.chatServer = chatServer;

        System.out.println("Chat- ChatConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ChatWorker worker = new ChatWorker(this.chatServer, client);
        Thread tw = new Thread(worker);
        return tw;
    }

    @Override
    public void stop() {
        System.out.println("Stopping services ...");
    }

}
