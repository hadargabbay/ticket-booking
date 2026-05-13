package controllers;

import models.Ticket;
import service.BookingService;
import java.util.List;

public class TicketController implements IController<Ticket> {
    private final BookingService bookingService;

    // Keeps a reference to the service that owns ticket and booking rules.
    public TicketController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Persists a ticket and its seat state through the booking service.
    @Override
    public String save(Ticket ticket) {
        try {
            bookingService.bookTicket(ticket);
            return "SUCCESS: Ticket saved successfully.";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // Not used in this project yet.
    @Override
    public String update(Ticket ticket) { return "Not implemented"; }

    // Not used in this project yet.
    @Override
    public String delete(int id) { return "Not implemented"; }

    // Not used in this project yet.
    @Override
    public Ticket getById(int id) { return null; }

    // Listing all tickets is not exposed through this controller.
    @Override
    public List<Ticket> getAll() { return null; }

    // Resolves the customer by name (creating if needed), then books the requested number of seats.
    public List<Ticket> bookBestSeat(String showTitle, String customerName, String algoPref, int amount) {
        models.Customer customer = bookingService.getOrCreateCustomer(customerName);
        return bookingService.bookBestSeat(showTitle, customer.getId(), algoPref, amount);
    }

    // Undoes a booking and frees the seat on the stored show.
    public void cancelTicket(Ticket ticket) {
        bookingService.cancelTicket(ticket);
    }

    // Clears all bookings for one show title and marks seats available again.
    public void resetShow(String showTitle) {
        bookingService.resetShow(showTitle);
    }

    // Removes the show and all its tickets from storage.
    public void deleteShow(String showTitle) {
        bookingService.deleteShow(showTitle);
    }
}