package com.wedding.model;

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

    public String getId() { 
        return id; 
    }
    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public String getUsername() { 
        return username; 
    }
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    //Returns the user's role
    public abstract String getRole();

    //Formats the user record 
    public abstract String toFileString();

    //display
    public abstract void display();
}
