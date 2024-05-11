package concertpro.persistance.repository.user_repository;

import concertpro.model.User;
import concertpro.persistance.repository.HibernateUtils;
import org.hibernate.Session;

import java.util.Objects;

public class UserHibernateRepository implements IUserRepository {
    @Override
    public User findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void save(User elem) {

    }

    @Override
    public void update(User elem, Long aLong) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if(!Objects.isNull(session.find(User.class, aLong))) {
                System.out.println("In update, am gasit userul cu id-ul " + aLong);
                session.merge(elem);
                session.flush();
            }

        });
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from User where username = :username and password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResultOrNull();
        }
    }
}
