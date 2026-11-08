package com.wedding.dao;

/**
 * ============================================================================
 * DATA ACCESS OBJECT (DAO): BookingDao
 * ============================================================================
 * ARCHITECTURAL PATTERN: DAO (Data Access Object)
 * - Manages persistent operations for wedding bookings (Vendors & Venues).
 * - Flat-file storage at '%USERPROFILE%/wedding-data/bookings.txt'.
 * - Dynamically constructs VendorBooking vs VenueBooking instances.
 * - Enforces business rules:
 *     1. Date clash prevention (isAvailable): avoids double-booking resources on the same date.
 *     2. Reschedule validation: ignores own booking ID during collision check.
 *     3. Soft cancellation: status updated to CANCELLED to preserve history while freeing date.
 *     4. Chronological schedule sorting: sorts active reservations by event date.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */

import com.wedding.model.Booking;
import com.wedding.model.VendorBooking;
import com.wedding.model.VenueBooking;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingDao {

    /** Flat file storage file name */
    private static final String FILE_NAME = "bookings.txt";

    /**
     * Reads all booking records from the file store.
     *
     * @return List of all Booking instances
     */
    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Booking b = parse(line);
            if (b != null) list.add(b);
        }
        return list;
    }

    /**
     * Parses a pipe-delimited line from bookings.txt into either a VenueBooking
     * or VendorBooking instance based on the stored booking type.
     *
     * @param line Raw pipe-delimited text line
     * @return Concrete Booking instance or null if line format is invalid
     */
    private Booking parse(String line) {
        String[] p = line.split("[|]");
        if (p.length < 9) return null;                 // damaged line
        try {
            int guests = Integer.parseInt(p[5]);
            if (p[7].equals("VENUE")) {
                return new VenueBooking(p[0], p[1], p[2], p[3], p[4], guests, p[6],
                        Integer.parseInt(p[8]));
            }
            return new VendorBooking(p[0], p[1], p[2], p[3], p[4], guests, p[6]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Serializes all booking objects back to the file store.
     *
     * @param bookings List of Booking objects
     */
    private void saveAll(List<Booking> bookings) {
        List<String> lines = new ArrayList<>();
        for (Booking b : bookings) lines.add(b.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    /**
     * Checks if a resource (vendor or venue) is free on a given event date.
     *
     * @param resourceId Target vendor or venue ID
     * @param date       Event date string (yyyy-MM-dd)
     * @param ignoreId   Booking ID to ignore (allows rescheduling without self-collision)
     * @return true if resource is available on that date, false if already booked
     */
    public boolean isAvailable(String resourceId, String date, String ignoreId) {
        for (Booking b : findAll()) {
            if (b.getId().equals(ignoreId)) continue;
            if (b.getStatus().equals("CANCELLED")) continue;
            if (b.getResourceId().equals(resourceId) && b.getEventDate().equals(date)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates and saves a new booking after verifying resource availability.
     *
     * @param b Booking object to persist
     * @return true if successfully saved, false if ID already exists or resource is busy
     */
    public boolean add(Booking b) {
        List<Booking> bookings = findAll();
        for (Booking x : bookings) {
            if (x.getId().equals(b.getId())) return false;           // id already used
        }
        if (!isAvailable(b.getResourceId(), b.getEventDate(), "")) {
            return false;                                             // date clash
        }
        bookings.add(b);
        saveAll(bookings);
        return true;
    }

    /**
     * Finds a single booking by its unique ID.
     *
     * @param id Booking identifier (e.g., B001)
     * @return Matching Booking instance, or null if not found
     */
    public Booking findById(String id) {
        for (Booking b : findAll()) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }

    /**
     * Retrieves all bookings created by a specific user.
     *
     * @param userId Client user ID
     * @return List of bookings belonging to this user
     */
    public List<Booking> findByUser(String userId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : findAll()) {
            if (b.getUserId().equals(userId)) result.add(b);
        }
        return result;
    }

    /**
     * Returns an active chronological event timeline, excluding cancelled reservations,
     * sorted ascending by event date.
     *
     * @return Chronologically sorted list of active bookings
     */
    public List<Booking> getSchedule() {
        List<Booking> result = new ArrayList<>();
        for (Booking b : findAll()) {
            if (!b.getStatus().equals("CANCELLED")) result.add(b);
        }
        Collections.sort(result, new Comparator<Booking>() {
            @Override
            public int compare(Booking a, Booking b) {
                return a.getEventDate().compareTo(b.getEventDate());
            }
        });
        return result;
    }

    /**
     * Updates an existing booking (e.g., rescheduling date/time or changing guest count).
     * Re-verifies date availability to prevent collisions.
     *
     * @param updated Modified Booking instance
     * @return true if updated, false if date clash occurs or booking not found
     */
    public boolean update(Booking updated) {
        if (!isAvailable(updated.getResourceId(), updated.getEventDate(), updated.getId())) {
            return false;                                             // new date clashes
        }
        List<Booking> bookings = findAll();
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(updated.getId())) {
                bookings.set(i, updated);
                saveAll(bookings);
                return true;
            }
        }
        return false;
    }

    /**
     * Cancels a booking while keeping its record for audit history and freeing the date.
     *
     * @param id Booking ID to cancel
     * @return true if successfully cancelled, false if not found
     */
    public boolean cancelBooking(String id) {
        List<Booking> bookings = findAll();
        for (Booking b : bookings) {
            if (b.getId().equals(id)) {
                b.setStatus("CANCELLED");
                saveAll(bookings);
                return true;
            }
        }
        return false;
    }

    /**
     * Permanently deletes a booking record from the system.
     *
     * @param id Booking ID to remove
     * @return true if deleted, false if not found
     */
    public boolean delete(String id) {
        List<Booking> bookings = findAll();
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(id)) {
                bookings.remove(i);
                saveAll(bookings);
                return true;
            }
        }
        return false;
    }
}
