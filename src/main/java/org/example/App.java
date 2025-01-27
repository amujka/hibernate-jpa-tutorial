package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.math.BigDecimal;

public class App {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

        Product product = addProduct("Monitor", new BigDecimal("299.99"));
        System.out.println(product);

        updateProduct(product.getId(), "Laptop", new BigDecimal("899.99"));

        Product selectedProduct = selectProduct(product.getId());
        System.out.println(selectedProduct);

        deleteProduct(product.getId());
    }

    public static Product addProduct(String name, BigDecimal price) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Product product = null;

        try {
            transaction = session.beginTransaction();
            product = new Product();
            product.setName(name);
            product.setPrice(price);
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    public static void updateProduct(Long id, String newName, BigDecimal newPrice) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                product.setName(newName);
                product.setPrice(newPrice);
                session.update(product);  // Updating the product
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static Product selectProduct(Long id) {
        Session session = sessionFactory.openSession();
        Product product = null;

        try {
            product = session.get(Product.class, id);  // Retrieving product by ID
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }

    public static void deleteProduct(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);  // Deleting the product
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
