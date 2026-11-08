package com.wedding.model;

public class VerifiedReview extends Review {
    private String bookingId;

    public VerifiedReview(String id, String userId, String targetId, int rating, String comment, String status, String bookingId) {
        super(id, userId, targetId, rating, comment, status);
        this.bookingId = bookingId;
    }

    public String getBookingId() { 
        return bookingId; 
    }

    @Override
    public String getReviewType() { 
        return "VERIFIED"; 
    }

    @Override
    public int getWeight() { 
        return 2; 
    } // Double weight in average

    @Override
    public String toFileString() { 
        return baseFields() + "|VERIFIED|" + bookingId; 
    }
}
