package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class App {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

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

    public static void updateBookTitle(Long id, String newTitle){
        Session session  = sessionFactory.openSession();
        Query updateBookQuery = session.createQuery("UPDATE Book b set b.title=:title WHERE b.id=:id");

    }




}

