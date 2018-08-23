package com.cloudbank.dto;

import com.cloudbank.model._enum.Bank;

import java.util.Objects;

/**
 * User sign in dto
 *
 * @author Vitalii Abramov
 */
public class SignInUserDto {
    private String login;
    private String password;
    private Bank bankSystem;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bank getBankSystem() {
        return bankSystem;
    }

    public void setBankSystem(Bank bankSystem) {
        this.bankSystem = bankSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInUserDto that = (SignInUserDto) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "SignInUserDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", bankSystem=" + bankSystem +
                '}';
    }
}
