package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JPAOrder {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaExampleUnit");

    public void createOrder(Order order){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(order);
            transaction.commit();
            em.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
            transaction.rollback();
        }finally {
            em.close();
        }
    }

    public Order getOrderById(Long id){
        EntityManager em = emf.createEntityManager();
        Order order = em.find(Order.class,id);
        em.close();
        return order;
    }

    public void updateOrder(Order order){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            em.merge(order);
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            transaction.rollback();
        }finally {
            em.close();
        }
    }

    public void deleteOrderById(Long id){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            Order order = em.find(Order.class,id);
            if (order != null) {
                em.remove(order);
                System.out.println("Order " + order.getOrderNumber() + " is removed");
            }
            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            transaction.rollback();
        }finally {
            em.close();
        }
    }
}
