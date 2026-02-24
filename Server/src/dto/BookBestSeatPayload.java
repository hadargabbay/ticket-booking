package dto;

public class BookBestSeatPayload {
    private String showTitle;
    private String customerName;
    private String algorithmPref;
    private int amount = 1;

    public BookBestSeatPayload() {}

    public BookBestSeatPayload(String showTitle, String customerName, String algorithmPref, int amount) {
        this.showTitle = showTitle;
        this.customerName = customerName;
        this.algorithmPref = algorithmPref;
        this.amount = amount;
    }

    public String getShowTitle() { return showTitle; }
    public void setShowTitle(String showTitle) { this.showTitle = showTitle; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getAlgorithmPref() { return algorithmPref; }
    public void setAlgorithmPref(String algorithmPref) { this.algorithmPref = algorithmPref; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}