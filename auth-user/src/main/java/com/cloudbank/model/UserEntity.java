package com.cloudbank.model;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

/**
 * User entity.
 *
 * @author Vitalii Abramov
 * @since 1.1
 */
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private UserName name;

    @NotNull
    @Size(min = 6, max = 15)
    @Column(name = "login", unique = true)
    private String login;

    @NotNull
    @Size(min = 6, max = 15)
    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @ElementCollection
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserPhone> phones;

    @ElementCollection
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserRole> roles;

    public UserEntity() {
    }

    public UserEntity(UserName name,
                      String login,
                      String password,
                      String email,
                      Set<UserPhone> phones,
                      Set<UserRole> roles) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phones = phones;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserName getName() {
        return name;
    }

    public void setName(UserName name) {
        this.name = name;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<UserPhone> getPhones() {
        return phones;
    }

    public void setPhones(Set<UserPhone> phones) {
        this.phones = phones;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name=" + name +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                ", roles=" + roles +
                '}';
    }
}
