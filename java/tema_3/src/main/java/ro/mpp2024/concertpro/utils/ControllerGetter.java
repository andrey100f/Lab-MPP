package ro.mpp2024.concertpro.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.controller.DashboardController;
import ro.mpp2024.concertpro.controller.LogInController;
import ro.mpp2024.concertpro.dao.model.Order;
import ro.mpp2024.concertpro.dao.repository.order_repository.IOrderRepository;
import ro.mpp2024.concertpro.dao.repository.order_repository.OrderDbRepository;
import ro.mpp2024.concertpro.dao.repository.spectacle_repository.ISpectacleRepository;
import ro.mpp2024.concertpro.dao.repository.spectacle_repository.SpectacleDbRepository;
import ro.mpp2024.concertpro.dao.repository.user_repository.IUserRepository;
import ro.mpp2024.concertpro.dao.repository.user_repository.UserDbRepository;
import ro.mpp2024.concertpro.dao.validator.OrderValidator;
import ro.mpp2024.concertpro.dao.validator.Validator;
import ro.mpp2024.concertpro.service.OrderService;
import ro.mpp2024.concertpro.service.SpectacleService;
import ro.mpp2024.concertpro.service.UserService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ControllerGetter {
    private static final Logger logger = LogManager.getLogger();

    private static Properties getProperties() {
        logger.traceEntry("Initializing properties from file");

        Properties properties =new Properties();
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            MessageAlert.showErrorMessage(null, "Cannot find bd.config " + e);
        }

        logger.traceExit();
        return properties;
    }

    public static DashboardController getDashboardController() {
        Properties properties = getProperties();

        ISpectacleRepository spectacleRepository = new SpectacleDbRepository(properties);
        SpectacleService spectacleService = new SpectacleService(spectacleRepository);

        Validator<Order> orderValidator = new OrderValidator();
        IOrderRepository orderRepository = new OrderDbRepository(properties, orderValidator);
        OrderService orderService = new OrderService(orderRepository, spectacleRepository);

        DashboardController dashboardController = new DashboardController();
        dashboardController.setSpectacleService(spectacleService);
        dashboardController.setOrderService(orderService);
        return dashboardController;
    }

    public static LogInController getLoginController() {
        Properties properties = getProperties();

        IUserRepository userRepository = new UserDbRepository(properties);
        UserService userService = new UserService(userRepository);

        return new LogInController(userService);
    }
}
