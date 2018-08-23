package com.cloudbank.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * User role
 *
 * @author Vitalii Abramov
 */
@Embeddable
public class UserRole {

    @Column(name = "role")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
