package com.wedding.model;

import com.wedding.util.FileUtil;

public class AdminUser extends User {
    private String permissionLevel;   // SUPER or NORMAL

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
                + FileUtil.clean(getUsername()) + "|" + FileUtil.clean(getPassword())
                + "|ADMIN|" + FileUtil.clean(permissionLevel);
    }

    @Override
    public void display() {
        System.out.println("[ADMIN] " + getId() + " | " + getName() + " | " + getEmail()
                + " | Permission: " + permissionLevel);
    }
}
