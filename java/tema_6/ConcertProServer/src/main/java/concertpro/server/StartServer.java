package concertpro.server;

import concertpro.model.Order;
import concertpro.network.utils.AbstractServer;
import concertpro.network.utils.ChatConcurrentServer;
import concertpro.network.utils.ServerException;
import concertpro.persistance.repository.order_repository.IOrderRepository;
import concertpro.persistance.repository.order_repository.OrderDbRepository;
import concertpro.persistance.repository.order_repository.OrderHibernateRepository;
import concertpro.persistance.repository.spectacle_repository.ISpectacleRepository;
import concertpro.persistance.repository.spectacle_repository.SpectacleDbRepository;
import concertpro.persistance.repository.spectacle_repository.SpectacleHibernateRepository;
import concertpro.persistance.repository.user_repository.IUserRepository;
import concertpro.persistance.repository.user_repository.UserDbRepository;
import concertpro.persistance.repository.user_repository.UserHibernateRepository;
import concertpro.services.IChatService;
import concertpro.validator.OrderValidator;
import concertpro.validator.Validator;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    public static void main(String[] args) {
        Properties serverProperties = new Properties();
        try {
            serverProperties.load(StartServer.class.getResourceAsStream("/concertproserver.properties"));
            System.out.println("Server properties set. ");
            serverProperties.list(System.out);
        }
        catch (IOException e) {
            System.err.println("Cannot find concertproserver.properties " + e);
        }

        IUserRepository userRepository = new UserHibernateRepository();
        ISpectacleRepository spectacleRepository = new SpectacleHibernateRepository();
        Validator<Order> orderValidator = new OrderValidator();
        IOrderRepository orderRepository = new OrderHibernateRepository(orderValidator);
        IChatService chatService = new ChatService(userRepository, spectacleRepository, orderRepository);

        int chatServerPort = 55555;
        try {
            chatServerPort = Integer.parseInt(serverProperties.getProperty("chat.server.port"));
        }
        catch (NumberFormatException nef) {
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port 55555");
        }

        System.out.println("Starting server on port: " + chatServerPort);
        AbstractServer server = new ChatConcurrentServer(chatServerPort, chatService);

        try {
            server.start();
        }
        catch (ServerException exception) {
            System.err.println("Error starting the server" + exception.getMessage());
            exception.printStackTrace();
        }
        finally {
            try {
                server.stop();
            }
            catch(ServerException exception){
                System.err.println("Error stopping server " + exception.getMessage());
            }
        }
    }
}
