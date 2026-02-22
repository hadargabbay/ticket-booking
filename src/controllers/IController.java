package controllers;

import java.util.List;

public interface IController<T> {
    String save(T entity);
    String update(T entity);
    String delete(int id);
    T getById(int id);
    List<T> getAll();

    /**
     * Books the best available seat for a show and customer.
     * Returns the created Ticket or null if booking failed.
     */
    default T bookBestSeat(String showTitle, int customerId) {
        return null;
    }
}
