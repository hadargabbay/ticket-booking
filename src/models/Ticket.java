package models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Customer customer;
    private Show show;
    private Seat seat;
    private LocalDateTime bookingTime;
    private double price;
    private String status; // e.g., "CONFIRMED", "CANCELLED"

    public Ticket() {
        // No-arg constructor for JSON deserialization (GSON)
    }

    public Ticket(int id, Customer customer, Show show, Seat seat, LocalDateTime bookingTime, double price, String status) {
        this.id = id;
        this.customer = customer;
        this.show = show;
        this.seat = seat;
        this.bookingTime = bookingTime;
        this.price = price;
        this.status = status;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Show getShow() {
        return show;
    }

    public Seat getSeat() {
        return seat;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", customer=" + customer +
                ", show=" + show +
                ", seat=" + seat +
                ", bookingTime=" + bookingTime +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}

