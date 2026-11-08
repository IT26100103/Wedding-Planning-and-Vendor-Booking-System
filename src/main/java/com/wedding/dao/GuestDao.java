package com.wedding.dao;

/**
 * ============================================================================
 * DATA ACCESS OBJECT (DAO): GuestDao
 * ============================================================================
 * ARCHITECTURAL PATTERN: DAO (Data Access Object)
 * - Manages guest list and RSVP records in '%USERPROFILE%/wedding-data/guests.txt'.
 * - Handles polymorphic reconstitution of RegularGuest vs VipGuest instances.
 * - Supports guest searching, RSVP status updates, seating assignment, and auto ID generation (G001).
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */


import com.wedding.model.Guest;
import com.wedding.model.RegularGuest;
import com.wedding.model.VipGuest;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class GuestDao {

    private static final String FILE_NAME = "guests.txt";

    // ---------- READ ----------
    public List<Guest> findAll() {
        List<Guest> guests = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Guest guest = parseLine(line);
            if (guest != null) {
                guests.add(guest);
            }
        }
        return guests;
    }

    public Guest findById(String id) {
        if (id == null) return null;
        for (Guest guest : findAll()) {
            if (guest.getGuestId().equalsIgnoreCase(id.trim())) {
                return guest;
            }
        }
        return null;
    }

    public List<Guest> searchByName(String keyword) {
        List<Guest> result = new ArrayList<>();
        if (keyword == null) return findAll();
        String q = keyword.trim().toLowerCase();
        for (Guest guest : findAll()) {
            if (guest.getFullName().toLowerCase().contains(q)) {
                result.add(guest);
            }
        }
        return result;
    }

    // ---------- CREATE ----------
    public void add(Guest guest) {
        List<Guest> guests = findAll();
        guests.add(guest);
        saveAll(guests);
    }

    // ---------- UPDATE ----------
    public boolean update(Guest updated) {
        List<Guest> guests = findAll();
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getGuestId().equalsIgnoreCase(updated.getGuestId())) {
                guests.set(i, updated);
                saveAll(guests);
                return true;
            }
        }
        return false;
    }

    // ---------- DELETE ----------
    public boolean delete(String id) {
        if (id == null) return false;
        List<Guest> guests = findAll();
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getGuestId().equalsIgnoreCase(id.trim())) {
                guests.remove(i);
                saveAll(guests);
                return true;
            }
        }
        return false;
    }

    // Next free ID: G001, G002, G003 ...
    public String nextId() {
        int max = 0;
        for (Guest guest : findAll()) {
            if (guest.getGuestId() != null && guest.getGuestId().startsWith("G")) {
                try {
                    int number = Integer.parseInt(guest.getGuestId().substring(1));
                    if (number > max) {
                        max = number;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return String.format("G%03d", max + 1);
    }

    // Writes every guest back to the file
    private void saveAll(List<Guest> guests) {
        List<String> lines = new ArrayList<>();
        for (Guest guest : guests) {
            lines.add(guest.toFileLine());   // polymorphism: each object writes its own line
        }
        FileUtil.writeLines(FILE_NAME, lines);
    }

    // Turns one line of text back into a RegularGuest or a VipGuest
    private Guest parseLine(String line) {
        String[] p = line.split("[|]");
        if (p.length < 6) {
            return null;   // ignore damaged lines
        }
        if (p[0].equals("VIP") && p.length >= 7) {
            return new VipGuest(p[1], p[2], p[3], p[4], p[5], p[6]);
        }
        return new RegularGuest(p[1], p[2], p[3], p[4], p[5]);
    }
}
