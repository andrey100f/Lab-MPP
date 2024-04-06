package ro.mpp2024.concertpro.dao.model;

import java.util.Date;
import java.util.Objects;

public class Spectacle {
    private Long spectacleId;
    private String artistName;
    private Date spectacleDate;
    private String spectaclePlace;
    private Long seatsAvailable;
    private Long seatsSold;

    public Spectacle() {
    }

    public Spectacle(Long spectacleId, String artistName, Date spectacleDate, String spectaclePlace, Long seatsAvailable, Long seatsSold) {
        this.spectacleId = spectacleId;
        this.artistName = artistName;
        this.spectacleDate = spectacleDate;
        this.spectaclePlace = spectaclePlace;
        this.seatsAvailable = seatsAvailable;
        this.seatsSold = seatsSold;
    }

    public Long getSpectacleId() {
        return spectacleId;
    }

    public void setSpectacleId(Long spectacleId) {
        this.spectacleId = spectacleId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Date getSpectacleDate() {
        return spectacleDate;
    }

    public void setSpectacleDate(Date spectacleDate) {
        this.spectacleDate = spectacleDate;
    }

    public String getSpectaclePlace() {
        return spectaclePlace;
    }

    public void setSpectaclePlace(String spectaclePlace) {
        this.spectaclePlace = spectaclePlace;
    }

    public Long getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Long seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Long getSeatsSold() {
        return seatsSold;
    }

    public void setSeatsSold(Long seatsSold) {
        this.seatsSold = seatsSold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectacle spectacle = (Spectacle) o;
        return Objects.equals(spectacleId, spectacle.spectacleId) && Objects.equals(artistName, spectacle.artistName) && Objects.equals(spectacleDate, spectacle.spectacleDate) && Objects.equals(spectaclePlace, spectacle.spectaclePlace) && Objects.equals(seatsAvailable, spectacle.seatsAvailable) && Objects.equals(seatsSold, spectacle.seatsSold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spectacleId, artistName, spectacleDate, spectaclePlace, seatsAvailable, seatsSold);
    }

    @Override
    public String toString() {
        return "Spectacle{" +
                "spectacleId=" + spectacleId +
                ", artistName='" + artistName + '\'' +
                ", spectacleDate=" + spectacleDate +
                ", spectaclePlace='" + spectaclePlace + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", seatsSold=" + seatsSold +
                '}';
    }
}
