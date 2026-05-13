package controllers;

import java.util.List;

/** Common shape for a controller that can save, read, and delete one kind of entity. */
public interface IController<T> {
    // Tries to create or persist an entity; returns a short text starting with SUCCESS or ERROR.
    String save(T entity);
    // Reserved for future use: would change an existing entity.
    String update(T entity);
    // Reserved for future use: would remove an entity by id.
    String delete(int id);
    // Returns one entity by id, or null if not implemented or not found.
    T getById(int id);
    // Returns all entities of this type, or null if not supported.
    List<T> getAll();

    // Optional helper for simple booking flows; default does nothing and returns null.
    // Books the best available seat for a show and customer.
    default T bookBestSeat(String showTitle, int customerId) {
        return null;
    }
}
