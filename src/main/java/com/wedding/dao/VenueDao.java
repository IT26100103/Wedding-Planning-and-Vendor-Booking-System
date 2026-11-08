package com.wedding.dao;

import com.wedding.model.IndoorVenue;
import com.wedding.model.OutdoorVenue;
import com.wedding.model.Venue;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class VenueDao {

    private static final String FILE_NAME = "venues.txt";

    // READ ALL
    public List<Venue> findAll() {
        List<Venue> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Venue v = parse(line);
            if (v != null) list.add(v);
        }
        return list;
    }

    private Venue parse(String line) {
        String[] p = line.split("[|]");
        if (p.length < 7) return null;                 // damaged line
        try {
            int capacity = Integer.parseInt(p[3]);
            double price = Double.parseDouble(p[4]);
            boolean extra = Boolean.parseBoolean(p[6]);
            if (p[5].equals("INDOOR")) return new IndoorVenue(p[0], p[1], p[2], capacity, price, extra);
            if (p[5].equals("OUTDOOR")) return new OutdoorVenue(p[0], p[1], p[2], capacity, price, extra);
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void saveAll(List<Venue> venues) {
        List<String> lines = new ArrayList<>();
        for (Venue v : venues) lines.add(v.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    // CREATE
    public boolean add(Venue v) {
        List<Venue> venues = findAll();
        for (Venue x : venues) {
            if (x.getId().equals(v.getId())) return false;   // id already used
        }
        venues.add(v);
        saveAll(venues);
        return true;
    }

    // READ (search by name, location or type)
    public List<Venue> search(String keyword) {
        List<Venue> result = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Venue v : findAll()) {
            if (v.getName().toLowerCase().contains(k)
                    || v.getLocation().toLowerCase().contains(k)
                    || v.getType().toLowerCase().contains(k)) {
                result.add(v);
            }
        }
        return result;
    }

    // READ (venues big enough for the guest count)
    public List<Venue> findByCapacity(int guests) {
        List<Venue> result = new ArrayList<>();
        for (Venue v : findAll()) {
            if (v.canHold(guests)) result.add(v);
        }
        return result;
    }

    public Venue findById(String id) {
        for (Venue v : findAll()) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }

    // Next free ID
    public String nextId() {
        int max = 0;
        for (Venue v : findAll()) {
            if (v.getId() != null && v.getId().startsWith("VN")) {
                try {
                    int num = Integer.parseInt(v.getId().substring(2));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("VN%03d", max + 1);
    }

    // UPDATE
    public boolean update(Venue updated) {
        List<Venue> venues = findAll();
        for (int i = 0; i < venues.size(); i++) {
            if (venues.get(i).getId().equals(updated.getId())) {
                venues.set(i, updated);
                saveAll(venues);
                return true;
            }
        }
        return false;
    }

    // DELETE
    public boolean delete(String id) {
        List<Venue> venues = findAll();
        for (int i = 0; i < venues.size(); i++) {
            if (venues.get(i).getId().equals(id)) {
                venues.remove(i);
                saveAll(venues);
                return true;
            }
        }
        return false;
    }
}
