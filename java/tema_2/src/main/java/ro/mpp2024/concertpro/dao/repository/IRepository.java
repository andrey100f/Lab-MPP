package ro.mpp2024.concertpro.dao.repository;

public interface IRepository<T, ID> {
    T findOne(ID id);
    Iterable<T> findAll();
    void save(T elem);
    void update(T elem, ID id);
    void delete(ID id);
}
