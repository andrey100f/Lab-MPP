package concertpro.server;

import concertpro.model.Order;
import concertpro.model.Spectacle;
import concertpro.model.User;
import concertpro.persistance.repository.order_repository.IOrderRepository;
import concertpro.persistance.repository.spectacle_repository.ISpectacleRepository;
import concertpro.persistance.repository.user_repository.IUserRepository;
import concertpro.services.ChatException;
import concertpro.services.IChatObserver;
import concertpro.services.IChatService;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatService implements IChatService {

    private IUserRepository userRepository;
    private ISpectacleRepository spectacleRepository;
    private IOrderRepository orderRepository;

    private Map<String, IChatObserver> loggedClients;

    public ChatService(IUserRepository userRepository, ISpectacleRepository spectacleRepository,
                       IOrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.spectacleRepository = spectacleRepository;
        this.orderRepository = orderRepository;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, IChatObserver client) throws ChatException {
        User userR = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());

        if(userR.getId() != 0) {
            if(loggedClients.get(user.getUsername()) != null) {
                throw new ChatException("User already logged in.");
            }

            loggedClients.put(userR.getUsername(), client);
//            notifyUsersLoggedIn(user);
        }
        else {
            throw new ChatException("Authentication failed.");
        }
    }

    private final int defaultThreadsNumber = 5;

//    private void notifyUsers(User user) throws ChatException {
//        Iterable<User> users = userRepository.findAll();
//
//        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNumber);
//        for(User us : users) {
//            if(us != user) {
//                IChatObserver chatClient = loggedClients.get(us.getUsername());
//
//                if(chatClient != null) {
//                    executor.execute(() -> {
//                        try {
//                            System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
//                            chatClient.userLoggedIn(user);
//                        }
//                        catch (ChatException e) {
//                            System.err.println("Error notifying friend " + e);
//                        }
//                    });
//                }
//            }
//        }
//
//        executor.shutdown();
//    }

    private void notifyUsers(Spectacle[] spectacles) throws ChatException {
        Iterable<User> users = userRepository.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(this.defaultThreadsNumber);

        for(User us : users){
            IChatObserver chatClient = loggedClients.get(us.getUsername());
            if (chatClient != null)
                executor.execute(() -> {
                    System.out.println("Notifying ["+us.getId()+"] friend ["+spectacles+"] changed.");

                    try {
                        chatClient.updateSpectacles(spectacles);
                    } catch (ChatException e) {
                        throw new RuntimeException(e);
                    }
                });

        }

        executor.shutdown();
    }

    @Override
    public void logout(User user, IChatObserver client) throws ChatException {
        IChatObserver localClient = loggedClients.remove(user.getUsername());

        if(localClient == null) {
            throw new ChatException("User "+user.getId()+" is not logged in.");
        }

//        this.notifyUsersLoggedOut(user);
    }

    @Override
    public synchronized User[] getLoggedUsers(User user) throws ChatException {
        Iterable<User> users = userRepository.findAll();
        Set<User> result = new TreeSet<>();
        System.out.println("Logged friends for: "+user.getId());

        for (User us : users) {
            if (loggedClients.containsKey(user.getUsername())){    //the friend is logged in
                result.add(new User(us.getUsername(), us.getPassword(), us.getLoggedIn()));
                System.out.println("+" + us.getId());
            }
        }

        System.out.println("Size " + result.size());
        return result.toArray(new User[result.size()]);
    }

    @Override
    public synchronized Spectacle[] getAllSpectacles() throws ChatException {
        List<Spectacle> spectacles =  (List<Spectacle>) this.spectacleRepository.findAll();
        return spectacles.toArray(new Spectacle[0]);
    }

    @Override
    public synchronized Spectacle[] getSpectaclesByDate(LocalDate date) throws ChatException {
        List<Spectacle> spectacles =  (List<Spectacle>) this.spectacleRepository.getAllByDate(date);
        return spectacles.toArray(new Spectacle[0]);
    }

    @Override
    public synchronized void addOrder(Order order) throws ChatException {
        this.orderRepository.save(order);

        if(order.getSpectacle().getSeatsAvailable() < order.getNumberOfSeats()) {
            throw new ChatException("There are not enough seats for this spectacle.");
        }

        Spectacle newSpectacle = order.getSpectacle();

        newSpectacle.setSeatsSold(newSpectacle.getSeatsSold() + order.getNumberOfSeats());
        newSpectacle.setSeatsAvailable(newSpectacle.getSeatsAvailable() - order.getNumberOfSeats());
        this.spectacleRepository.update(newSpectacle, newSpectacle.getId());

        Spectacle[] spectacles = this.getAllSpectacles();

        for (IChatObserver client : loggedClients.values()) {
            try {
                client.updateSpectacles(spectacles);
            } catch (ChatException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
