package com.wedding.model;

import com.wedding.util.FileUtil;

public class RegularUser extends User {
    private String weddingDate;

    public RegularUser(String id, String name, String email, String username, String password, String weddingDate) {
        super(id, name, email, username, password);
        this.weddingDate = weddingDate;
    }

    public String getWeddingDate() { 
        return weddingDate; 
    }
    public void setWeddingDate(String weddingDate) { 
        this.weddingDate = weddingDate; 
    }

    @Override
    public String getRole() { 
        return "USER"; 
    }

    @Override
    public String toFileString() {
        return getId() + "|" + FileUtil.clean(getName()) + "|" + FileUtil.clean(getEmail()) + "|"
                + FileUtil.clean(getUsername()) + "|" + FileUtil.clean(getPassword()) + "|USER|"
                + FileUtil.clean(weddingDate);
    }

    @Override
    public void display() {
        System.out.println("[User] " + getId() + " | " + getName() + " (" + getEmail() + ") | Wedding: " + weddingDate);
    }
}
