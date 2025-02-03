package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

    }
}
