package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App
{
    public static void main( String[] args )
    {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaExampleUnit");
        EntityManager en = emf.createEntityManager();

        Person person = new Person();
//        person.setFirstName("Alen");
//        person.setLastName("Mujkanovic");
        en.getTransaction().begin();

        Person person1 = en.find(Person.class,1);
        if (person1 != null){
            System.out.println(person1.getFirstName());
        }

        en.getTransaction().commit();
    }
}
