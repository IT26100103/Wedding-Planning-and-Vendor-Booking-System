package com.wedding.dao;

/**
 * ============================================================================
 * DATA ACCESS OBJECT (DAO): UserDao
 * ============================================================================
 * ARCHITECTURAL PATTERN: DAO (Data Access Object)
 * - Isolates data persistence logic from business and presentation layers.
 * - Manages read, write, update, delete (CRUD), and authentication operations
 *   for User accounts stored in '%USERPROFILE%/wedding-data/users.txt'.
 * - Handles polymorphic instantiation of AdminUser vs RegularUser from records.
 * - Thread-safe access via centralized FileUtil.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */


import com.wedding.model.AdminUser;
import com.wedding.model.RegularUser;
import com.wedding.model.User;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final String FILE_NAME = "users.txt";

    // READ ALL
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            User u = parse(line);
            if (u != null) list.add(u);
        }
        return list;
    }

    private User parse(String line) {
        String[] p = line.split("[|]");
        if (p.length < 7) return null;                 // damaged line
        if (p[5].equals("ADMIN")) {
            return new AdminUser(p[0], p[1], p[2], p[3], p[4], p[6]);
        }
        return new RegularUser(p[0], p[1], p[2], p[3], p[4], p[6]);
    }

    private void saveAll(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) lines.add(u.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    // CREATE
    public boolean add(User newUser) {
        List<User> users = findAll();
        for (User u : users) {
            if (u.getUsername().equals(newUser.getUsername())) return false;   // username taken
        }
        users.add(newUser);
        saveAll(users);
        return true;
    }

    // READ (find by id)
    public User findById(String id) {
        for (User u : findAll()) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    // READ (search by username)
    public User findByUsername(String username) {
        for (User u : findAll()) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    // UPDATE (replace the user with the same id)
    public boolean update(User updated) {
        List<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(updated.getId())) {
                users.set(i, updated);
                saveAll(users);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean delete(String id) {
        List<User> users = findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                users.remove(i);
                saveAll(users);
                return true;
            }
        }
        return false;
    }

    // Next ID generator: U001, U002 ... or A001, A002 ...
    public String nextId(String role) {
        String prefix = "ADMIN".equalsIgnoreCase(role) ? "A" : "U";
        int max = 0;
        for (User u : findAll()) {
            if (u.getId() != null && u.getId().startsWith(prefix)) {
                try {
                    int num = Integer.parseInt(u.getId().substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("%s%03d", prefix, max + 1);
    }

    // LOGIN
    public User login(String username, String password) {
        User u = findByUsername(username);
        if (u != null && u.getPassword().equals(password)) return u;
        return null;
    }
}
