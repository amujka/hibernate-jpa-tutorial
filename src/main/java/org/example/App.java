package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.math.BigDecimal;

public class App {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

        Product product = new Product("Monitor", new BigDecimal("299.99"));
        addProduct(product);

        updateProduct(product.getId(), "Laptop", new BigDecimal("899.99"));

        Product selectedProduct = selectProduct(product.getId());
        System.out.println(selectedProduct);

        deleteProduct(product.getId());
    }

    public static void addProduct(Product product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            System.out.println("added product: " + product);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public static void updateProduct(Long id, String newName, BigDecimal newPrice) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                product.setName(newName);
                product.setPrice(newPrice);
                session.update(product);
                transaction.commit();
                System.out.println("product updated");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public static Product selectProduct(Long id) {
        Session session = sessionFactory.openSession();
        Product product = null;

        try {
            product = session.get(Product.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
        return product;
    }

    public static void deleteProduct(Long id) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                transaction.commit();
                System.out.println("product deleted");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}
