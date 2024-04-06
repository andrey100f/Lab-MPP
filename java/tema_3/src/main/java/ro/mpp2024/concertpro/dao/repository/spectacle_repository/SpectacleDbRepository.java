package ro.mpp2024.concertpro.dao.repository.spectacle_repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.mpp2024.concertpro.dao.model.Spectacle;
import ro.mpp2024.concertpro.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpectacleDbRepository implements ISpectacleRepository {
    private final JdbcUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();

    public SpectacleDbRepository(Properties props) {
        logger.info("Initializing SpectacleDbRepository with properties: {} ", props);
        this.jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public Spectacle findOne(Long spectacleIdN) {
        logger.traceEntry();
        Connection connection = this.jdbcUtils.getConnection();
        Spectacle spectacle = new Spectacle();

        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM spectacles WHERE " +
                "spectacleId = ?")) {
            preparedStatement.setLong(1, spectacleIdN);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Long spectacleId = resultSet.getLong("spectacleId");
                    String artistName = resultSet.getString("artistName");
                    LocalDate spectacleDate = resultSet.getDate("spectacleDate").toLocalDate();
                    String spectaclePlace = resultSet.getString("spectaclePlace");
                    Long seatsAvailable = resultSet.getLong("seatsAvailable");
                    Long seatsSold = resultSet.getLong("seatsSold");

                    spectacle.setId(spectacleId);
                    spectacle.setArtistName(artistName);
                    spectacle.setSpectacleDate(spectacleDate);
                    spectacle.setSpectaclePlace(spectaclePlace);
                    spectacle.setSeatsAvailable(seatsAvailable);
                    spectacle.setSeatsSold(seatsSold);
                }
            }
        } catch (SQLException exception) {
            logger.error(exception);
            System.err.println("Error DB: " + exception);
        }

        logger.traceExit();
        return spectacle;
    }

    @Override
    public Iterable<Spectacle> findAll() {
        logger.traceEntry("findAll Spectacles task");
        Connection connection = this.jdbcUtils.getConnection();
        List<Spectacle> spectacles = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from spectacles")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long spectacleId = resultSet.getLong("spectacleId");
                    String artistName = resultSet.getString("artistName");
                    LocalDate spectacleDate = resultSet.getDate("spectacleDate").toLocalDate();
                    String spectaclePlace = resultSet.getString("spectaclePlace");
                    Long seatsAvailable = resultSet.getLong("seatsAvailable");
                    Long seatsSold = resultSet.getLong("seatsSold");

                    Spectacle spectacle = new Spectacle(artistName, spectacleDate, spectaclePlace, seatsAvailable, seatsSold);
                    spectacle.setId(spectacleId);
                    spectacles.add(spectacle);
                }
            }
        } catch (SQLException exception) {
            logger.error(exception);
            System.err.println("Error DB: " + exception);
        }

        logger.traceExit();
        return spectacles;
    }

    @Override
    public void save(Spectacle spectacle) {
    }

    @Override
    public void update(Spectacle spectacle, Long spectacleId) {
        logger.traceEntry("update task {} ", spectacle);
        Connection connection = this.jdbcUtils.getConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement("update spectacles set " +
                "artistName = ?, spectacleDate = ?, spectaclePlace = ?, seatsAvailable = ?, seatsSold = ? " +
                "where spectacleId = ?")) {
            preparedStatement.setString(1, spectacle.getArtistName());
            preparedStatement.setString(2, spectacle.getSpectacleDate().toString());
            preparedStatement.setString(3, spectacle.getSpectaclePlace());
            preparedStatement.setLong(4, spectacle.getSeatsAvailable());
            preparedStatement.setLong(5, spectacle.getSeatsSold());
            preparedStatement.setLong(6, spectacleId);

            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);
        } catch (SQLException exception) {
            logger.error(exception);
            System.err.println("Error DB: " + exception);
        }

        logger.traceExit();
    }

    @Override
    public void delete(Long spectacleId) {
    }

    @Override
    public Iterable<Spectacle> getAllByDate(LocalDate date) {
        logger.traceEntry("findSpectaclesByDate task");
        Connection connection = this.jdbcUtils.getConnection();
        List<Spectacle> spectacles = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from spectacles where spectacleDate = ?")) {
            preparedStatement.setString(1, date.toString());

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long spectacleId = resultSet.getLong("spectacleId");
                    String artistName = resultSet.getString("artistName");
                    LocalDate spectacleDate = resultSet.getDate("spectacleDate").toLocalDate();
                    String spectaclePlace = resultSet.getString("spectaclePlace");
                    Long seatsAvailable = resultSet.getLong("seatsAvailable");
                    Long seatsSold = resultSet.getLong("seatsSold");

                    Spectacle spectacle = new Spectacle(artistName, spectacleDate, spectaclePlace, seatsAvailable, seatsSold);
                    spectacle.setId(spectacleId);
                    spectacles.add(spectacle);
                }
            }
        } catch (SQLException exception) {
            logger.error(exception);
            System.err.println("Error DB: " + exception);
        }

        logger.traceExit();
        return spectacles;
    }
}
