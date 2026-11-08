package com.wedding.servlet;

import com.wedding.dao.GuestDao;
import com.wedding.model.Guest;
import com.wedding.model.RegularGuest;
import com.wedding.model.VipGuest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/guests")
public class GuestServlet extends HttpServlet {

    private final GuestDao guestDao = new GuestDao();

    // GET: the browser wants to SEE a page (list, empty form, edit form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        if (action.equals("new")) {
            showForm(request, response, null);
        } else if (action.equals("edit")) {
            Guest guest = guestDao.findById(request.getParameter("id"));
            showForm(request, response, guest);
        } else {
            String keyword = request.getParameter("keyword");
            List<Guest> guests;
            if (keyword == null || keyword.isBlank()) {
                guests = guestDao.findAll();
            } else {
                guests = guestDao.searchByName(keyword);
            }
            request.setAttribute("guests", guests);
            request.getRequestDispatcher("/guest/guest-list.jsp").forward(request, response);
        }
    }

    // POST: the browser is SENDING data (save the form, or delete a guest)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            guestDao.delete(request.getParameter("id"));
            response.sendRedirect(request.getContextPath() + "/guests");
            return;
        }

        // Otherwise the action is save: add a new guest or update an existing one
        Guest guest = buildGuest(request);
        String error = validate(guest);
        if (error != null) {
            request.setAttribute("error", error);
            showForm(request, response, guest);
            return;
        }

        if (guest.getGuestId().isEmpty()) {
            guest.setGuestId(guestDao.nextId());
            guestDao.add(guest);
        } else {
            guestDao.update(guest);
        }
        response.sendRedirect(request.getContextPath() + "/guests");
    }

    // Shows the add/edit form, filled with the guest if there is one
    private void showForm(HttpServletRequest request, HttpServletResponse response, Guest guest)
            throws ServletException, IOException {
        request.setAttribute("guest", guest);
        if (guest instanceof VipGuest) {
            request.setAttribute("tableNumber", ((VipGuest) guest).getTableNumber());
        }
        request.getRequestDispatcher("/guest/guest-form.jsp").forward(request, response);
    }

    // Reads the form fields and builds the right kind of guest
    private Guest buildGuest(HttpServletRequest request) {
        String id = read(request, "guestId");
        String type = read(request, "guestType");
        String name = read(request, "fullName");
        String email = read(request, "email");
        String phone = read(request, "phone");
        String status = read(request, "rsvpStatus");
        String table = read(request, "tableNumber");

        if (type.equals("VIP")) {
            return new VipGuest(id, name, email, phone, status, table);
        }
        return new RegularGuest(id, name, email, phone, status);
    }

    // Returns the trimmed value of a form field, or an empty text if it is missing
    private String read(HttpServletRequest request, String fieldName) {
        String value = request.getParameter(fieldName);
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    // Returns an error message, or null if everything is fine
    private String validate(Guest guest) {
        if (guest.getFullName().isEmpty()) {
            return "Name is required.";
        }
        if (!guest.getEmail().contains("@")) {
            return "Please enter a valid email address.";
        }
        if (!guest.getPhone().matches("[0-9]{10}")) {
            return "Phone number must be exactly 10 digits.";
        }
        if (guest instanceof VipGuest) {
            String table = ((VipGuest) guest).getTableNumber();
            if (table.isEmpty()) {
                return "A VIP guest needs a table number.";
            }
            if (table.contains("|")) {
                return "The | character is not allowed.";
            }
        }
        if (guest.getFullName().contains("|") || guest.getEmail().contains("|")) {
            return "The | character is not allowed.";
        }
        return null;
    }
}
