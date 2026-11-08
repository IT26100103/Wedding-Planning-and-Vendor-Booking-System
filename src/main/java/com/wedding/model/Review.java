package com.wedding.model;

import com.wedding.util.FileUtil;

public abstract class Review {
    private String id;
    private String userId;       // who wrote the review
    private String targetId;     // vendor id or venue id being reviewed
    private int rating;          // 1 to 5
    private String comment;
    private String status;       // PUBLISHED or HIDDEN (admin moderation)

    public Review(String id, String userId, String targetId, int rating,
                  String comment, String status) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.rating = rating;
        this.comment = FileUtil.clean(comment);   // a pipe would break the file format
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

    // Each child class writes its own version
    public abstract String getReviewType();
    public abstract int getWeight();
    public abstract String toFileString();

    public void display() {
        System.out.println("[" + getReviewType() + "] " + id + " | User: " + userId
                + " | " + targetId + " | " + rating + "/5 | " + comment + " | " + status);
    }

    protected String baseFields() {
        return id + "|" + userId + "|" + targetId + "|" + rating + "|" + comment + "|" + status;
    }
}
