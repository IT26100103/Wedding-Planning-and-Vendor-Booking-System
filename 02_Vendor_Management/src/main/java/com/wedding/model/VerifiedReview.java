package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: VerifiedReview
 * ============================================================================
 * OOP CONCEPT: INHERITANCE & SPECIALIZATION
 * - Represents verified customer reviews tied to an authentic booking ID.
 * - Weight factor is 2 (counts double in weighted rating calculations).
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class VerifiedReview extends Review {
    private String bookingId;

    public VerifiedReview(String id, String userId, String targetId, int rating,
                          String comment, String status, String bookingId) {
        super(id, userId, targetId, rating, comment, status);
        this.bookingId = bookingId;
    }

    public String getBookingId() { return bookingId; }

    @Override
    public String getReviewType() { return "VERIFIED"; }

    @Override
    public int getWeight() { return 2; } // Double weight in average

    @Override
    public String toFileString() { return baseFields() + "|VERIFIED|" + bookingId; }
}
