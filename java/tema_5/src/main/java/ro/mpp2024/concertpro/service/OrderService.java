package ro.mpp2024.concertpro.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.dao.exception.ValidationException;
import ro.mpp2024.concertpro.dao.model.Order;
import ro.mpp2024.concertpro.dao.model.Spectacle;
import ro.mpp2024.concertpro.dao.repository.order_repository.IOrderRepository;
import ro.mpp2024.concertpro.dao.repository.spectacle_repository.ISpectacleRepository;

public class OrderService {
    private final IOrderRepository orderRepository;
    private final ISpectacleRepository spectacleRepository;
    private static final Logger logger = LogManager.getLogger();

    public OrderService(IOrderRepository orderRepository, ISpectacleRepository spectacleRepository) {
        logger.info("Initializing Order Service.");
        this.orderRepository = orderRepository;
        this.spectacleRepository = spectacleRepository;
    }

    public void addOrder(Order order) {
        this.orderRepository.save(order);

        Spectacle newSpectacle = order.getSpectacle();

        if(order.getSpectacle().getSeatsAvailable() < order.getNumberOfSeats()) {
            throw new ValidationException("There are not enough seats for this spectacle.");
        }

        newSpectacle.setSeatsSold(newSpectacle.getSeatsSold() + order.getNumberOfSeats());
        newSpectacle.setSeatsAvailable(newSpectacle.getSeatsAvailable() - order.getNumberOfSeats());
        this.spectacleRepository.update(newSpectacle, newSpectacle.getId());
    }
}
