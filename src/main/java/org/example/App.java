package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

    }

    public static void createAllEntities(){
        Session session= sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Author author1 = new Author("Alen Alenic");
        Author author2 = new Author("Ana Anic");

        Book book1 = new Book("Book about nothing", author1);
        Book book2 = new Book("Book about Hibernate", author2);

        Publisher publisher1 = new Publisher("School Book");
        Publisher publisher2 = new Publisher("Algorithm");

        author1.getBooks().add(book1);
        author1.getPublishers().add(publisher1);

        author2.getBooks().add(book2);
        author2.getPublishers().add(publisher2);

        publisher1.getAuthors().add(author1);
        publisher2.getAuthors().add(author2);

        session.persist(author1);
        session.persist(author2);
        session.persist(book1);
        session.persist(book2);
        session.persist(publisher1);
        session.persist(publisher2);

        transaction.commit();
        session.close();
    }

    public static void getAuthors() {
        Session session = sessionFactory.openSession();
        List<Author> authors = session.createQuery("FROM Author a JOIN FETCH a.books", Author.class).list();
        for (Author author : authors) {
            System.out.println("Author: " + author.getName());
            for (Book book : author.getBooks()) {
                System.out.println("  Book: " + book.getTitle());
            }
        }
        session.close();
    }

    public static void updateBookTitle(Long id, String newTitle) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("UPDATE Book b SET b.title=:title WHERE b.id=:id", Book.class)
                .setParameter("title", newTitle)
                .setParameter("id", id);
        transaction.commit();
        session.close();
        System.out.println("Book title updated.");
    }

    public static void deleteBookById(Long id){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Book b WHERE b.id = :bookId", Book.class)
                .setParameter("bookId", id)
                .executeUpdate();
        transaction.commit();
        session.close();
    }
}
