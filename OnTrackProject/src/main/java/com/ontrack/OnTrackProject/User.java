package com.ontrack.OnTrackProject;

import java.util.*;

public abstract class User {
    private String userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Date registrationDate;

    public User(String userId, String username, String email, String firstName, String lastName) {
        if (userId == null || userId.trim().isEmpty()) throw new IllegalArgumentException("User ID cannot be null or empty");
        if (username == null || username.trim().isEmpty()) throw new IllegalArgumentException("Username cannot be null or empty");
        if (!isValidEmail(email)) throw new IllegalArgumentException("Invalid email format");

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.registrationDate = new Date();
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public String getFullName() { return firstName + " " + lastName; }
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Date getRegistrationDate() { return registrationDate; }

    public void setEmail(String email) {
        if (!isValidEmail(email)) throw new IllegalArgumentException("Invalid email format");
        this.email = email;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
