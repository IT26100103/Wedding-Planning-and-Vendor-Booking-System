package com.wedding.model;

import com.wedding.util.FileUtil;

/**
 * ============================================================================
 * ABSTRACT BASE CLASS: Review
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. ABSTRACTION:
 *    - Encapsulates common feedback metadata: review ID, author user ID,
 *      target entity ID (vendor/venue), star rating (1-5), feedback comment,
 *      and moderation status (PUBLISHED or HIDDEN).
 * 2. ENCAPSULATION:
 *    - Attributes are private; text comments are sanitized via FileUtil.clean()
 *      to ensure integrity during file-based persistence.
 * 3. POLYMORPHISM:
 *    - Defines abstract methods getReviewType(), getWeight(), and toFileString()
 *      which are overridden by PublicReview (standard weight) and VerifiedReview
 *      (weighted double for confirmed bookings).
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public abstract class Review {
    /** Unique identifier for review record (e.g., R001) */
    private String id;

    /** User identifier of the person submitting the review */
    private String userId;

    /** Identifier of the vendor or venue being reviewed */
    private String targetId;

    /** Numerical star rating (1 to 5) */
    private int rating;

    /** Text feedback commentary submitted by the reviewer */
    private String comment;

    /** Moderation status: PUBLISHED (visible to public) or HIDDEN (moderated/pending) */
    private String status;

    /**
     * Parameterized constructor initializing review attributes.
     *
     * @param id       Unique review identifier
     * @param userId   User ID of reviewer
     * @param targetId Target vendor/venue ID
     * @param rating   Star rating (1 to 5)
     * @param comment  Review text
     * @param status   Moderation status (PUBLISHED/HIDDEN)
     */
    public Review(String id, String userId, String targetId, int rating,
                  String comment, String status) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.rating = rating;
        this.comment = FileUtil.clean(comment);   // sanitize to avoid delimiter collision
        this.status = status;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getTargetId() { return targetId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = FileUtil.clean(comment); }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    /**
     * Polymorphic method: Returns the review classification (PUBLIC or VERIFIED).
     *
     * @return review type classification
     */
    public abstract String getReviewType();

    /**
     * Polymorphic method: Returns the statistical weighting factor of this review
     * when calculating weighted vendor score averages.
     *
     * @return review weight multiplier (1 for standard public, 2 for verified booking)
     */
    public abstract int getWeight();

    /**
     * Polymorphic method: Serializes the review object into pipe-delimited persistence format.
     *
     * @return formatted record line for reviews.txt
     */
    public abstract String toFileString();

    /**
     * Console diagnostic display for debugging and test reporting.
     */
    public void display() {
        System.out.println("[" + getReviewType() + "] " + id + " | User: " + userId
                + " | " + targetId + " | " + rating + "/5 | " + comment + " | " + status);
    }

    /**
     * Helper method to serialize shared attributes across review subclasses.
     *
     * @return pipe-delimited base field string
     */
    protected String baseFields() {
        return id + "|" + userId + "|" + targetId + "|" + rating + "|" + comment + "|" + status;
    }
}
