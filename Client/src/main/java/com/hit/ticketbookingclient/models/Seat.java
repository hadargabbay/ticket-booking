
package com.hit.ticketbookingclient.models;

import java.io.Serializable;

public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;
    private int row;
    private int number;
    private double price;
    private boolean isAvailable;

    public Seat() {
        // No-arg constructor for JSON deserialization (GSON)
    }

    public Seat(int row, int number, double price, boolean isAvailable) {
        this.row = row;
        this.number = number;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    // Getters
    public int getRow() { return row; }
    public int getNumber() { return number; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }

    // Setters (for GSON deserialization)
    public void setRow(int row) { this.row = row; }
    public void setNumber(int number) { this.number = number; }
    public void setPrice(double price) { this.price = price; }
    public void setAvailable(boolean available) { isAvailable = available; }
}