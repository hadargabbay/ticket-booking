package dao;

import models.*;

import java.util.List;

/** Defines every read/write operation the app needs on customers, shows, and tickets. */
public interface IDao {
    /** Saves a new customer or replaces one that has the same id. */
    void saveCustomer(Customer customer);
    /** Looks up one customer by numeric id, or returns null if none match. */
    Customer getCustomer(int id);
    /** Returns every customer currently in memory. */
    List<Customer> getAllCustomers();
    /** Updates an existing customer record (same as save when id matches). */
    void updateCustomer(Customer customer);
    /** Removes one customer by id and persists the change. */
    void deleteCustomer(int id);

    /** Saves a new show or replaces one that has the same id. */
    void saveShow(Show show);
    /** Finds a show by its numeric id. */
    Show getShow(int id);
    /** Returns every show currently in memory. */
    List<Show> getAllShows();
    /** Writes the latest version of a show back to storage. */
    void updateShow(Show show);
    /** Deletes a show by numeric id. */
    void deleteShow(int id);
    /** Finds a show by its title, ignoring upper/lower case. */
    Show getShowByTitle(String title);
    /** Deletes a show by matching title (ignoring case). */
    void deleteShowByTitle(String title);

    /** Saves a new ticket or replaces one that has the same id. */
    void saveTicket(Ticket ticket);
    /** Finds a ticket by its numeric id. */
    Ticket getTicket(int id);
    /** Returns every ticket currently in memory. */
    List<Ticket> getAllTickets();
    /** Updates an existing ticket record. */
    void updateTicket(Ticket ticket);
    /** Deletes one ticket by id. */
    void deleteTicket(int id);
    /** Removes every ticket linked to a given show title. */
    void deleteTicketsByShowTitle(String title);

    /** Loads customers, shows, and tickets from the binary file into memory. */
    void loadData();
    /** Writes all in-memory data to the binary file. */
    void saveData();
}
