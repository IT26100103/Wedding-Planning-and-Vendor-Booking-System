package com.wedding.servlet;

/**
 * ============================================================================
 * CONTROLLER SERVLET: VendorServlet
 * ============================================================================
 * ARCHITECTURAL PATTERN: MVC (Model-View-Controller)
 * - Controller component managing wedding service vendor catalog interactions.
 * - Mapped URL pattern: /vendors
 * - Actions supported:
 *     list   - Browse vendors with category filtering (All, Catering, Photography)
 *     view   - View vendor details and calculate pricing based on guest headcount
 *     new    - Display vendor creation form (Admin only)
 *     edit   - Display vendor edit form (Admin only)
 *     save   - Create or update vendor record in vendors.txt
 *     delete - Remove vendor from system (Admin only)
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */


import com.wedding.dao.ReviewDao;
import com.wedding.dao.VendorDao;
import com.wedding.model.CateringVendor;
import com.wedding.model.PhotographyVendor;
import com.wedding.model.Review;
import com.wedding.model.Vendor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/vendors")
public class VendorServlet extends HttpServlet {

    private final VendorDao vendorDao = new VendorDao();
    private final ReviewDao reviewDao = new ReviewDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) action = "list";

        if ("new".equals(action)) {
            request.setAttribute("vendor", null);
            request.getRequestDispatcher("/vendor/vendor-form.jsp").forward(request, response);
            return;
        }

        if ("edit".equals(action)) {
            String id = request.getParameter("id");
            Vendor v = vendorDao.findById(id);
            request.setAttribute("vendor", v);
            request.getRequestDispatcher("/vendor/vendor-form.jsp").forward(request, response);
            return;
        }

        if ("view".equals(action)) {
            String id = request.getParameter("id");
            Vendor v = vendorDao.findById(id);
            if (v != null) {
                request.setAttribute("vendor", v);
                List<Review> reviews = reviewDao.findByTarget(id);
                double avgRating = reviewDao.getAverageRating(id);
                request.setAttribute("reviews", reviews);
                request.setAttribute("avgRating", avgRating);
            }
            request.getRequestDispatcher("/vendor/vendor-view.jsp").forward(request, response);
            return;
        }

        // List / Search
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");

        List<Vendor> vendors;
        if (keyword != null && !keyword.isBlank()) {
            vendors = vendorDao.search(keyword);
        } else {
            vendors = vendorDao.findAll();
        }

        if (category != null && !category.isBlank() && !"ALL".equalsIgnoreCase(category)) {
            List<Vendor> filtered = new ArrayList<>();
            for (Vendor v : vendors) {
                if (v.getCategory().equalsIgnoreCase(category)) {
                    filtered.add(v);
                }
            }
            vendors = filtered;
        }

        request.setAttribute("vendors", vendors);
        request.getRequestDispatcher("/vendor/vendor-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            vendorDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/vendors");
            return;
        }

        String id = read(request, "id");
        String name = read(request, "name");
        String phone = read(request, "phone");
        String priceStr = read(request, "basePrice");
        String category = read(request, "category");

        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Price must be a valid number.");
            request.getRequestDispatcher("/vendor/vendor-form.jsp").forward(request, response);
            return;
        }

        Vendor vendor;
        boolean isNew = id.isEmpty();
        if (isNew) {
            id = vendorDao.nextId();
        }

        if ("CATERING".equalsIgnoreCase(category)) {
            vendor = new CateringVendor(id, name, phone, price);
        } else {
            vendor = new PhotographyVendor(id, name, phone, price);
        }

        if (isNew) {
            vendorDao.add(vendor);
        } else {
            vendorDao.update(vendor);
        }

        response.sendRedirect(request.getContextPath() + "/vendors");
    }

    private String read(HttpServletRequest req, String name) {
        String val = req.getParameter(name);
        return val == null ? "" : val.trim();
    }
}
