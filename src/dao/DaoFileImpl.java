package dao;

import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoFileImpl implements IDao {
    private static final String DATA_FILE = "datasource.txt";
    
    private List<Customer> customers;
    private List<Show> shows;
    private List<Ticket> tickets;

    public DaoFileImpl() {
        this.customers = new ArrayList<>();
        this.shows = new ArrayList<>();
        this.tickets = new ArrayList<>();
        loadData();
    }

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

    @Override
    public Customer getCustomer(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    @Override
    public void updateCustomer(Customer customer) {
        saveCustomer(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        customers.removeIf(customer -> customer.getId() == id);
        saveData();
    }

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

    @Override
    public Show getShow(int id) {
        for (Show show : shows) {
            if (show.getId() == id) {
                return show;
            }
        }
        return null;
    }

    @Override
    public List<Show> getAllShows() {
        return new ArrayList<>(shows);
    }

    @Override
    public void updateShow(Show show) {
        saveShow(show);
    }

    @Override
    public void deleteShow(int id) {
        shows.removeIf(show -> show.getId() == id);
        saveData();
    }

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

    @Override
    public Ticket getTicket(int id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        saveTicket(ticket);
    }

    @Override
    public void deleteTicket(int id) {
        tickets.removeIf(ticket -> ticket.getId() == id);
        saveData();
    }

    @Override
    public void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            // File doesn't exist yet, start with empty lists
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
            // Initialize with empty lists if loading fails
            customers = new ArrayList<>();
            shows = new ArrayList<>();
            tickets = new ArrayList<>();
        }
    }

    @Override
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
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
}

