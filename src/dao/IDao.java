package dao;

import models.*;

import java.util.List;

public interface IDao {
    // Customer operations
    void saveCustomer(Customer customer);
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);

    // Show operations
    void saveShow(Show show);
    Show getShow(int id);
    List<Show> getAllShows();
    void updateShow(Show show);
    void deleteShow(int id);

    // Ticket operations
    void saveTicket(Ticket ticket);
    Ticket getTicket(int id);
    List<Ticket> getAllTickets();
    void updateTicket(Ticket ticket);
    void deleteTicket(int id);

    // Load and save all data
    void loadData();
    void saveData();
}

