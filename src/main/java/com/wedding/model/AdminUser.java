package com.wedding.model;

import com.wedding.util.FileUtil;

<<<<<<< HEAD
public class AdminUser extends User {
    private String permissionLevel;   // SUPER or NORMAL
=======
/**
 * ============================================================================
 * CONCRETE CLASS: AdminUser
 * ============================================================================
 * OOP CONCEPT: INHERITANCE & SPECIALIZATION
 * - Inherits from User base class.
 * - Adds administrative permission level (e.g. "FULL", "SUPER", "NORMAL").
 * - Implements polymorphic getRole() ("ADMIN") and toFileString().
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class AdminUser extends User {
    private String permissionLevel;
>>>>>>> origin/main

    public AdminUser(String id, String name, String email, String username,
                     String password, String permissionLevel) {
        super(id, name, email, username, password);
        this.permissionLevel = permissionLevel;
    }

    public String getPermissionLevel() { return permissionLevel; }
    public void setPermissionLevel(String permissionLevel) { this.permissionLevel = permissionLevel; }

    @Override
    public String getRole() { return "ADMIN"; }

    @Override
    public String toFileString() {
        return getId() + "|" + FileUtil.clean(getName()) + "|" + FileUtil.clean(getEmail()) + "|"
<<<<<<< HEAD
                + FileUtil.clean(getUsername()) + "|" + FileUtil.clean(getPassword())
                + "|ADMIN|" + FileUtil.clean(permissionLevel);
=======
                + FileUtil.clean(getUsername()) + "|" + FileUtil.clean(getPassword()) + "|ADMIN|"
                + FileUtil.clean(permissionLevel);
>>>>>>> origin/main
    }

    @Override
    public void display() {
<<<<<<< HEAD
        System.out.println("[ADMIN] " + getId() + " | " + getName() + " | " + getEmail()
                + " | Permission: " + permissionLevel);
=======
        System.out.println("[Admin] " + getId() + " | " + getName() + " | Level: " + permissionLevel);
>>>>>>> origin/main
    }
}
