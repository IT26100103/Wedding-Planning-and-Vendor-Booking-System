package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: VerifiedReview
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE & SPECIALIZATION:
 *    - Extends Review base class.
 *    - Adds bookingId specialization, linking the review to an authentic booking record.
 * 2. POLYMORPHISM:
 *    - getReviewType(): returns "VERIFIED".
 *    - getWeight(): returns 2 (counts with double weight in rating averages
 *      because it represents a verified transaction).
 *    - toFileString(): persists the linked booking ID.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class VerifiedReview extends Review {
    /** The booking transaction identifier that verifies genuine service usage */
    private String bookingId;

    /**
     * Parameterized constructor for a verified customer review.
     *
     * @param id        Unique review ID
     * @param userId    Reviewer user ID
     * @param targetId  Target vendor or venue ID
     * @param rating    Score from 1 to 5
     * @param comment   Textual comments
     * @param status    Moderation status (PUBLISHED/HIDDEN)
     * @param bookingId Linked booking ID confirming actual service use
     */
    public VerifiedReview(String id, String userId, String targetId, int rating,
                          String comment, String status, String bookingId) {
        super(id, userId, targetId, rating, comment, status);
        this.bookingId = bookingId;
    }

    /**
     * Retrieves the linked booking confirmation identifier.
     *
     * @return booking ID string
     */
    public String getBookingId() { return bookingId; }

    /**
     * Identifies this review as verified by an actual booking.
     *
     * @return "VERIFIED"
     */
    @Override
    public String getReviewType() { return "VERIFIED"; }

    /**
     * Verified reviews receive higher statistical weight (2x) in average calculations.
     *
     * @return 2
     */
    @Override
    public int getWeight() { return 2; }

    /**
     * Serializes verified review record to persistence format.
     * format: id|userId|targetId|rating|comment|status|VERIFIED|bookingId
     *
     * @return Serialized line for reviews.txt
     */
    @Override
    public String toFileString() { return baseFields() + "|VERIFIED|" + bookingId; }
}
