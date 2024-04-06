package ro.mpp2024.concertpro.dao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Spectacle implements Entity<Long>, Serializable {
    private Long spectacleId;
    private String artistName;
    private Date spectacleDate;
    private String spectaclePlace;
    private Long seatsAvailable;
    private Long seatsSold;

    public Spectacle() {
        this.spectacleId = 0L;
        this.artistName = "";
        this.spectacleDate = new Date();
        this.spectaclePlace = "";
        this.seatsAvailable = 0L;
        this.seatsSold = 0L;
    }

    public Spectacle(String artistName, Date spectacleDate, String spectaclePlace, Long seatsAvailable, Long seatsSold) {
        this.artistName = artistName;
        this.spectacleDate = spectacleDate;
        this.spectaclePlace = spectaclePlace;
        this.seatsAvailable = seatsAvailable;
        this.seatsSold = seatsSold;
    }

    @Override
    public Long getId() {
        return this.spectacleId;
    }

    @Override
    public void setId(Long spectacleId) {
        this.spectacleId = spectacleId;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Date getSpectacleDate() {
        return this.spectacleDate;
    }

    public void setSpectacleDate(Date spectacleDate) {
        this.spectacleDate = spectacleDate;
    }

    public String getSpectaclePlace() {
        return this.spectaclePlace;
    }

    public void setSpectaclePlace(String spectaclePlace) {
        this.spectaclePlace = spectaclePlace;
    }

    public Long getSeatsAvailable() {
        return this.seatsAvailable;
    }

    public void setSeatsAvailable(Long seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

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
        return Objects.equals(this.spectacleId, spectacle.spectacleId) &&
                Objects.equals(this.artistName, spectacle.artistName) &&
                Objects.equals(this.spectacleDate, spectacle.spectacleDate) &&
                Objects.equals(this.spectaclePlace, spectacle.spectaclePlace) &&
                Objects.equals(this.seatsAvailable, spectacle.seatsAvailable) &&
                Objects.equals(this.seatsSold, spectacle.seatsSold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.spectacleId, this.artistName, this.spectacleDate, this.spectaclePlace,
                this.seatsAvailable, this.seatsSold);
    }

    @Override
    public String toString() {
        return "Spectacle{" +
                "spectacleId=" + this.spectacleId +
                ", artistName='" + this.artistName + '\'' +
                ", spectacleDate=" + this.spectacleDate +
                ", spectaclePlace='" + this.spectaclePlace + '\'' +
                ", seatsAvailable=" + this.seatsAvailable +
                ", seatsSold=" + this.seatsSold +
                '}';
    }
}
