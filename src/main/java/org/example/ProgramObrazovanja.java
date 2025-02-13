package org.example;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class ProgramObrazovanja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int programObrazovanjaID;

    private String naziv;

    private int CSVET;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "upisi",
            joinColumns = { @JoinColumn(name = "programObrazovanjaID") },
            inverseJoinColumns = { @JoinColumn(name = "polaznikID") }
    )
    private Set<Polaznik> polaznici = new HashSet<>();

    public int getProgramObrazovanjaID() {
        return programObrazovanjaID;
    }

    public void setProgramObrazovanjaID(int programObrazovanjaID) {
        this.programObrazovanjaID = programObrazovanjaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Set<Polaznik> getPolaznici() {
        return polaznici;
    }

    public void setPolaznici(Set<Polaznik> polaznici) {
        this.polaznici = polaznici;
    }

    public int getCSVET() {
        return CSVET;
    }

    public void setCSVET(int CSVET) {
        this.CSVET = CSVET;
    }
}
