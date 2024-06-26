package ro.mpp2024.concertpro.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private final Properties jdbcProperties;
    private static final Logger logger = LogManager.getLogger();
    private  Connection instance = null;

    public JdbcUtils(Properties jdbcProperties) {
        this.jdbcProperties=jdbcProperties;
    }

    private Connection getNewConnection() {
        logger.traceEntry();

        String url = this.jdbcProperties.getProperty("jdbc.url");
        String user = this.jdbcProperties.getProperty("jdbc.user");
        String password = this.jdbcProperties.getProperty("jdbc.password");

        logger.info("trying to connect to database ... {}",url);
        logger.info("user: {}",user);
        logger.info("pass: {}", password);

        Connection connection = null;
        try {
            if (user != null && password != null) {
                connection = DriverManager.getConnection(url, user, password);
            }
            else {
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection " + e);
        }

        return connection;
    }

    public Connection getConnection() {
        logger.traceEntry();
        try {
            if (instance == null || instance.isClosed()) {
                instance = getNewConnection();
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }

        logger.traceExit(instance);
        return instance;
    }
}
