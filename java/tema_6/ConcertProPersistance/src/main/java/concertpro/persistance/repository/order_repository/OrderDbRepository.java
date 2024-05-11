package concertpro.persistance.repository.order_repository;

import concertpro.model.Order;
import concertpro.persistance.repository.JdbcUtils;
import concertpro.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class OrderDbRepository implements IOrderRepository {

    private final JdbcUtils jdbcUtils;
    private final Validator<Order> orderValidator;

    public OrderDbRepository(Properties properties, Validator<Order> orderValidator) {
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
        this.orderValidator.validate(order);

        Connection connection = this.jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into orders (buyerName, " +
                "spectacleId, numberOfSeats) values (?, ?, ?)")) {
            preparedStatement.setString(1, order.getBuyerName());
            preparedStatement.setLong(2, order.getSpectacle().getId());
            preparedStatement.setLong(3, order.getNumberOfSeats());

            int result = preparedStatement.executeUpdate();
        }
        catch (SQLException exception) {
            System.err.println("Error DB " + exception);
        }
    }

    @Override
    public void update(Order order, Long orderId) {
    }

    @Override
    public void delete(Long orderId) {
    }

}
