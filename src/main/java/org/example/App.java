package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class App {

    public static void main(String[] args) {

    }

    static void createEntities(){
        EntityManager em = JpaUtil.getEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Author author1 = new Author("Alen Mujkanovic");
            Author author2 = new Author("Ana Anic");

            Book book1 = new Book("Hello world for dummies",author1);
            Book book2 = new Book("Hibernate/JPA for beginners",author2);

            Publisher publisher1 = new Publisher("Skolska Knjiga");
            Publisher publisher2 = new Publisher("Algoritam");

            author1.getBooks().add(book1);
            author2.getBooks().add(book2);

            book1.getPublishers().add(publisher1);
            book2.getPublishers().add(publisher2);

            publisher1.getBooks().add(book1);
            publisher2.getBooks().add(book2);

            em.persist(author1);
            em.persist(author2);
            em.persist(publisher1);
            em.persist(publisher2);
            em.persist(book1);
            em.persist(book2);

            transaction.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if (transaction.isActive())transaction.rollback();
        }

        em.close();
    }

    static void getAllAuthors(){
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a JOIN FETCH a.books", Author.class);
        List<Author> authors = query.getResultList();

        for (Author author : authors) {
            System.out.println("Author: " + author);
            for (Book book : author.getBooks()) {
                System.out.println("  Book: " + book);
            }
        }
    }

    static void updateBookTitle(long bookId, String newTitle){
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            Book book = em.find(Book.class , bookId);
            if (book != null) {
                book.setTitle(newTitle);
                em.merge(book);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction.isActive()) transaction.rollback();
        }
        em.close();
    }

    private static void deleteBook(long bookId) {
            EntityManager em = JpaUtil.getEntityManager();
            EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Book book = em.find(Book.class, bookId);
            if (book != null) {
                em.remove(book);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction.isActive()) transaction.rollback();
        }
        em.close();
    }

}
