package com.wedding.servlet;

/**
 * ============================================================================
 * CONTROLLER SERVLET: RsvpServlet
 * ============================================================================
 * ARCHITECTURAL PATTERN: MVC (Model-View-Controller)
 * - Public-facing controller allowing invited guests to search their invitation
 *   and submit their RSVP attendance confirmation online.
 * - Mapped URL pattern: /rsvp
 * - Actions supported:
 *     GET  - Display public RSVP lookup search and response form
 *     POST - Update guest RSVP status to ACCEPTED or DECLINED
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */


import com.wedding.dao.GuestDao;
import com.wedding.model.Guest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/rsvp")
public class RsvpServlet extends HttpServlet {

    private final GuestDao guestDao = new GuestDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null) {
            request.setAttribute("guest", guestDao.findById(id));
        }
        request.getRequestDispatcher("/guest/rsvp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("guestId");
        String status = request.getParameter("rsvpStatus");

        boolean validReply = status != null
                && (status.equals("ACCEPTED") || status.equals("DECLINED"));

        if (id == null || !validReply) {
            request.setAttribute("error", "Please enter your guest ID and choose a reply.");
        } else {
            Guest guest = guestDao.findById(id.trim());
            if (guest == null) {
                request.setAttribute("error", "No guest found with that ID.");
            } else {
                guest.setRsvpStatus(status);
                guestDao.update(guest);
                request.setAttribute("guest", guest);
                request.setAttribute("message", "Thank you, " + guest.getFullName() + ". Your reply is saved: " + status);
            }
        }
        request.getRequestDispatcher("/guest/rsvp.jsp").forward(request, response);
    }
}
