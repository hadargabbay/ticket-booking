package controllers;

import service.BookingService;
import models.Ticket;
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
    public String update(Ticket ticket) {
        return "Update functionality not yet implemented";
    }

    @Override
    public String delete(int id) {
        return "Delete functionality not yet implemented";
    }

    @Override
    public Ticket getById(int id) {
        return null; // ימומש בהמשך לפי הצורך
    }

    @Override
    public List<Ticket> getAll() {
        return null; // ימומש בהמשך לפי הצורך
    }
}