package ro.mpp.concertpro.model.mapper;

import ro.mpp.concertpro.model.Spectacle;
import ro.mpp.concertpro.model.dto.AddSpectacleDto;
import ro.mpp.concertpro.model.dto.SpectacleDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SpectacleMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static SpectacleDto mapToDto(Spectacle spectacle) {
        return SpectacleDto.builder()
            .spectacleId(spectacle.getSpectacleId())
            .artistName(spectacle.getArtistName())
            .spectacleDate(spectacle.getSpectacleDate().format(formatter))
            .spectaclePlace(spectacle.getSpectaclePlace())
            .seatsAvailable(spectacle.getSeatsAvailable())
            .seatsSold(spectacle.getSeatsSold())
            .build();
    }

    public static Spectacle mapToModel(SpectacleDto spectacleDto) {
        return Spectacle.builder()
            .spectacleId(spectacleDto.spectacleId())
            .artistName(spectacleDto.artistName())
            .spectacleDate(LocalDate.parse(spectacleDto.spectacleDate(), formatter))
            .spectaclePlace(spectacleDto.spectaclePlace())
            .seatsAvailable(spectacleDto.seatsAvailable())
            .seatsSold(spectacleDto.seatsSold())
            .build();
    }

    public static Spectacle mapFromAddDtoToModel(AddSpectacleDto spectacleDto) {
        return Spectacle.builder()
            .artistName(spectacleDto.artistName())
            .spectacleDate(LocalDate.parse(spectacleDto.spectacleDate(), formatter))
            .spectaclePlace(spectacleDto.spectaclePlace())
            .seatsAvailable(spectacleDto.seatsAvailable())
            .seatsSold(spectacleDto.seatsSold())
            .build();
    }

}
