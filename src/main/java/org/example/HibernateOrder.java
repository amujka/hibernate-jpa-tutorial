package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateOrder {
    private SessionFactory sessionFactory;

    public HibernateOrder() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void close() {
        sessionFactory.close();
    }

    public void addOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(order);
        transaction.commit();
        session.close();
    }

    public Order getOrderById(Long id) {
        Session session = sessionFactory.openSession();
        Order order = session.get(Order.class, id);
        session.close();
        return order;
    }


    public void updateOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }

    public void deleteOrderById(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String hql = "DELETE FROM Order WHERE id =:id";
            session.createQuery(hql)
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
