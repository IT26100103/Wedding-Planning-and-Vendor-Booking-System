package com.wedding.model;

import com.wedding.util.FileUtil;

<<<<<<< HEAD
=======
/**
 * ============================================================================
 * CONCRETE CLASS: RegularUser
 * ============================================================================
 * OOP CONCEPT: INHERITANCE & POLYMORPHISM
 * - Inherits from User base class.
 * - Adds specialization: weddingDate (planned wedding ceremony date).
 * - Implements polymorphic getRole() ("USER") and toFileString().
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
>>>>>>> origin/main
public class RegularUser extends User {
    private String weddingDate;

    public RegularUser(String id, String name, String email, String username,
                       String password, String weddingDate) {
        super(id, name, email, username, password);
        this.weddingDate = weddingDate;
    }

    public String getWeddingDate() { return weddingDate; }
    public void setWeddingDate(String weddingDate) { this.weddingDate = weddingDate; }

    @Override
    public String getRole() { return "USER"; }

    @Override
    public String toFileString() {
        return getId() + "|" + FileUtil.clean(getName()) + "|" + FileUtil.clean(getEmail()) + "|"
<<<<<<< HEAD
                + FileUtil.clean(getUsername()) + "|" + FileUtil.clean(getPassword())
                + "|USER|" + FileUtil.clean(weddingDate);
=======
                + FileUtil.clean(getUsername()) + "|" + FileUtil.clean(getPassword()) + "|USER|"
                + FileUtil.clean(weddingDate);
>>>>>>> origin/main
    }

    @Override
    public void display() {
<<<<<<< HEAD
        System.out.println("[USER] " + getId() + " | " + getName() + " | " + getEmail()
                + " | Wedding date: " + weddingDate);
=======
        System.out.println("[User] " + getId() + " | " + getName() + " (" + getEmail() + ") | Wedding: " + weddingDate);
>>>>>>> origin/main
    }
}
