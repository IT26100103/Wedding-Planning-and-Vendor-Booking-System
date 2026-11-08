package com.wedding.model;

/**
 * ============================================================================
 * ABSTRACT BASE CLASS: User
 * ============================================================================
 * OOP CONCEPT: ABSTRACTION & ENCAPSULATION
 * - Abstraction: Defines common identity and authentication credentials for all users
 *   while leaving role-specific behavior to derived classes.
 * - Encapsulation: All fields are private with public getters and setters.
 * - Polymorphism: getRole() and toFileString() are abstract, implemented by child classes.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public abstract class User {
    private String id;
    private String name;
    private String email;
    private String username;
    private String password;

    public User(String id, String name, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    /**
     * Polymorphic method: Returns the user's role ("USER" or "ADMIN").
     */
    public abstract String getRole();

    /**
     * Polymorphic method: Formats the user record as a pipe-delimited string.
     */
    public abstract String toFileString();

    /**
     * Console display for debugging.
     */
    public abstract void display();
}
