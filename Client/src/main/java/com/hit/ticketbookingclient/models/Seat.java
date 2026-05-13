
package com.hit.ticketbookingclient.models;

import java.io.Serializable;

/** One seat in a venue: row, number, price, and whether it is still free to sell. */
public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;
    private int row;
    private int number;
    private double price;
    private boolean isAvailable;

    /** Empty constructor so JSON from the server can fill this object. */
    public Seat() {
        // No-arg constructor for JSON deserialization (GSON)
    }

    /** Creates a seat with all fields set (for tests or local building). */
    public Seat(int row, int number, double price, boolean isAvailable) {
        this.row = row;
        this.number = number;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    /** Returns which row this seat belongs to. */
    public int getRow() { return row; }
    /** Returns the seat number within the row. */
    public int getNumber() { return number; }
    /** Returns how much this seat costs. */
    public double getPrice() { return price; }
    /** Returns true if the seat is still available to book. */
    public boolean isAvailable() { return isAvailable; }

    /** Sets the row when rebuilding from JSON. */
    public void setRow(int row) { this.row = row; }
    /** Sets the seat number when rebuilding from JSON. */
    public void setNumber(int number) { this.number = number; }
    /** Sets the price when rebuilding from JSON. */
    public void setPrice(double price) { this.price = price; }
    /** Marks the seat free or taken to match the server state. */
    public void setAvailable(boolean available) { isAvailable = available; }
}
