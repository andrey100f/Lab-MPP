package concertpro.persistance.repository.user_repository;

import concertpro.model.Spectacle;
import concertpro.model.User;
import concertpro.persistance.repository.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDbRepository implements IUserRepository {

    private final JdbcUtils jdbcUtils;

    public UserDbRepository(Properties props) {
        this.jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public User findOne(Long userId) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        Connection connection = this.jdbcUtils.getConnection();
        List<User> users = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from users")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long userId = resultSet.getLong("userId");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Boolean loggedIn = resultSet.getBoolean("loggedIn");

                    User user = new User(username, password, loggedIn);
                    user.setId(userId);
                    users.add(user);
                }
            }
        } catch (SQLException exception) {
            System.err.println("Error DB: " + exception);
        }

        return users;
    }

    @Override
    public void save(User user) {
    }

    @Override
    public void update(User user, Long userId) {
        Connection connection = this.jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("update users set username = ?, " +
                "password = ?, loggedIn = ? where userId = ?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.getLoggedIn());
            preparedStatement.setLong(4, userId);

            int result = preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Error DB: " + exception);
        }

    }

    @Override
    public void delete(Long userId) {
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
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
            System.err.println("Error DB: " + exception);
        }

        return user;
    }

}
