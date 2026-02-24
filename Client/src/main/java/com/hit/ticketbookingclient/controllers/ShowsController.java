package com.hit.ticketbookingclient.controllers;

import com.google.gson.reflect.TypeToken;
import com.hit.ticketbookingclient.models.ApiRequest;
import com.hit.ticketbookingclient.models.ApiResponse;
import com.hit.ticketbookingclient.models.Show;
import com.hit.ticketbookingclient.models.Seat;
import com.hit.ticketbookingclient.network.NetworkClient;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ShowsController {

    @FXML private TableView<Show> showsTable;
    @FXML private TableColumn<Show, Integer> idCol;
    @FXML private TableColumn<Show, String> titleCol;
    @FXML private TableColumn<Show, String> timeCol;
    @FXML private TableColumn<Show, String> venueCol;
    @FXML private TableColumn<Show, String> ticketsCol;
    @FXML private Label statusLabel;
    @FXML private javafx.scene.layout.HBox adminPanel;

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        titleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        timeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getShowTime().format(formatter)));
        venueCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVenue()));

        ticketsCol.setCellValueFactory(cellData -> {
            Show show = cellData.getValue();
            if (show.getSeats() == null || show.getSeats().isEmpty()) {
                return new SimpleStringProperty("N/A");
            }
            int totalSeats = show.getSeats().size();
            int bookedSeats = 0;
            for (Seat seat : show.getSeats()) {
                if (!seat.isAvailable()) {
                    bookedSeats++;
                }
            }
            return new SimpleStringProperty(bookedSeats + " / " + totalSeats);
        });
        loadShows();
    }

    @FXML
    public void loadShows() {
        statusLabel.setText("Loading shows from server...");
        ApiRequest request = new ApiRequest("GET_ALL_SHOWS", null);
        ApiResponse response = NetworkClient.getInstance().sendRequest(request);
        if (response.isSuccess() && response.getData() != null) {
            try {
                Type listType = new TypeToken<List<Show>>(){}.getType();
                List<Show> showsList = NetworkClient.getInstance().getGson().fromJson(response.getData(), listType);
                ObservableList<Show> observableShows = FXCollections.observableArrayList(showsList);
                showsTable.setItems(observableShows);
                statusLabel.setText("");
            } catch (Exception e) {
                statusLabel.setText("Error parsing data from server.");
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("Failed to load shows: " + response.getMessage());
        }
    }

    @FXML
    public void goToBookingView(javafx.event.ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/com/hit/ticketbookingclient/MainView.fxml"));
            javafx.scene.Parent root = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root, 400, 500));
            stage.show();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading booking view.");
        }
    }

    @FXML
    public void toggleAdminMode() {
        if (adminPanel.isVisible()) {
            adminPanel.setVisible(false);
            adminPanel.setManaged(false);
            statusLabel.setTextFill(javafx.scene.paint.Color.BLUE);
            statusLabel.setText("Admin mode disabled.");
            return;
        }

        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Admin Access");
        dialog.setHeaderText("Restricted Area");
        dialog.setContentText("Enter Admin Password:");

        java.util.Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals("admin123")) { // password = admin123
            adminPanel.setVisible(true);
            adminPanel.setManaged(true);
            statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            statusLabel.setText("Admin mode unlocked!");
        }
        else {
            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            statusLabel.setText("Incorrect password.");
        }
    }

    @FXML
    public void resetShowByName() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Reset Show");
        dialog.setHeaderText("Resetting a show will cancel all its tickets.");
        dialog.setContentText("Enter the exact Show Title:");
        java.util.Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String titleToReset = result.get().trim();
            com.google.gson.JsonElement payload = NetworkClient.getInstance().getGson().toJsonTree(titleToReset);
            ApiRequest request = new ApiRequest("RESET_SHOW", payload);
            ApiResponse response = NetworkClient.getInstance().sendRequest(request);
            if (response.isSuccess()) {
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                statusLabel.setText("Show '" + titleToReset + "' was reset completely.");
                loadShows();
            }
            else {
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                statusLabel.setText("Error resetting show: " + response.getMessage());
            }
        }
    }

    @FXML
    public void addNewShow() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog("New Concert,Tel Aviv,25/12/2026 20:30,50,150.0");
        dialog.setTitle("Add New Show");
        dialog.setHeaderText("Enter details separated by commas:\nFormat: Title, Venue, Date (dd/MM/yyyy HH:mm), Total Seats, Base Price");
        dialog.setContentText("Details:");
        java.util.Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String[] parts = result.get().split(",");
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Missing arguments");
                }
                String title = parts[0].trim();
                String venue = parts[1].trim();
                String dateStr = parts[2].trim();
                int seatCount = Integer.parseInt(parts[3].trim());
                double basePrice = Double.parseDouble(parts[4].trim());
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                java.time.LocalDateTime showTime = java.time.LocalDateTime.parse(dateStr, formatter);

                java.util.List<Seat> newSeats = new java.util.ArrayList<>();
                for (int i = 1; i <= seatCount; i++) {
                    double finalPrice = basePrice;
                    if (i <= 5){
                        finalPrice = basePrice * 2;
                    }
                    else if (i > seatCount - 5) {
                        finalPrice = basePrice * 0.8;
                    }
                    newSeats.add(new Seat(1, i, finalPrice, true));
                }
                int maxId = 100;
                for (Show s : showsTable.getItems()) {
                    if (s.getId() > maxId) {
                        maxId = s.getId();
                    }
                }
                int newId = maxId + 1;
                Show newShow = new Show(newId, title, "Admin Created Show", showTime, venue, newSeats);
                com.google.gson.JsonElement payload = NetworkClient.getInstance().getGson().toJsonTree(newShow);
                ApiRequest request = new ApiRequest("ADD_SHOW", payload);
                ApiResponse response = NetworkClient.getInstance().sendRequest(request);
                if (response.isSuccess()) {
                    statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                    statusLabel.setText("New show added successfully!");
                    loadShows();
                }
            }
            catch (Exception e) {
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                statusLabel.setText("Error. Please use exact format: Title, Venue, dd/MM/yyyy HH:mm, Seats, Price");
            }
        }
    }

    @FXML
    public void deleteShowByName() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
        dialog.setTitle("Delete Show");
        dialog.setHeaderText("DANGER: This will completely DELETE the show and all its tickets!");
        dialog.setContentText("Enter the exact Show Title to DELETE:");
        java.util.Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String titleToDelete = result.get().trim();
            com.google.gson.JsonElement payload = NetworkClient.getInstance().getGson().toJsonTree(titleToDelete);
            ApiRequest request = new ApiRequest("DELETE_SHOW", payload);
            ApiResponse response = NetworkClient.getInstance().sendRequest(request);
            if (response.isSuccess()) {
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                statusLabel.setText("Show '" + titleToDelete + "' was deleted completely.");
                loadShows();
            }
            else {
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                statusLabel.setText("Error deleting show: " + response.getMessage());
            }
        }
    }
}