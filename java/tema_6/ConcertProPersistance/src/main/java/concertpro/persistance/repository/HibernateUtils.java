package concertpro.persistance.repository;

import concertpro.model.Order;
import concertpro.model.Spectacle;
import concertpro.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if((sessionFactory == null) || (sessionFactory.isClosed())) {
            sessionFactory = createNewSessionFactory();
        }

        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory() {
        sessionFactory = new Configuration()
            .addAnnotatedClass(Spectacle.class)
            .addAnnotatedClass(Order.class)
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

        return sessionFactory;
    }

    public static void closeSessionFactory() {
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
