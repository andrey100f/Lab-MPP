package ro.mpp2024.concertpro.dao.repository.order_repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.dao.model.Order;
import ro.mpp2024.concertpro.dao.validator.OrderValidator;
import ro.mpp2024.concertpro.dao.validator.Validator;
import ro.mpp2024.concertpro.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class OrderDbRepository implements IOrderRepository {
    private final JdbcUtils jdbcUtils;
    private final Validator<Order> orderValidator;
    private static final Logger logger = LogManager.getLogger();

    public OrderDbRepository(Properties properties, Validator<Order> orderValidator) {
        logger.info("Initializing OrderDbRepository with properties: {} ", properties);
        this.jdbcUtils = new JdbcUtils(properties);
        this.orderValidator = orderValidator;
    }

    @Override
    public Order findOne(Long orderId) {
        return null;
    }

    @Override
    public Iterable<Order> findAll() {
        return null;
    }

    @Override
    public void save(Order order) {
        logger.traceEntry("saving task {}", order);
        this.orderValidator.validate(order);

        Connection connection = this.jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into orders (buyerName, " +
                "spectacleId, numberOfSeats) values (?, ?, ?)")) {
            preparedStatement.setString(1, order.getBuyerName());
            preparedStatement.setLong(2, order.getSpectacle().getId());
            preparedStatement.setLong(3, order.getNumberOfSeats());

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);
        }
        catch (SQLException exception) {
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        logger.traceExit();
    }

    @Override
    public void update(Order order, Long orderId) {
    }

    @Override
    public void delete(Long orderId) {
    }
}
