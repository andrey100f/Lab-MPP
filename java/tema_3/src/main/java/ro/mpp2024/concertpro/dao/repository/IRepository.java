package ro.mpp2024.concertpro.dao.repository;

import ro.mpp2024.concertpro.dao.model.Entity;

public interface IRepository<T extends Entity<ID>, ID> {
    T findOne(ID id);
    Iterable<T> findAll();
    void save(T elem);
    void update(T elem, ID id);
    void delete(ID id);
}
