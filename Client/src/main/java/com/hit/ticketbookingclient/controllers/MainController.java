package com.hit.ticketbookingclient.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.hit.ticketbookingclient.models.ApiRequest;
import com.hit.ticketbookingclient.models.ApiResponse;
import com.hit.ticketbookingclient.models.BookBestSeatPayload;
import com.hit.ticketbookingclient.network.NetworkClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import java.util.Optional;

public class MainController {

    @FXML private TextField showTitleField;
    @FXML private TextField customerNameField;
    @FXML private ComboBox<String> algoComboBox;
    @FXML private TextField amountField;
    @FXML private Button bookButton;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        if (algoComboBox != null) algoComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleBookSeat() {
        String showTitle = showTitleField.getText();
        String customerName = customerNameField.getText();
        String selectedAlgo = algoComboBox.getValue();
        String selectedAmountStr = amountField.getText();

        if (showTitle == null || showTitle.trim().isEmpty() ||
                customerName == null || customerName.trim().isEmpty() ||
                selectedAlgo == null || selectedAmountStr == null || selectedAmountStr.trim().isEmpty()) {
            showStatus("Please fill in all fields.", false);
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(selectedAmountStr.trim());
            if (amount <= 0) {
                showStatus("Amount must be at least 1.", false);
                return;
            }
        } catch (NumberFormatException e) {
            showStatus("Please enter a valid number for tickets.", false);
            return;
        }
        String algoCode = selectedAlgo.equals("Cheapest Seat") ? "CHEAPEST" : "FIRST_AVAILABLE";
        BookBestSeatPayload payload = new BookBestSeatPayload(showTitle, customerName, algoCode, amount);
        JsonElement jsonPayload = NetworkClient.getInstance().getGson().toJsonTree(payload);
        ApiRequest request = new ApiRequest("BOOK_BEST_SEAT", jsonPayload);
        showStatus("Sending request to server...", true);
        statusLabel.setTextFill(Color.BLUE);
        ApiResponse response = NetworkClient.getInstance().sendRequest(request);
        if (response.isSuccess()) {
            double totalPrice = 0.0;
            int ticketsCount = 0;

            try {
                if (response.getData() != null && response.getData().isJsonArray()) {
                    JsonArray ticketsArray = response.getData().getAsJsonArray();
                    ticketsCount = ticketsArray.size();
                    for (JsonElement element : ticketsArray) {
                        totalPrice += element.getAsJsonObject().get("price").getAsDouble();
                    }
                }
            } catch (Exception e) {
                totalPrice = 100.0 * amount;
                ticketsCount = amount;
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Secure Payment Gateway");
            dialog.setHeaderText("Booking " + ticketsCount + " ticket(s). Total amount: " + totalPrice + "₪");
            dialog.setContentText("Please enter your credit card number:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().trim().isEmpty()) {
                String ccNumber = result.get().trim();
                String last4 = ccNumber.length() >= 4 ? ccNumber.substring(ccNumber.length() - 4) : ccNumber;
                showStatus("Success! Card ending in *" + last4 + " was charged " + totalPrice + "₪.\n" + ticketsCount + " Ticket(s) Booked Successfully!", true);
            }
            else {
                ApiRequest cancelReq = new ApiRequest("CANCEL_BOOKING", response.getData());
                NetworkClient.getInstance().sendRequest(cancelReq);
                showStatus("Payment Cancelled. The tickets were NOT booked and seats are freed.", false);
            }
        }
        else {
            showStatus("Error: " + response.getMessage(), false);
        }
    }

    @FXML
    public void goToShowsView(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/hit/ticketbookingclient/ShowsView.fxml"));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 600, 450));
            stage.show();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
            showStatus("Error loading shows view.", false);
        }
    }

    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setTextFill(isSuccess ? Color.GREEN : Color.RED);
    }
}