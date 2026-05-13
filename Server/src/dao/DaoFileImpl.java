package dao;

import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** Saves all data in one binary file using Java serialization. */
public class DaoFileImpl implements IDao {
    private String filePath;

    private List<Customer> customers;
    private List<Show> shows;
    private List<Ticket> tickets;

    // Opens storage at a custom path and tries to load existing data from disk.
    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        this.customers = new ArrayList<>();
        this.shows = new ArrayList<>();
        this.tickets = new ArrayList<>();
        loadData();
    }

    // Uses the default file name {@code datasource.txt} in the working directory.
    public DaoFileImpl() {
        this("datasource.txt");
    }

    /** Finds the first show whose title matches, ignoring case differences. */
    @Override
    public Show getShowByTitle(String title) {
        for (Show show : shows) {
            if (show.getTitle().equalsIgnoreCase(title)) {
                return show;
            }
        }
        return null;
    }

    // Inserts a new customer or updates an existing one with the same id, then saves the file.
    @Override
    public void saveCustomer(Customer customer) {
        if (customer != null) {
            // Check if customer already exists (update) or add new
            boolean found = false;
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId() == customer.getId()) {
                    customers.set(i, customer);
                    found = true;
                    break;
                }
            }
            if (!found) {
                customers.add(customer);
            }
            saveData();
        }
    }

    // Returns the customer with the given id, or null if not found.
    @Override
    public Customer getCustomer(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    // Returns a defensive copy of the customer list.
    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    // Delegates to saveCustomer so updates stay consistent.
    @Override
    public void updateCustomer(Customer customer) {
        saveCustomer(customer);
    }

    // Removes a customer by id and saves the file.
    @Override
    public void deleteCustomer(int id) {
        customers.removeIf(customer -> customer.getId() == id);
        saveData();
    }

    // Inserts a new show or updates an existing one with the same id, then saves the file.
    @Override
    public void saveShow(Show show) {
        if (show != null) {
            // Check if show already exists (update) or add new
            boolean found = false;
            for (int i = 0; i < shows.size(); i++) {
                if (shows.get(i).getId() == show.getId()) {
                    shows.set(i, show);
                    found = true;
                    break;
                }
            }
            if (!found) {
                shows.add(show);
            }
            saveData();
        }
    }

    // Returns the show with the given id, or null if not found.
    @Override
    public Show getShow(int id) {
        for (Show show : shows) {
            if (show.getId() == id) {
                return show;
            }
        }
        return null;
    }

    // Returns a defensive copy of the show list.
    @Override
    public List<Show> getAllShows() {
        return new ArrayList<>(shows);
    }

    // Delegates to saveShow so updates stay consistent.
    @Override
    public void updateShow(Show show) {
        saveShow(show);
    }

    // Removes a show by id and saves the file.
    @Override
    public void deleteShow(int id) {
        shows.removeIf(show -> show.getId() == id);
        saveData();
    }

    // Inserts a new ticket or updates an existing one with the same id, then saves the file.
    @Override
    public void saveTicket(Ticket ticket) {
        if (ticket != null) {
            // Check if ticket already exists (update) or add new
            boolean found = false;
            for (int i = 0; i < tickets.size(); i++) {
                if (tickets.get(i).getId() == ticket.getId()) {
                    tickets.set(i, ticket);
                    found = true;
                    break;
                }
            }
            if (!found) {
                tickets.add(ticket);
            }
            saveData();
        }
    }

    // Returns the ticket with the given id, or null if not found.
    @Override
    public Ticket getTicket(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    // Returns a defensive copy of the ticket list.
    @Override
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    // Delegates to saveTicket so updates stay consistent.
    @Override
    public void updateTicket(Ticket ticket) {
        saveTicket(ticket);
    }

    // Removes a ticket by id and saves the file.
    @Override
    public void deleteTicket(int id) {
        tickets.removeIf(ticket -> ticket.getId() == id);
        saveData();
    }

    // Reads the binary file into the three lists; starts empty if the file is missing or unreadable.
    @Override
    public void loadData() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read the data in the order it was written
            customers = (List<Customer>) ois.readObject();
            shows = (List<Show>) ois.readObject();
            tickets = (List<Ticket>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File doesn't exist, start with empty lists
            customers = new ArrayList<>();
            shows = new ArrayList<>();
            tickets = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
            e.printStackTrace();
            // init with empty lists if loading fails
            customers = new ArrayList<>();
            shows = new ArrayList<>();
            tickets = new ArrayList<>();
        }
    }

    // Writes customers, shows, and tickets to the binary file in one shot.
    @Override
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Write all data to the file
            oos.writeObject(customers);
            oos.writeObject(shows);
            oos.writeObject(tickets);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Removes any show whose title matches, ignoring case, then saves.
    @Override
    public void deleteShowByTitle(String title) {
        shows.removeIf(show -> show.getTitle().equalsIgnoreCase(title));
        saveData();
    }

    // Removes tickets whose linked show has the given title (ignoring case), then saves.
    @Override
    public void deleteTicketsByShowTitle(String title) {
        tickets.removeIf(ticket -> ticket.getShow().getTitle().equalsIgnoreCase(title));
        saveData();
    }
}
