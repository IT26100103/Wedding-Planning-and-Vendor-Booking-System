package com.wedding.dao;

/**
 * ============================================================================
 * DATA ACCESS OBJECT (DAO): VenueDao
 * ============================================================================
 * ARCHITECTURAL PATTERN: DAO (Data Access Object)
 * - Manages persistent operations for wedding reception venues (Indoor & Outdoor).
 * - Flat-file storage at '%USERPROFILE%/wedding-data/venues.txt'.
 * - Polymorphically constructs IndoorVenue or OutdoorVenue instances from file records.
 * - Supports capacity filtering (canHold), text search (name/location/type),
 *   and full CRUD operations.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */

import com.wedding.model.IndoorVenue;
import com.wedding.model.OutdoorVenue;
import com.wedding.model.Venue;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class VenueDao {

    /** Persistent storage file name for venues */
    private static final String FILE_NAME = "venues.txt";

    /**
     * Reads and returns all venues currently saved in the file store.
     *
     * @return List of all Venue objects
     */
    public List<Venue> findAll() {
        List<Venue> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Venue v = parse(line);
            if (v != null) list.add(v);
        }
        return list;
    }

    /**
     * Parses a pipe-delimited record from venues.txt into an IndoorVenue or OutdoorVenue instance.
     *
     * @param line Raw pipe-delimited text record
     * @return Concrete Venue instance or null if parsing fails
     */
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

    /**
     * Writes the list of venues back to the persistent file.
     *
     * @param venues List of Venue instances
     */
    private void saveAll(List<Venue> venues) {
        List<String> lines = new ArrayList<>();
        for (Venue v : venues) lines.add(v.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    /**
     * Adds a new venue record if the ID is not already registered.
     *
     * @param v Venue object to add
     * @return true if successfully saved, false if ID conflict exists
     */
    public boolean add(Venue v) {
        List<Venue> venues = findAll();
        for (Venue x : venues) {
            if (x.getId().equals(v.getId())) return false;   // id already used
        }
        venues.add(v);
        saveAll(venues);
        return true;
    }

    /**
     * Searches venues matching a keyword against name, location, or type.
     *
     * @param keyword Search term
     * @return Filtered list of matching venues
     */
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

    /**
     * Filters venues that can comfortably accommodate the required guest count.
     *
     * @param guests Minimum required seating capacity
     * @return Venues with capacity >= guests
     */
    public List<Venue> findByCapacity(int guests) {
        List<Venue> result = new ArrayList<>();
        for (Venue v : findAll()) {
            if (v.canHold(guests)) result.add(v);
        }
        return result;
    }

    /**
     * Finds a single venue by its unique ID.
     *
     * @param id Venue ID (e.g., VN01)
     * @return Matching Venue instance, or null if not found
     */
    public Venue findById(String id) {
        for (Venue v : findAll()) {
            if (v.getId().equals(id)) return v;
        }
        return null;
    }

    /**
     * Updates an existing venue record.
     *
     * @param updated Modified Venue instance
     * @return true if updated, false if not found
     */
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

    /**
     * Deletes a venue from persistent storage.
     *
     * @param id Venue identifier
     * @return true if deleted, false if not found
     */
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
