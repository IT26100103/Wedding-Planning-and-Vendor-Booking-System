package com.wedding.servlet;

import com.wedding.dao.VenueDao;
import com.wedding.model.Venue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * ============================================================================
 * CONTROLLER SERVLET: VenueServlet
 * ============================================================================
 * ARCHITECTURAL PATTERN: MVC (Model-View-Controller)
 * - Manages venue catalog browsing and capacity exploration.
 * - Mapped URL pattern: /venues
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */
@WebServlet("/venues")
public class VenueServlet extends HttpServlet {

    private final VenueDao venueDao = new VenueDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String capacityStr = request.getParameter("guests");

        List<Venue> venues;
        if (keyword != null && !keyword.isBlank()) {
            venues = venueDao.search(keyword);
        } else if (capacityStr != null && !capacityStr.isBlank()) {
            try {
                int guests = Integer.parseInt(capacityStr);
                venues = venueDao.findByCapacity(guests);
            } catch (NumberFormatException e) {
                venues = venueDao.findAll();
            }
        } else {
            venues = venueDao.findAll();
        }

        request.setAttribute("venues", venues);
        request.getRequestDispatcher("/venue/venue-list.jsp").forward(request, response);
    }
}
