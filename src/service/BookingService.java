package service;

import dao.IDao;
import algorithms.IAlgoBooking;
import models.*;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private IDao dao;
    private IAlgoBooking algorithm;

    public BookingService(IDao dao, IAlgoBooking algorithm) { // constructor
        this.dao = dao;
        this.algorithm = algorithm;
    }

    public void setAlgorithm(IAlgoBooking algorithm) { // change the algo based on what we need

        this.algorithm = algorithm;
    }

    public Ticket bookBestSeat(String showTitle, int customerId) {
        Show show = dao.getShowByTitle(showTitle);
        Customer customer = dao.getCustomer(customerId);
        if (show == null || customer == null) {
            return null;
        }
        Seat bestSeat = algorithm.findBestSeat(show.getSeats());

        if (bestSeat != null) {
            bestSeat.setAvailable(false);

            int ticketId = dao.getAllTickets().size() + 1;
            Ticket newTicket = new Ticket(ticketId, customer, show, bestSeat,
                    java.time.LocalDateTime.now(), bestSeat.getPrice(), "CONFIRMED");

            dao.updateShow(show);
            dao.saveTicket(newTicket);
            return newTicket;
        }
        return null;
    }

    public void bookTicket(Ticket ticket)
    {
        // קריאה ל-DAO כדי לשמור את הכרטיס החדש במקור הנתונים
        dao.saveTicket(ticket);
        // עדכון המופע במידת הצורך
        dao.updateShow(ticket.getShow());
    }
}