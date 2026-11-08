package com.wedding.dao;

import com.wedding.model.Booking;
import com.wedding.model.VendorBooking;
import com.wedding.model.VenueBooking;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingDao {

    private static final String FILE_NAME = "bookings.txt";

    // READ ALL
    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Booking b = parse(line);
            if (b != null) list.add(b);
        }
        return list;
    }

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

    private void saveAll(List<Booking> bookings) {
        List<String> lines = new ArrayList<>();
        for (Booking b : bookings) lines.add(b.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    // SCHEDULING: is this vendor/venue free on this date?
    // ignoreId lets a booking be rescheduled without clashing with itself
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

    // CREATE
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

    // READ
    public Booking findById(String id) {
        for (Booking b : findAll()) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }

    public List<Booking> findByUser(String userId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : findAll()) {
            if (b.getUserId().equals(userId)) result.add(b);
        }
        return result;
    }

    // EVENT SCHEDULE: all active bookings sorted by date
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

    // UPDATE (reschedule date, time or guests)
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

    // UPDATE (cancel keeps the record but frees the date)
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

    // Next free
    public String nextId() {
        int max = 0;
        for (Booking b : findAll()) {
            if (b.getId() != null && b.getId().startsWith("B")) {
                try {
                    int num = Integer.parseInt(b.getId().substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("B%03d", max + 1);
    }

    // DELETE
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
