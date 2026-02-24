package controllers;

import models.Show;
import service.BookingService;
import java.util.List;

public class ShowController implements IController<Show> {
    private final BookingService bookingService;

    public ShowController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String save(Show show) {
        try {
            bookingService.addShow(show);
            return "SUCCESS: Show added successfully.";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @Override
    public String update(Show show) { return "Not implemented"; }

    @Override
    public String delete(int id) { return "Not implemented"; }

    @Override
    public Show getById(int id) { return null; }

    @Override
    public List<Show> getAll() {
        return bookingService.getAllShows();
    }
}