package concertpro.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "spectacles")
public class Spectacle implements Entity<Long>, Serializable {

    private Long id;
    private String artistName;
    private LocalDate spectacleDate;
    private String spectaclePlace;
    private Long seatsAvailable;
    private Long seatsSold;

    public Spectacle() {
        this.id = 0L;
        this.artistName = "";
        this.spectacleDate = LocalDate.now();
        this.spectaclePlace = "";
        this.seatsAvailable = 0L;
        this.seatsSold = 0L;
    }

    public Spectacle(String artistName, LocalDate spectacleDate, String spectaclePlace, Long seatsAvailable, Long seatsSold) {
        this.artistName = artistName;
        this.spectacleDate = spectacleDate;
        this.spectaclePlace = spectaclePlace;
        this.seatsAvailable = seatsAvailable;
        this.seatsSold = seatsSold;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spectacleId")
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long spectacleId) {
        this.id = spectacleId;
    }

    @Column(name = "artistName")
    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Column(name = "spectacleDate")
    public LocalDate getSpectacleDate() {
        return this.spectacleDate;
    }

    public void setSpectacleDate(LocalDate spectacleDate) {
        this.spectacleDate = spectacleDate;
    }

    @Column(name = "spectaclePlace")
    public String getSpectaclePlace() {
        return this.spectaclePlace;
    }

    public void setSpectaclePlace(String spectaclePlace) {
        this.spectaclePlace = spectaclePlace;
    }

    @Column(name = "seatsAvailable")
    public Long getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public void setSeatsAvailable(Long seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    @Column(name = "seatsSold")
    public Long getSeatsSold() {
        return this.seatsSold;
    }

    public void setSeatsSold(Long seatsSold) {
        this.seatsSold = seatsSold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectacle spectacle = (Spectacle) o;
        return Objects.equals(this.id, spectacle.id) &&
            Objects.equals(this.artistName, spectacle.artistName) &&
            Objects.equals(this.spectacleDate, spectacle.spectacleDate) &&
            Objects.equals(this.spectaclePlace, spectacle.spectaclePlace) &&
            Objects.equals(this.seatsAvailable, spectacle.seatsAvailable) &&
            Objects.equals(this.seatsSold, spectacle.seatsSold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.artistName, this.spectacleDate, this.spectaclePlace,
            this.seatsAvailable, this.seatsSold);
    }

    @Override
    public String toString() {
        return "Spectacle{" +
            "spectacleId=" + this.id +
            ", artistName='" + this.artistName + '\'' +
            ", spectacleDate=" + this.spectacleDate +
            ", spectaclePlace='" + this.spectaclePlace + '\'' +
            ", seatsAvailable=" + this.seatsAvailable +
            ", seatsSold=" + this.seatsSold +
            '}';
    }

}
