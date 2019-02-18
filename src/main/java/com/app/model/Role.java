package com.app.model;

public enum Role {
    ADMIN ("ROLE_ADMIN"),
    USER ("ROLE_USER");

    private String fullName;

    Role(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
