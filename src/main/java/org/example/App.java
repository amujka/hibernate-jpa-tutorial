package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class App {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Polaznik\n2. add program obrazovanja\n3. upisi polaznika\n4. prebaci polaznika\n5. print polaznici\n6.Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    createNewPolaznik(scanner);
                    break;
                case 2:
                    createNewProgramObrazovanja(scanner);
                    break;
                case 3:
                    enrollPolaznik(scanner);
                    break;
                case 4:
                    transferPolaznik(scanner);
                    break;
                case 5:
                    getAllPolaznikByProgramObrazovanja(scanner);
                    break;
                default:
                    System.out.println("Exit....");
                    return;
            }
        }

    }
    static void createNewPolaznik(Scanner scanner){
        Transaction tx = null;
        Session session;
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        try {
             session = sessionFactory.openSession();
             tx = session.beginTransaction();
            Polaznik polaznik = new Polaznik();
            polaznik.setIme(firstName);
            polaznik.setPrezime(lastName);
            session.persist(polaznik);
            System.out.println("Polaznik added");
            tx.commit();
            session.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if (tx != null) tx.rollback();
        }
    }

    static void createNewProgramObrazovanja(Scanner scanner) {
        Transaction tx = null;
        Session session;
        System.out.println("Enter program name:");
        String name = scanner.nextLine();

        System.out.println("Enter CSVET value:");
        int csvet = scanner.nextInt();

        try  {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            ProgramObrazovanja programObrazovanja = new ProgramObrazovanja();
            programObrazovanja.setNaziv(name);
            programObrazovanja.setCSVET(csvet);
            session.persist(programObrazovanja);
            System.out.println("Program obrazovanja added");
            tx.commit();
            session.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if (tx != null) tx.rollback();
        }
    }

    static void enrollPolaznik(Scanner scanner) {
        Transaction tx = null;
        Session session;
        System.out.println("Enter Program Id:");
        long programId = scanner.nextLong();

        System.out.println("Enter Polaznik Id:");
        long polaznikId = scanner.nextLong();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.beginTransaction();

            Polaznik polaznik = session.get(Polaznik.class, polaznikId);
            ProgramObrazovanja program = session.get(ProgramObrazovanja.class, programId);

            if (polaznik != null && program != null) {
                polaznik.getProgramiObrazovanja().add(program);
                session.persist(polaznik);
            }

            System.out.println("Polaznik upisan");
            tx.commit();
            session.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if (tx != null) tx.rollback();
        }

    }

    static void transferPolaznik(Scanner scanner) {
        Transaction tx = null;
        Session session;
        System.out.print("enter ID polaznika: ");
        int polaznikID = scanner.nextInt();

        System.out.print("enter current ID programa: ");
        int stariProgramID = scanner.nextInt();

        System.out.print("enter new ID programa: ");
        int noviProgramID = scanner.nextInt();

        try {
             session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Polaznik polaznik = session.get(Polaznik.class, polaznikID);
            ProgramObrazovanja currentProgram = session.get(ProgramObrazovanja.class, stariProgramID);
            ProgramObrazovanja newProgram = session.get(ProgramObrazovanja.class, noviProgramID);

            polaznik.getProgramiObrazovanja().remove(currentProgram);
            polaznik.getProgramiObrazovanja().add(newProgram);
            session.update(polaznik);
            tx.commit();
            session.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if (tx != null) tx.rollback();
        }

    }

    static void getAllPolaznikByProgramObrazovanja(Scanner scanner){
        Transaction tx = null;
        Session session;
        System.out.println("Enter Program ID:");
        int id = scanner.nextInt();

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            ProgramObrazovanja program = session.get(ProgramObrazovanja.class, id);
            System.out.println("Participants in Program: " + program.getNaziv());
            for (Polaznik polaznik : program.getPolaznici()) {
                System.out.printf("%s %s, Program: %s, CSVET Points: %d%n",polaznik.getIme(),polaznik.getPrezime(),program.getNaziv(),program.getCSVET()
                );
            }

            tx.commit();
            session.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            if (tx != null) tx.rollback();
        }
    }
}
