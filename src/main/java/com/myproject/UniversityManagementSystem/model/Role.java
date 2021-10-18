package com.myproject.UniversityManagementSystem.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
