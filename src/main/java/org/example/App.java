package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;


public class App {

    public static void main(String[] args) {

    }

    static void getAllAuthors(){
        EntityManager em = JpaUtil.getEntityManager();
        try{

            Query query = em.createQuery("SELECT a FROM Author a JOIN FETCH a.books");
            List<Author> authors = query.getResultList();

            for (Author author : authors) {
                System.out.println("Author: " + author.getName());
                for (Book book : author.getBooks()){
                    System.out.println(book);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            em.close();
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
