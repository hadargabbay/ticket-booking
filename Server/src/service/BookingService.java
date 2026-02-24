package service;

import dao.IDao;
import algorithms.IAlgoBooking;
import algorithms.CheapestSeatAlgo;
import algorithms.FirstAvailableAlgo;
import models.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Core business logic and mediator between controllers and DAO

public class BookingService {
    private IDao dao;
    private IAlgoBooking algorithm;

    public BookingService(IDao dao, IAlgoBooking algorithm) {
        this.dao = dao;
        this.algorithm = algorithm;
    }

    public void setAlgorithm(IAlgoBooking algorithm) {
        this.algorithm = algorithm;
    }
    // Books tickets
    public List<Ticket> bookBestSeat(String showTitle, int customerId, String algoPref, int amount) {
        Show show = dao.getShowByTitle(showTitle);
        Customer customer = dao.getCustomer(customerId);

        if (show == null || customer == null) {
            return null;
        }
        // Validate enough seats are available before booking
        int availableSeats = 0;
        for (Seat seat : show.getSeats()) {
            if (seat.isAvailable()) {
                availableSeats++;
            }
        }
        if (availableSeats < amount) {
            return null;// Not enough seats
        }

        IAlgoBooking selectedAlgo;
        if ("FIRST_AVAILABLE".equals(algoPref)) {
            selectedAlgo = new FirstAvailableAlgo();
        }
        else {
            selectedAlgo = new CheapestSeatAlgo();
        }

        List<Ticket> bookedTickets = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            Seat bestSeat = selectedAlgo.findBestSeat(show.getSeats());

            if (bestSeat != null) {
                bestSeat.setAvailable(false);
                int ticketId = dao.getAllTickets().size() + 1;
                Ticket newTicket = new Ticket(ticketId, customer, show, bestSeat,
                        LocalDateTime.now(), bestSeat.getPrice(), "CONFIRMED");

                dao.updateShow(show);
                dao.saveTicket(newTicket);
                bookedTickets.add(newTicket);
            }
            else {
                break;
            }
        }
        return bookedTickets.isEmpty() ? null : bookedTickets;
    }

    public void bookTicket(Ticket ticket) {
        dao.saveTicket(ticket);
        dao.updateShow(ticket.getShow());
    }

    public List<Show> getAllShows() {
        return dao.getAllShows();
    }

    public void addShow(Show show) {
        dao.saveShow(show);
    }
    // Cancels a ticket and frees the seat
    public void cancelTicket(Ticket ticket) {
        if (ticket == null || ticket.getShow() == null || ticket.getSeat() == null) return;
        Show show = dao.getShowByTitle(ticket.getShow().getTitle());
        if (show != null) {
            for (Seat s : show.getSeats()) {
                if (s.getNumber() == ticket.getSeat().getNumber()) {
                    s.setAvailable(true);
                    break;
                }
            }
            dao.updateShow(show);
        }
        List<Ticket> allTickets = dao.getAllTickets();
        if (allTickets != null) {
            allTickets.removeIf(t -> t.getId() == ticket.getId());
        }
    }

    public models.Customer getOrCreateCustomer(String name) {
        for (models.Customer c : dao.getAllCustomers()) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        int newId = dao.getAllCustomers().size() + 1;
        models.Customer newCustomer = new models.Customer(newId, name, "", "");
        dao.saveCustomer(newCustomer);
        return newCustomer;
    }
    // Resets a show by clearing all its tickets
    public void resetShow(String showTitle) {
        Show targetShow = dao.getShowByTitle(showTitle);

        if (targetShow != null) {
            for (Seat seat : targetShow.getSeats()) {
                seat.setAvailable(true);
            }
            dao.updateShow(targetShow);
            dao.deleteTicketsByShowTitle(showTitle);
        }
    }
    // deletes a show and its tickets
    public void deleteShow(String showTitle) {
        dao.deleteTicketsByShowTitle(showTitle);
        dao.deleteShowByTitle(showTitle);
    }
}