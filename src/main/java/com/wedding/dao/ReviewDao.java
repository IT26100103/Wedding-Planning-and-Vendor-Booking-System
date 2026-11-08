package com.wedding.dao;

/**
 * ============================================================================
 * DATA ACCESS OBJECT (DAO): ReviewDao
 * ============================================================================
 * ARCHITECTURAL PATTERN: DAO (Data Access Object)
 * - Encapsulates persistent operations for reviews and feedback on vendors and venues.
 * - Storage mechanism: flat file storage at '%USERPROFILE%/wedding-data/reviews.txt'.
 * - Demonstrates Polymorphic deserialization: parses lines into either
 *   PublicReview or VerifiedReview instances based on the stored review type.
 * - Provides weighted average rating calculation, moderation status updates,
 *   and duplicate review prevention (one review per user per target).
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */

import com.wedding.model.PublicReview;
import com.wedding.model.Review;
import com.wedding.model.VerifiedReview;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class ReviewDao {

    /** The flat file name used to persist review records */
    private static final String FILE_NAME = "reviews.txt";

    /**
     * Retrieves all review records from the persistent store.
     *
     * @return List of all Review objects
     */
    public List<Review> findAll() {
        List<Review> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Review r = parse(line);
            if (r != null) list.add(r);
        }
        return list;
    }

    /**
     * Parses a pipe-delimited line from reviews.txt into a concrete Review object
     * (either VerifiedReview or PublicReview).
     *
     * @param line Raw pipe-delimited text line
     * @return Concrete Review instance, or null if line format is invalid
     */
    private Review parse(String line) {
        String[] p = line.split("[|]");
        if (p.length < 8) return null;                 // damaged line
        try {
            int rating = Integer.parseInt(p[3]);
            if (p[6].equals("VERIFIED")) {
                return new VerifiedReview(p[0], p[1], p[2], rating, p[4], p[5], p[7]);
            }
            return new PublicReview(p[0], p[1], p[2], rating, p[4], p[5]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Serializes all reviews to the flat storage file.
     *
     * @param reviews List of review records
     */
    private void saveAll(List<Review> reviews) {
        List<String> lines = new ArrayList<>();
        for (Review r : reviews) lines.add(r.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    /**
     * Adds a new review after validating rating constraints (1-5), non-empty comment,
     * unique review ID, and ensuring one review per user per vendor.
     *
     * @param r The review to add
     * @return true if successfully saved, false if validation fails
     */
    public boolean add(Review r) {
        if (r.getRating() < 1 || r.getRating() > 5) return false;     // rating must be 1-5
        if (r.getComment().trim().isEmpty()) return false;            // comment is required
        for (Review x : findAll()) {
            if (x.getId().equals(r.getId())) return false;            // id already used
            if (x.getUserId().equals(r.getUserId()) && x.getTargetId().equals(r.getTargetId())) {
                return false;                                         // one review per user per target
            }
        }
        List<Review> all = findAll();
        all.add(r);
        saveAll(all);
        return true;
    }

    /**
     * Finds a specific review by its unique ID.
     *
     * @param id The review identifier (e.g. R001)
     * @return Matching Review object, or null if not found
     */
    public Review findById(String id) {
        for (Review r : findAll()) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }

    /**
     * Retrieves all published reviews for a given vendor or venue target.
     * Hidden reviews awaiting admin moderation are excluded.
     *
     * @param targetId Target entity ID (vendor or venue)
     * @return List of published reviews for the target
     */
    public List<Review> findByTarget(String targetId) {
        List<Review> result = new ArrayList<>();
        for (Review r : findAll()) {
            if (r.getTargetId().equals(targetId) && r.getStatus().equals("PUBLISHED")) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Retrieves all reviews authored by a specific user.
     *
     * @param userId The author's user ID
     * @return List of reviews submitted by this user
     */
    public List<Review> findByUser(String userId) {
        List<Review> result = new ArrayList<>();
        for (Review r : findAll()) {
            if (r.getUserId().equals(userId)) result.add(r);
        }
        return result;
    }

    /**
     * Computes the weighted average rating for a target vendor or venue.
     * Demonstrates polymorphism: VerifiedReview instances contribute 2x weight,
     * while PublicReview instances contribute 1x weight.
     *
     * @param targetId The target vendor or venue ID
     * @return Weighted average rating (0.0 if no reviews exist)
     */
    public double getAverageRating(String targetId) {
        double sum = 0;
        int totalWeight = 0;
        for (Review r : findByTarget(targetId)) {
            sum += r.getRating() * r.getWeight();
            totalWeight += r.getWeight();
        }
        if (totalWeight == 0) return 0;
        return sum / totalWeight;
    }

    /**
     * Computes the next available sequential ID formatted as R001, R002, etc.
     *
     * @return Next formatted review ID string
     */
    public String nextId() {
        int max = 0;
        for (Review r : findAll()) {
            if (r.getId() != null && r.getId().startsWith("R")) {
                try {
                    int num = Integer.parseInt(r.getId().substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException ignored) {}
            }
        }
        return String.format("R%03d", max + 1);
    }

    /**
     * Updates an existing review record.
     *
     * @param updated Modified Review object
     * @return true if updated, false if not found or validation fails
     */
    public boolean update(Review updated) {
        if (updated.getRating() < 1 || updated.getRating() > 5) return false;
        List<Review> reviews = findAll();
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getId().equals(updated.getId())) {
                reviews.set(i, updated);
                saveAll(reviews);
                return true;
            }
        }
        return false;
    }

    /**
     * Administrative moderation action to toggle a review's visibility status
     * between PUBLISHED and HIDDEN.
     *
     * @param id        Review ID
     * @param newStatus Either "PUBLISHED" or "HIDDEN"
     * @return true if successfully updated, false otherwise
     */
    public boolean moderateReview(String id, String newStatus) {
        if (!newStatus.equals("PUBLISHED") && !newStatus.equals("HIDDEN")) return false;
        Review r = findById(id);
        if (r == null) return false;
        r.setStatus(newStatus);
        return update(r);
    }

    /**
     * Deletes a review record by its ID.
     *
     * @param id Review ID to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(String id) {
        List<Review> reviews = findAll();
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getId().equals(id)) {
                reviews.remove(i);
                saveAll(reviews);
                return true;
            }
        }
        return false;
    }
}
