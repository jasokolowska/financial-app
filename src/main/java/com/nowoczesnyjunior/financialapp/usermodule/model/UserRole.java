package com.nowoczesnyjunior.financialapp.usermodule.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
