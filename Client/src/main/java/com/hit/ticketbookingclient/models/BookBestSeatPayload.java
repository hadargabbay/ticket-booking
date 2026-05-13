package com.hit.ticketbookingclient.models;

// Values the booking screen sends when asking the server to reserve seats.
public class BookBestSeatPayload {
    private String showTitle;
    private String customerName;
    private String algorithmPref;
    private int amount = 1;

    // Empty object for JSON tools.
    public BookBestSeatPayload() {}

    // Fills all booking fields in one call.
    public BookBestSeatPayload(String showTitle, String customerName, String algorithmPref, int amount) {
        this.showTitle = showTitle;
        this.customerName = customerName;
        this.algorithmPref = algorithmPref;
        this.amount = amount;
    }

    // Returns the show the user wants to book.
    public String getShowTitle() { return showTitle; }
    // Sets the show title.
    public void setShowTitle(String showTitle) { this.showTitle = showTitle; }

    // Returns the buyer's name.
    public String getCustomerName() { return customerName; }
    // Sets the buyer's name.
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    // Returns which seat-selection strategy to use (CHEAPEST or FIRST_AVAILABLE).
    public String getAlgorithmPref() { return algorithmPref; }
    // Sets the strategy code.
    public void setAlgorithmPref(String algorithmPref) { this.algorithmPref = algorithmPref; }

    // Returns how many tickets to book at once.
    public int getAmount() { return amount; }
    // Sets how many tickets to book.
    public void setAmount(int amount) { this.amount = amount; }
}
