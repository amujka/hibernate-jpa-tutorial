package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class App
{
    public static void main( String[] args )
    {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaExampleUnit");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Contract contract1 = new Contract();
        contract1.setStartDate(LocalDate.of(2025, 1, 1));
        contract1.setDuration(12);
        contract1.setSalary(new BigDecimal("2500"));

        Contract contract2 = new Contract();
        contract2.setStartDate(LocalDate.of(2026, 6, 1));
        contract2.setDuration(24);
        contract2.setSalary(new BigDecimal("10339"));

        Company company1 = new Company();
        company1.setName("Company Microsoft");

        Company company2 = new Company();
        company2.setName("Company Apple");

        // Povezivanje ugovora s tvrtkom (Company)
        contract1.setCompany(company1);
        contract2.setCompany(company2);

        // Kreiranje osoba (Person)
        Person person1 = new Person();
        person1.setName("Ivo Ivic");

        Person person2 = new Person();
        person2.setName("Ana Anic");

        Set<Contract> contractsForPerson1 = new HashSet<>();
        contractsForPerson1.add(contract1);
        contractsForPerson1.add(contract2);
        person1.setContracts(contractsForPerson1);

        Set<Contract> contractsForPerson2 = new HashSet<>();
        contractsForPerson2.add(contract1);
        person2.setContracts(contractsForPerson2);

        em.persist(company1);
        em.persist(company2);
        em.persist(contract1);
        em.persist(contract2);
        em.persist(person1);
        em.persist(person2);


        person1.setName("Ivo Updated");
        em.merge(person1);

        em.remove(contract1);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}
