package com.cloudbank.model;

import com.cloudbank.model._enum.Bank;

import javax.persistence.*;
import java.util.Objects;

/**
 * User bank
 *
 * @author Vitalii Abramov
 */
@Entity
@Table(name = "bank")
public class UserBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank")
    private Bank bank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBank userBank = (UserBank) o;
        return Objects.equals(id, userBank.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserBank{" +
                "id=" + id +
                ", bank=" + bank +
                '}';
    }
}
