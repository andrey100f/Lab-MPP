package ro.mpp2024.concertpro.dao.model;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Entity<Long>, Serializable {
    private Long orderId;
    private String buyerName;
    private Spectacle spectacle;
    private Long numberOfSeats;

    public Order() {
        this.orderId = 0L;
        this.buyerName = "";
        this.spectacle = new Spectacle();
        this.numberOfSeats = 0L;
    }

    public Order(String buyerName, Spectacle spectacle, Long numberOfSeats) {
        this.buyerName = buyerName;
        this.spectacle = spectacle;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public Long getId() {
        return this.orderId;
    }

    @Override
    public void setId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return this.buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Spectacle getSpectacle() {
        return this.spectacle;
    }

    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public Long getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return this.numberOfSeats.equals(order.numberOfSeats) && Objects.equals(this.orderId, order.orderId) &&
                Objects.equals(this.buyerName, order.buyerName) && Objects.equals(this.spectacle, order.spectacle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.orderId, this.buyerName, this.spectacle, this.numberOfSeats);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + this.orderId +
                ", buyerName='" + this.buyerName + '\'' +
                ", ticket=" + this.spectacle +
                ", numberOfSeats=" + this.numberOfSeats +
                '}';
    }
}
