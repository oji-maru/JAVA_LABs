package ua.autostation.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ua.autostation.util.HibernateUtil;

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

    public T findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(this.type, id);
        }
    }
}