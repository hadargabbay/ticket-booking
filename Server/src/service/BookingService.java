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

    // Connects this service to storage and the default seat-picking strategy used when none is overridden per request.
    public BookingService(IDao dao, IAlgoBooking algorithm) {
        this.dao = dao;
        this.algorithm = algorithm;
    }

    // Switches the default seat algorithm used by the console app (not always used for each network request).
    public void setAlgorithm(IAlgoBooking algorithm) {
        this.algorithm = algorithm;
    }

    // Books several seats for one customer on one show, using FIRST_AVAILABLE or CHEAPEST logic per seat.
    // Returns null if the show or customer is missing, or if not enough free seats exist.
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

    // Stores a ticket that was already built elsewhere and refreshes the show in storage.
    public void bookTicket(Ticket ticket) {
        dao.saveTicket(ticket);
        dao.updateShow(ticket.getShow());
    }

    // Returns a copy of every show the DAO knows about.
    public List<Show> getAllShows() {
        return dao.getAllShows();
    }

    // Adds a new show or updates an existing one through the DAO.
    public void addShow(Show show) {
        dao.saveShow(show);
    }

    // Frees the booked seat on the show and removes the ticket record so the seat can be sold again.
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

    // Finds a customer by name (ignoring case) or creates a minimal new customer with a new id.
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

    // Marks every seat on the show as free and deletes all tickets for that show title.
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

    // Removes all tickets for the show, then removes the show itself from storage.
    public void deleteShow(String showTitle) {
        dao.deleteTicketsByShowTitle(showTitle);
        dao.deleteShowByTitle(showTitle);
    }
}
