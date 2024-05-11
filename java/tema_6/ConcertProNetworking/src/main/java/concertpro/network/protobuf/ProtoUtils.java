package concertpro.network.protobuf;

import concertpro.model.Order;
import concertpro.model.Spectacle;
import concertpro.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProtoUtils {
    public static ConcertProProtocol.Request createLoginRequest(User user){
        ConcertProProtocol.User userDto = ConcertProProtocol.User.newBuilder()
            .setUsername(user.getUsername())
            .setPassword(user.getPassword())
            .setLoggedIn(user.getLoggedIn())
            .build();

        ConcertProProtocol.Request request = ConcertProProtocol.Request.newBuilder()
            .setType(ConcertProProtocol.Request.Type.LOGIN)
            .setUser(userDto)
            .build();

        return request;
    }

    public static ConcertProProtocol.Request createGetSpectaclesRequest() {
        ConcertProProtocol.Request request = ConcertProProtocol.Request.newBuilder()
            .setType(ConcertProProtocol.Request.Type.GET_SPECTACLES)
            .build();
        return request;
    }

    public static ConcertProProtocol.Request createGetSpectaclesByDateRequest(LocalDate date) {
        ConcertProProtocol.Request request = ConcertProProtocol.Request.newBuilder()
            .setType(ConcertProProtocol.Request.Type.GET_FILTERED_SPECTACLES)
            .setDate(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
            .build();

        return request;
    }

    public static ConcertProProtocol.Request createAddOrderRequest(Order order) {
        ConcertProProtocol.Spectacle spectacle = ConcertProProtocol.Spectacle.newBuilder()
            .setId(order.getSpectacle().getId())
            .setArtistName(order.getSpectacle().getArtistName())
            .setSpectacleDate(order.getSpectacle().getSpectacleDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
            .setSpectaclePlace(order.getSpectacle().getSpectaclePlace())
            .setSeatsAvailable(order.getSpectacle().getSeatsAvailable())
            .setSeatsSold(order.getSpectacle().getSeatsSold())
            .build();

        ConcertProProtocol.Order orderDto = ConcertProProtocol.Order.newBuilder()
            .setBuyerName(order.getBuyerName())
            .setNumberOfSeats(order.getNumberOfSeats())
            .setSpectacle(spectacle)
            .build();

        ConcertProProtocol.Request request = ConcertProProtocol.Request.newBuilder()
            .setType(ConcertProProtocol.Request.Type.ADD_ORDER)
            .setOrder(orderDto)
            .build();

        return request;
    }

    public static Spectacle[] getSpectaclesResponse(ConcertProProtocol.Response response) {
        Spectacle[] spectacles = new Spectacle[response.getSpectaclesCount()];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for(int i = 0; i < response.getSpectaclesCount(); i++) {
            ConcertProProtocol.Spectacle spectacleDto = response.getSpectacles(i);
            Spectacle spectacle = new Spectacle();
            spectacle.setId(spectacleDto.getId());
            spectacle.setArtistName(spectacleDto.getArtistName());
            spectacle.setSpectacleDate(LocalDate.parse(spectacleDto.getSpectacleDate(), formatter));
            spectacle.setSpectaclePlace(spectacleDto.getSpectaclePlace());
            spectacle.setSeatsAvailable(spectacleDto.getSeatsAvailable());
            spectacle.setSeatsSold(spectacleDto.getSeatsSold());
            spectacles[i] = spectacle;
        }

        return spectacles;
    }

    public static String getError(ConcertProProtocol.Response response){
        String errorMessage = response.getError();
        return errorMessage;
    }


}
