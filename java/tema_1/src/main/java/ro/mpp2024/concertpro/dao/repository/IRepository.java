package ro.mpp2024.concertpro.dao.repository;

import java.util.List;

public interface IRepository<T> {
    T getById();
    List<T> getAll();
    T add(T entity);
    T update(T entity);
    void delete(Long id);
}
