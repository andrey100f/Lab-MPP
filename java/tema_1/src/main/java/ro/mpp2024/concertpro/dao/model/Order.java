package ro.mpp2024.concertpro.dao.model;

import java.util.Objects;

public class Order {
    private Long orderId;
    private String buyerName;
    private Spectacle spectacle;
    private int numberOfSeats;

    public Order() {
    }

    public Order(Long orderId, String buyerName, Spectacle spectacle, int numberOfSeats) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.spectacle = spectacle;
        this.numberOfSeats = numberOfSeats;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return numberOfSeats == order.numberOfSeats && Objects.equals(orderId, order.orderId) && Objects.equals(buyerName, order.buyerName) && Objects.equals(spectacle, order.spectacle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, buyerName, spectacle, numberOfSeats);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", buyerName='" + buyerName + '\'' +
                ", spectacle=" + spectacle +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }
}
