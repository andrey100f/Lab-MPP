package ro.mpp.concertpro.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddSpectacleDto(

    @NotBlank
    String artistName,

    @NotBlank
    String spectacleDate,

    @NotBlank
    String spectaclePlace,

    @Min(0)
    Long seatsAvailable,

    @Min(0)
    Long seatsSold
) { }
