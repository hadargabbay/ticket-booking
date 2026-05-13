package models;

import java.io.Serializable;
import java.time.LocalDateTime;

/** One confirmed (or cancelled) booking linking a customer, show, seat, price, and time. */
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Customer customer;
    private Show show;
    private Seat seat;
    private LocalDateTime bookingTime;
    private double price;
    private String status; // e.g., "CONFIRMED", "CANCELLED"

    /** Empty constructor so JSON tools can build a Ticket from network data. */
    public Ticket() {
        // No-arg constructor for JSON deserialization (GSON)
    }

    /** Creates a ticket with every field set for normal business use. */
    public Ticket(int id, Customer customer, Show show, Seat seat, LocalDateTime bookingTime, double price, String status) {
        this.id = id;
        this.customer = customer;
        this.show = show;
        this.seat = seat;
        this.bookingTime = bookingTime;
        this.price = price;
        this.status = status;
    }

    /** Returns the ticket id. */
    public int getId() {
        return id;
    }

    /** Returns who bought the ticket. */
    public Customer getCustomer() {
        return customer;
    }

    /** Returns which show this ticket is for. */
    public Show getShow() {
        return show;
    }

    /** Returns which seat was booked. */
    public Seat getSeat() {
        return seat;
    }

    /** Returns when the booking was made. */
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    /** Returns the price charged for this ticket. */
    public double getPrice() {
        return price;
    }

    /** Returns the booking status string (for example CONFIRMED). */
    public String getStatus() {
        return status;
    }

    /** Updates the ticket id. */
    public void setId(int id) {
        this.id = id;
    }

    /** Updates the customer reference. */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /** Updates the show reference. */
    public void setShow(Show show) {
        this.show = show;
    }

    /** Updates the seat reference. */
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    /** Updates the booking timestamp. */
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    /** Updates the stored price. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Updates the status text. */
    public void setStatus(String status) {
        this.status = status;
    }

    /** Builds a short text summary useful for logs or debugging. */
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

