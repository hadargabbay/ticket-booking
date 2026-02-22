package dto;

/**
 * Payload for the BOOK_BEST_SEAT action.
 */
public class BookBestSeatPayload {
    private String showTitle;
    private int customerId;

    public BookBestSeatPayload() {
    }

    public BookBestSeatPayload(String showTitle, int customerId) {
        this.showTitle = showTitle;
        this.customerId = customerId;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
