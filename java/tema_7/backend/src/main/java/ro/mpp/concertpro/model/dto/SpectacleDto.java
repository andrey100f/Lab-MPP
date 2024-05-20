package ro.mpp.concertpro.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SpectacleDto(

    @NotNull(message = "Spectacle ID is required")
    Long spectacleId,

    @NotBlank(message = "Artist name is required")
    String artistName,

    @NotBlank(message = "Spectacle date is required")
    String spectacleDate,

    @NotBlank(message = "Spectacle place is required")
    String spectaclePlace,

    @Min(value = 0, message = "Seats available must be greater than or equal to 0")
    Long seatsAvailable,

    @Min(value = 0, message = "Seats sold must be greater than or equal to 0")
    Long seatsSold
) { }
