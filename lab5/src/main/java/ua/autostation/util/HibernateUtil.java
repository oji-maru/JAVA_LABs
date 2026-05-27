package ua.autostation.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.autostation.entity.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Завантажує параметри з файлу hibernate.properties та додає класи
            sessionFactory = new Configuration()
                    .addAnnotatedClass(Passport.class)
                    .addAnnotatedClass(Truck.class)
                    .addAnnotatedClass(Driver.class)
                    .addAnnotatedClass(TripRequest.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Помилка ініціалізації SessionFactory:" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() { return sessionFactory; }
}