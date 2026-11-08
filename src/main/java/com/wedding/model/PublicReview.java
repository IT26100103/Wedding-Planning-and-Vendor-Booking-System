package com.wedding.model;

public class PublicReview extends Review {

    public PublicReview(String id, String userId, String targetId, int rating, String comment, String status) {
        super(id, userId, targetId, rating, comment, status);
    }

    @Override
    public String getReviewType() { 
        return "PUBLIC"; 
    }

    @Override
    public int getWeight() { 
        return 1; 
    }

    @Override
    public String toFileString() { 
        return baseFields() + "|PUBLIC|-"; 
    }
}
