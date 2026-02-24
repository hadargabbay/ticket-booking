module com.hit.ticketbookingclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.hit.ticketbookingclient to javafx.fxml;
    exports com.hit.ticketbookingclient;
    exports com.hit.ticketbookingclient.controllers;
    opens com.hit.ticketbookingclient.controllers to javafx.fxml;
    opens com.hit.ticketbookingclient.models to com.google.gson;
}