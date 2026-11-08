package com.wedding.dao;

import com.wedding.model.PublicReview;
import com.wedding.model.Review;
import com.wedding.model.VerifiedReview;
import com.wedding.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class ReviewDao {

    private static final String FILE_NAME = "reviews.txt";

    // READ ALL
    public List<Review> findAll() {
        List<Review> list = new ArrayList<>();
        for (String line : FileUtil.readLines(FILE_NAME)) {
            Review r = parse(line);
            if (r != null) list.add(r);
        }
        return list;
    }

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

    private void saveAll(List<Review> reviews) {
        List<String> lines = new ArrayList<>();
        for (Review r : reviews) lines.add(r.toFileString());
        FileUtil.writeLines(FILE_NAME, lines);
    }

    // CREATE
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

    // READ
    public Review findById(String id) {
        for (Review r : findAll()) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }

    // Reviews other users can see for one vendor/venue (hidden ones are not shown)
    public List<Review> findByTarget(String targetId) {
        List<Review> result = new ArrayList<>();
        for (Review r : findAll()) {
            if (r.getTargetId().equals(targetId) && r.getStatus().equals("PUBLISHED")) {
                result.add(r);
            }
        }
        return result;
    }

    public List<Review> findByUser(String userId) {
        List<Review> result = new ArrayList<>();
        for (Review r : findAll()) {
            if (r.getUserId().equals(userId)) result.add(r);
        }
        return result;
    }

    // Average rating (polymorphism: getWeight() is 1 for public, 2 for verified)
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

    // UPDATE (user edits own review)
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

    // UPDATE (admin moderation: HIDDEN or PUBLISHED)
    public boolean moderateReview(String id, String newStatus) {
        if (!newStatus.equals("PUBLISHED") && !newStatus.equals("HIDDEN")) return false;
        Review r = findById(id);
        if (r == null) return false;
        r.setStatus(newStatus);
        return update(r);
    }

    // DELETE (user or admin)
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
