package org.example;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "ContractPerson",
            joinColumns = @JoinColumn(name = "IDPerson"),
            inverseJoinColumns = @JoinColumn(name = "IDContract"))
    private Set<Contract> contracts;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }
}
