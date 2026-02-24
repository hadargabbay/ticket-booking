package controllers;

import models.Ticket;
import service.BookingService;
import java.util.List;

public class TicketController implements IController<Ticket> {
    private final BookingService bookingService;

    public TicketController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public String save(Ticket ticket) {
        try {
            bookingService.bookTicket(ticket);
            return "SUCCESS: Ticket saved successfully.";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @Override
    public String update(Ticket ticket) { return "Not implemented"; }

    @Override
    public String delete(int id) { return "Not implemented"; }

    @Override
    public Ticket getById(int id) { return null; }

    @Override
    public List<Ticket> getAll() { return null; }

    public List<Ticket> bookBestSeat(String showTitle, String customerName, String algoPref, int amount) {
        models.Customer customer = bookingService.getOrCreateCustomer(customerName);
        return bookingService.bookBestSeat(showTitle, customer.getId(), algoPref, amount);
    }
    public void cancelTicket(Ticket ticket) {
        bookingService.cancelTicket(ticket);
    }
    public void resetShow(String showTitle) {
        bookingService.resetShow(showTitle);
    }
    public void deleteShow(String showTitle) {
        bookingService.deleteShow(showTitle);
    }
}