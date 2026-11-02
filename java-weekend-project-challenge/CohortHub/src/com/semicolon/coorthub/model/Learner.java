package com.semicolon.coorthub.model;

import java.util.Objects;

public class Learner {
    private final String id; // Immutable identity
    private String name;
    private String email;

    public Learner(String id, String name, String email) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID cannot be blank.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be blank.");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email cannot be blank.");

        this.id = id.trim();
        this.name = name.trim();
        this.email = email.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    // Consistent equals and hashCode using the immutable ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Learner learner = (Learner) o;
        return id.equals(learner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Learner[ID=%s, Name=%s, Email=%s]", id, name, email);
    }
}
