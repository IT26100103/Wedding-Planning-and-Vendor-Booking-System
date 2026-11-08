package com.wedding.servlet;

/**
 * ============================================================================
 * CONTROLLER SERVLET: UserServlet
 * ============================================================================
 * ARCHITECTURAL PATTERN: MVC (Model-View-Controller)
 * - Controller component handling user authentication, registration, session management,
 *   profile editing, and administrative user lists.
 * - Mapped URL patterns:
 *     /login    - User login authentication and session creation
 *     /logout   - Session invalidation and redirect
 *     /register - New regular user self-registration
 *     /profile  - Couple profile viewing and wedding date updates
 *     /users    - Administrative user management table and role modifications
 *
 * @author Wedding Planning System Team
 * @version 1.0
 */


import com.wedding.dao.UserDao;
import com.wedding.model.AdminUser;
import com.wedding.model.RegularUser;
import com.wedding.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet({"/users", "/login", "/logout", "/register", "/profile"})
public class UserServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if ("/login".equals(path)) {
            String msg = request.getParameter("msg");
            if ("logged_out".equals(msg)) {
                request.setAttribute("info", "You have been logged out successfully.");
            }
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            return;
        }

        if ("/logout".equals(path)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login?msg=logged_out");
            return;
        }

        if ("/register".equals(path)) {
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        if ("/profile".equals(path)) {
            User current = (User) request.getSession().getAttribute("currentUser");
            if (current == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            // Reload user fresh from DAO
            User fresh = userDao.findById(current.getId());
            if (fresh != null) {
                current = fresh;
                request.getSession().setAttribute("currentUser", fresh);
            }
            request.setAttribute("profileUser", current);
            request.getRequestDispatcher("/user/profile-edit.jsp").forward(request, response);
            return;
        }

        // Default: /users (User Management)
        String action = request.getParameter("action");
        if ("edit".equals(action)) {
            String id = request.getParameter("id");
            request.setAttribute("editUser", userDao.findById(id));
            request.getRequestDispatcher("/user/profile-edit.jsp").forward(request, response);
            return;
        }

        List<User> userList = userDao.findAll();
        request.setAttribute("users", userList);
        request.getRequestDispatcher("/user/user-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();

        if ("/login".equals(path)) {
            handleLogin(request, response);
            return;
        }

        if ("/register".equals(path)) {
            handleRegister(request, response);
            return;
        }

        if ("/profile".equals(path)) {
            handleProfileUpdate(request, response);
            return;
        }

        // /users actions: delete or admin edit
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            userDao.delete(id);
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }

        if ("save".equals(action)) {
            handleProfileUpdate(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = read(request, "username");
        String password = read(request, "password");

        if (username.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Please provide both username and password.");
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            return;
        }

        User user = userDao.login(username, password);
        if (user == null) {
            request.setAttribute("error", "Invalid username or password. Please try again.");
            request.setAttribute("enteredUsername", username);
            request.getRequestDispatcher("/user/login.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("currentUser", user);

        // Redirect based on role or wedding details
        if ("ADMIN".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/wedding");
        } else {
            response.sendRedirect(request.getContextPath() + "/wedding");
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = read(request, "fullName");
        String email = read(request, "email");
        String username = read(request, "username");
        String password = read(request, "password");
        String confirmPassword = read(request, "confirmPassword");
        String weddingDate = read(request, "weddingDate");

        if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "All fields marked with an asterisk are required.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match. Please re-enter.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        if (userDao.findByUsername(username) != null) {
            request.setAttribute("error", "Username '" + username + "' is already taken. Please choose another.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        String id = userDao.nextId("USER");
        RegularUser newUser = new RegularUser(id, name, email, username, password, weddingDate);
        boolean added = userDao.add(newUser);

        if (!added) {
            request.setAttribute("error", "Could not complete registration. Please try again.");
            request.getRequestDispatcher("/user/register.jsp").forward(request, response);
            return;
        }

        // Automatically log in the newly registered user
        request.getSession().setAttribute("currentUser", newUser);
        response.sendRedirect(request.getContextPath() + "/wedding?welcome=1");
    }

    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = read(request, "userId");
        User existing = userDao.findById(id);

        if (existing == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String name = read(request, "fullName");
        String email = read(request, "email");
        String password = read(request, "password");

        if (!name.isEmpty()) existing.setName(name);
        if (!email.isEmpty()) existing.setEmail(email);
        if (!password.isEmpty()) existing.setPassword(password);

        if (existing instanceof RegularUser) {
            String weddingDate = read(request, "weddingDate");
            if (!weddingDate.isEmpty()) {
                ((RegularUser) existing).setWeddingDate(weddingDate);
            }
        } else if (existing instanceof AdminUser) {
            String permission = read(request, "permissionLevel");
            if (!permission.isEmpty()) {
                ((AdminUser) existing).setPermissionLevel(permission);
            }
        }

        userDao.update(existing);

        // Update session if editing self
        User current = (User) request.getSession().getAttribute("currentUser");
        if (current != null && current.getId().equals(existing.getId())) {
            request.getSession().setAttribute("currentUser", existing);
        }

        request.setAttribute("info", "Profile updated successfully.");
        request.setAttribute("profileUser", existing);
        request.getRequestDispatcher("/user/profile-edit.jsp").forward(request, response);
    }

    private String read(HttpServletRequest req, String name) {
        String val = req.getParameter(name);
        return val == null ? "" : val.trim();
    }
}
