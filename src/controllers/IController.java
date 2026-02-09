package controllers;

import java.util.List;

public interface IController<T> {
    String save(T entity);
    String update(T entity);
    String delete(int id);
    T getById(int id);
    List<T> getAll();
}
