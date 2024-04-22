package concertpro.persistance.repository;

import concertpro.model.Entity;

public interface IRepository<T extends Entity<ID>, ID> {

    T findOne(ID id);
    Iterable<T> findAll();
    void save(T elem);
    void update(T elem, ID id);
    void delete(ID id);

}
