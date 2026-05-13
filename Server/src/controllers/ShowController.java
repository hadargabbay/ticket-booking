package controllers;

import models.Show;
import service.BookingService;
import java.util.List;

public class ShowController implements IController<Show> {
    private final BookingService bookingService;

    // Keeps a reference to the service that owns show-related business rules.
    public ShowController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Adds the show through the booking service and reports success or failure as text.
    @Override
    public String save(Show show) {
        try {
            bookingService.addShow(show);
            return "SUCCESS: Show added successfully.";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // Not used in this project yet.
    @Override
    public String update(Show show) { return "Not implemented"; }

    // Not used in this project yet.
    @Override
    public String delete(int id) { return "Not implemented"; }

    // Not used in this project yet.
    @Override
    public Show getById(int id) { return null; }

    // Returns every show from the booking service for API responses.
    @Override
    public List<Show> getAll() {
        return bookingService.getAllShows();
    }
}