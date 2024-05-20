package ro.mpp.concertpro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "spectacles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spectacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spectacleId")
    private Long spectacleId;

    @Column(name = "artistName")
    private String artistName;

    @Column(name = "spectacleDate")
    private LocalDate spectacleDate;

    @Column(name = "spectaclePlace")
    private String spectaclePlace;

    @Column(name = "seatsAvailable")
    private Long seatsAvailable;

    @Column(name = "seatsSold")
    private Long seatsSold;

}
