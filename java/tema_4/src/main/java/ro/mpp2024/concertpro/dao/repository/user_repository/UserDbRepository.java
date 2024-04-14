package ro.mpp2024.concertpro.dao.repository.user_repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.dao.model.User;
import ro.mpp2024.concertpro.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDbRepository implements IUserRepository {
    private final JdbcUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();

    public UserDbRepository(Properties props) {
        logger.info("Initializing UserDbRepository with properties: {} ", props);
        this.jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public User findOne(Long userId) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public void save(User user) {
    }

    @Override
    public void update(User user, Long userId) {
        logger.traceEntry("update task {} ", user);
        Connection connection = this.jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("update users set username = ?, " +
                "password = ?, loggedIn = ? where userId = ?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.getLoggedIn());
            preparedStatement.setLong(4, userId);

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException exception) {
            logger.error(exception);
            System.err.println("Error DB: " + exception);
        }

        logger.traceExit();
    }

    @Override
    public void delete(Long userId) {
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        logger.traceEntry();
        Connection connection = this.jdbcUtils.getConnection();
        User user = new User();

        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE " +
                "username = ? AND password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Long userId = resultSet.getLong("userId");
                    String usernameN = resultSet.getString("username");
                    String passwordN = resultSet.getString("password");
                    Boolean loggedIn = resultSet.getBoolean("loggedIn");

                    user.setId(userId);
                    user.setUsername(usernameN);
                    user.setPassword(passwordN);
                    user.setLoggedIn(loggedIn);
                }
            }
        } catch (SQLException exception) {
            logger.error(exception);
            System.err.println("Error DB: " + exception);
        }

        logger.traceExit();
        return user;
    }
}
