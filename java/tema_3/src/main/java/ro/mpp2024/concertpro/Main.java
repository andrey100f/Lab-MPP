package ro.mpp2024.concertpro;

import ro.mpp2024.concertpro.dao.model.Order;
import ro.mpp2024.concertpro.dao.model.Spectacle;
import ro.mpp2024.concertpro.dao.model.User;
import ro.mpp2024.concertpro.dao.repository.order_repository.IOrderRepository;
import ro.mpp2024.concertpro.dao.repository.order_repository.OrderDbRepository;
import ro.mpp2024.concertpro.dao.repository.spectacle_repository.ISpectacleRepository;
import ro.mpp2024.concertpro.dao.repository.spectacle_repository.SpectacleDbRepository;
import ro.mpp2024.concertpro.dao.repository.user_repository.IUserRepository;
import ro.mpp2024.concertpro.dao.repository.user_repository.UserDbRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties =new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        // Test UserRepository
        IUserRepository userRepository = new UserDbRepository(properties);
        User user = userRepository.findByUsernameAndPassword("john_doe", "parola123");
        System.out.println("Userul gasit este: " + user);

        User userToUpdate = new User("andrey", "andrey100f", false);
        userRepository.update(userToUpdate, 2L);

        // Test SpectacleRepository
        ISpectacleRepository spectacleRepository = new SpectacleDbRepository(properties);
        List<Spectacle> spectacleList = (List<Spectacle>) spectacleRepository.findAll();
        System.out.println("Toate spectacolele sunt: ");
        spectacleList.forEach(System.out::println);

        Spectacle spectacle = spectacleRepository.findOne(1L);
        System.out.println("Spectacolul cu id-ul 1 este: ");
        System.out.println(spectacle);

        // Test OrderRepository
//        IOrderRepository orderRepository = new OrderDbRepository(properties);
//        Order order = new Order("andrei", spectacle, 2L);
//        orderRepository.save(order);
    }
}
