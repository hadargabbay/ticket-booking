package com.hit.ticketbookingclient;

import javafx.application.Application;

public class Launcher {
    // Starts JavaFX using the real Application class;
    // needed on some JDK setups where the module main class differs.
    public static void main(String[] args) {
        Application.launch(ClientDriver.class, args);
    }
}
