package ua.autostation.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.autostation.util.HibernateUtil;

//LB6
import java.util.List;
import ua.autostation.entity.Driver;
import ua.autostation.entity.TripRequest;


public class GenericDAO<T> {
    private final Class<T> type;

    public GenericDAO(Class<T> type) { this.type = type; }

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // реалізація через Native SQL
    public List<Driver> findDriversByNameNative(String searchName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String sql = "SELECT * FROM drivers WHERE name LIKE :driverName";
            return session.createNativeQuery(sql, Driver.class)
                    .setParameter("driverName", "%" + searchName + "%")
                    .getResultList();
        }
    }
    // реалізація через HQL
    public List<TripRequest> findTripsByDestinationHQL(String city) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM TripRequest t WHERE t.destination = :dest";
            return session.createQuery(hql, TripRequest.class)
                    .setParameter("dest", city)
                    .getResultList();
        }
    }
    public T findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(this.type, id);
        }
    }
}