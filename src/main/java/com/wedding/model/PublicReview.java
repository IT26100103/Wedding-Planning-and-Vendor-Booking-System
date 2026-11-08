package com.wedding.model;

/**
 * ============================================================================
 * CONCRETE CLASS: PublicReview
 * ============================================================================
 * OOP CONCEPTS DEMONSTRATED:
 * 1. INHERITANCE:
 *    - Inherits core properties and validation logic from abstract class Review.
 * 2. POLYMORPHISM:
 *    - getReviewType(): returns "PUBLIC".
 *    - getWeight(): returns 1 (standard 1x weight in average rating calculation).
 *    - toFileString(): appends "|PUBLIC|-" to the base fields.
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
public class PublicReview extends Review {

    /**
     * Parameterized constructor for a standard community review.
     *
     * @param id       Unique review identifier
     * @param userId   Author user ID
     * @param targetId Target vendor or venue ID
     * @param rating   Score from 1 to 5
     * @param comment  Review textual feedback
     * @param status   Moderation status (PUBLISHED/HIDDEN)
     */
    public PublicReview(String id, String userId, String targetId, int rating,
                        String comment, String status) {
        super(id, userId, targetId, rating, comment, status);
    }

    /**
     * Identifies this review as an unverified public submission.
     *
     * @return "PUBLIC"
     */
    @Override
    public String getReviewType() { return "PUBLIC"; }

    /**
     * Standard weight factor for unverified reviews in aggregate rating calculations.
     *
     * @return 1
     */
    @Override
    public int getWeight() { return 1; }

    /**
     * Serializes public review record to persistence format.
     * format: id|userId|targetId|rating|comment|status|PUBLIC|-
     *
     * @return Serialized line for reviews.txt
     */
    @Override
    public String toFileString() { return baseFields() + "|PUBLIC|-"; }
}
