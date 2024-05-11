package concertpro.persistance.repository.spectacle_repository;

import concertpro.model.Spectacle;
import concertpro.persistance.repository.HibernateUtils;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Objects;

public class SpectacleHibernateRepository implements ISpectacleRepository {
    @Override
    public Spectacle findOne(Long aLong) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Spectacle where id=:idM ", Spectacle.class)
                    .setParameter("idM", aLong)
                    .getSingleResultOrNull();
        }
    }

    @Override
    public Iterable<Spectacle> findAll() {
        try(Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Spectacle ", Spectacle.class).getResultList();
        }
    }

    @Override
    public void save(Spectacle elem) {

    }

    @Override
    public void update(Spectacle elem, Long aLong) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Spectacle.class, aLong))) {
                System.out.println("In update, am gasit spectacolul cu id-ul "+aLong);
                session.merge(elem);
                session.flush();
            }
        });
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Iterable<Spectacle> getAllByDate(LocalDate date) {
        try(Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Spectacle where spectacleDate=:date", Spectacle.class)
                    .setParameter("date", date)
                    .getResultList();
        }
    }
}
