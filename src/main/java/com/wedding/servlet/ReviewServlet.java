package com.wedding.servlet;

import com.wedding.dao.BookingDao;
import com.wedding.dao.ReviewDao;
import com.wedding.dao.VendorDao;
import com.wedding.dao.VenueDao;
import com.wedding.model.Booking;
import com.wedding.model.PublicReview;
import com.wedding.model.Review;
import com.wedding.model.User;
import com.wedding.model.Vendor;
import com.wedding.model.Venue;
import com.wedding.model.VerifiedReview;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/reviews")
public class ReviewServlet extends HttpServlet {

    private final ReviewDao reviewDao = new ReviewDao();
    private final BookingDao bookingDao = new BookingDao();
    private final VenueDao venueDao = new VenueDao();
    private final VendorDao vendorDao = new VendorDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        if ("admin".equals(action)) {
            List<Review> allReviews = reviewDao.findAll();
            attachResourceNames(allReviews, request);
            request.setAttribute("reviews", allReviews);
            request.getRequestDispatcher("/review/review-admin.jsp").forward(request, response);
            return;
        }

        if ("new".equals(action)) {
            prepareReviewForm(request, response);
            return;
        }

        // Browse / List
        String targetId = request.getParameter("targetId");
        List<Review> reviews;
        if (targetId != null && !targetId.isBlank()) {
            reviews = reviewDao.findByTarget(targetId);
            double avg = reviewDao.getAverageRating(targetId);
            request.setAttribute("selectedTargetId", targetId);
            request.setAttribute("avgRating", avg);
        } else {
            // Show all published reviews
            reviews = new java.util.ArrayList<>();
            for (Review r : reviewDao.findAll()) {
                if ("PUBLISHED".equals(r.getStatus())) {
                    reviews.add(r);
                }
            }
        }

        attachResourceNames(reviews, request);
        request.setAttribute("reviews", reviews);
        request.setAttribute("venues", venueDao.findAll());
        request.setAttribute("vendors", vendorDao.findAll());
        request.getRequestDispatcher("/review/review-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("moderate".equals(action)) {
            String id = request.getParameter("id");
            String status = request.getParameter("status");
            reviewDao.moderateReview(id, status);
            response.sendRedirect(request.getContextPath() + "/reviews?action=admin");
            return;
        }

        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            reviewDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/reviews?action=admin");
            return;
        }

        // Submit new review
        String targetId = read(request, "targetId");
        String ratingStr = read(request, "rating");
        String comment = read(request, "comment");

        User current = (User) request.getSession().getAttribute("currentUser");
        String userId = (current != null) ? current.getId() : "U001";

        int rating = 5;
        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException ignored) {}

        if (comment.isEmpty()) {
            request.setAttribute("error", "Comment is required to submit a review.");
            prepareReviewForm(request, response);
            return;
        }

        // Determine if user has a verified booking for this target
        String matchingBookingId = null;
        List<Booking> userBookings = bookingDao.findByUser(userId);
        for (Booking b : userBookings) {
            if (b.getResourceId().equals(targetId) && !"CANCELLED".equals(b.getStatus())) {
                matchingBookingId = b.getId();
                break;
            }
        }

        String id = reviewDao.nextId();
        Review review;
        if (matchingBookingId != null) {
            // Verified Review has 2x weight!
            review = new VerifiedReview(id, userId, targetId, rating, comment, "PUBLISHED", matchingBookingId);
        } else {
            // Public Review has 1x weight
            review = new PublicReview(id, userId, targetId, rating, comment, "PUBLISHED");
        }

        boolean ok = reviewDao.add(review);
        if (!ok) {
            request.setAttribute("error", "You have already submitted a review for this venue or vendor.");
            prepareReviewForm(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/reviews?targetId=" + targetId + "&submitted=1");
    }

    private void prepareReviewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("venues", venueDao.findAll());
        request.setAttribute("vendors", vendorDao.findAll());

        String targetId = request.getParameter("targetId");
        if (targetId != null) {
            request.setAttribute("selectedTargetId", targetId);
        }

        request.getRequestDispatcher("/review/review-submit.jsp").forward(request, response);
    }

    private void attachResourceNames(List<Review> list, HttpServletRequest request) {
        Map<String, String> names = new HashMap<>();
        for (Venue v : venueDao.findAll()) {
            names.put(v.getId(), v.getName());
        }
        for (Vendor v : vendorDao.findAll()) {
            names.put(v.getId(), v.getName());
        }
        request.setAttribute("targetNames", names);
    }

    private String read(HttpServletRequest req, String name) {
        String val = req.getParameter(name);
        return val == null ? "" : val.trim();
    }
}
