package org.example;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Polaznik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int polaznikID;

    private String ime;
    private String prezime;

    @ManyToMany(mappedBy = "polaznici", cascade = CascadeType.ALL)
    private Set<ProgramObrazovanja> programiObrazovanja = new HashSet<>();


    public int getPolaznikID() {
        return polaznikID;
    }

    public void setPolaznikID(int polaznikID) {
        this.polaznikID = polaznikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Set<ProgramObrazovanja> getProgramiObrazovanja() {
        return programiObrazovanja;
    }

    public void setProgramiObrazovanja(Set<ProgramObrazovanja> programiObrazovanja) {
        this.programiObrazovanja = programiObrazovanja;
    }
}
