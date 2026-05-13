package com.hit.ticketbookingclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the JavaFX Client application.
 * Initializes the primary stage and loads the initial booking view.
 */
public class ClientDriver extends Application {

    /**
     * Builds the first window: loads the main booking FXML and shows it on the stage.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientDriver.class.getResource("/com/hit/ticketbookingclient/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 500);
        stage.setTitle("Ticket Booking System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /** JavaFX entry point: hands control to the toolkit which then calls {@link #start(Stage)}. */
    public static void main(String[] args) {
        launch(args);
    }
}