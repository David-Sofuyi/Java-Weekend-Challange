
package com.semicolon.coorthub.model;

public class Learner {
    private final String id;
    private String name;
    private String email;

    public Learner(String id, String name, String email) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be blank");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be blank");
        }

        this.id = id.trim();
        this.name = name.trim();
        this.email = email.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Learner ID: " + id +
                ", Name: " + name +
                ", Email: " + email;
    }
}
