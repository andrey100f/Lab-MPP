package concertpro.persistance.repository.order_repository;

import concertpro.model.Order;
import concertpro.persistance.repository.HibernateUtils;
import concertpro.validator.Validator;

public class OrderHibernateRepository implements IOrderRepository {

    private final Validator<Order> orderValidator;

    public OrderHibernateRepository(Validator<Order> orderValidator) {
        this.orderValidator = orderValidator;
    }

    @Override
    public Order findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Order> findAll() {
        return null;
    }

    @Override
    public void save(Order elem) {
        this.orderValidator.validate(elem);

        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(elem));
    }

    @Override
    public void update(Order elem, Long aLong) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
