package dto;

/** Data sent with BOOK_BEST_SEAT: which show, who is buying, which algorithm, and how many tickets. */
public class BookBestSeatPayload {
    private String showTitle;
    private String customerName;
    private String algorithmPref;
    private int amount = 1;

    /** Empty object for JSON tools. */
    public BookBestSeatPayload() {}

    /** Fills all booking fields in one call. */
    public BookBestSeatPayload(String showTitle, String customerName, String algorithmPref, int amount) {
        this.showTitle = showTitle;
        this.customerName = customerName;
        this.algorithmPref = algorithmPref;
        this.amount = amount;
    }

    /** Returns the show the user wants to book. */
    public String getShowTitle() { return showTitle; }
    /** Sets the show title. */
    public void setShowTitle(String showTitle) { this.showTitle = showTitle; }

    /** Returns the buyer's name (used to find or create a customer). */
    public String getCustomerName() { return customerName; }
    /** Sets the buyer's name. */
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    /** Returns FIRST_AVAILABLE, CHEAPEST, or similar preference text. */
    public String getAlgorithmPref() { return algorithmPref; }
    /** Sets which seat-selection strategy to use. */
    public void setAlgorithmPref(String algorithmPref) { this.algorithmPref = algorithmPref; }

    /** Returns how many seats to book in one request. */
    public int getAmount() { return amount; }
    /** Sets how many seats to book. */
    public void setAmount(int amount) { this.amount = amount; }
}
