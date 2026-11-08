# Vows &amp; Vendors — Wedding Planning and Vendor Booking System
> **SE1020 Object-Oriented Programming (OOP) Project**  
> **Repository:** [IT26100103/Wedding-Planning-and-Vendor-Booking-System](https://github.com/IT26100103/Wedding-Planning-and-Vendor-Booking-System)

---

## 1. System Overview
**Vows &amp; Vendors** is a comprehensive Java EE web application designed to simplify wedding coordination and supplier discovery in Sri Lanka. It integrates user authentication, vendor catalogs with dynamic pricing models, guest list tracking with online RSVP management, client reviews with weighted ratings, and venue exploration.

### Key Capabilities:
- **Port:** Runs locally on `http://localhost:8085/`
- **Architecture:** Model-View-Controller (MVC) + Data Access Object (DAO) Pattern
- **Data Persistence:** Thread-safe flat-file storage in `%USERPROFILE%\wedding-data\`
- **Auto-Seeding:** Missing data files are automatically initialized from `sample-data/` on startup

---

## 2. Object-Oriented Programming (OOP) Architecture
The system demonstrates the four pillars of OOP:

### A. Abstraction &amp; Inheritance
1. **User Subsystem**:
   - `User.java` (abstract class): Common credentials and identity (`id`, `name`, `email`, `username`, `password`).
   - `RegularUser.java`: Specialization for couples with wedding dates.
   - `AdminUser.java`: Specialization with administrative permission levels.
2. **Vendor Subsystem**:
   - `Vendor.java` (abstract class): Core supplier fields with abstract `calculateCost(int guests)` and `getCategory()`.
   - `CateringVendor.java`: Price scales per plate (`basePrice * guests`).
   - `PhotographyVendor.java`: Fixed package price (`basePrice`).
3. **Guest Subsystem**:
   - `Guest.java` (abstract class): Common contact info and RSVP status.
   - `RegularGuest.java`: Standard wedding invitee.
   - `VipGuest.java`: Specialization with reserved table identifier.
4. **Review Subsystem**:
   - `Review.java` (abstract class): Common rating attributes and abstract `getWeight()`.
   - `PublicReview.java`: 1x rating weight.
   - `VerifiedReview.java`: 2x rating weight for confirmed bookings.
5. **Venue Subsystem**:
   - `Venue.java` (abstract class): Base venue details and abstract `calculateCost(int hours)`.
   - `IndoorVenue.java` and `OutdoorVenue.java`.

### B. Polymorphism
- Dynamic calculation of costs: Catering per plate vs Photography fixed rates vs Venue hourly rates.
- Dynamic weighting in review aggregation: Verified booking reviews hold double weight when calculating average ratings.
- Dynamic file serialization: Each entity cleanly serializes its specialized attributes to pipe-delimited flat-file rows.

### C. Encapsulation
- All entity attributes are private, exposed through standard getters, setters, and business methods.

### D. Architectural Patterns
- **DAO Pattern:** Data access objects (`UserDao`, `VendorDao`, `GuestDao`, `ReviewDao`, `VenueDao`, `BookingDao`) decouple persistence logic from business logic.
- **MVC Pattern:** Servlets act as controllers processing requests, DAOs handle data access, and JSP views render the UI.

---

## 3. Directory &amp; Component Structure
```text
Wedding-Planning-and-Vendor-Booking-System/
├── src/
│   └── main/
│       ├── java/com/wedding/
│       │   ├── dao/          <- Data Access Objects (UserDao, VendorDao, GuestDao, ReviewDao, VenueDao, BookingDao)
│       │   ├── model/        <- OOP Abstract & Concrete entity models
│       │   ├── servlet/      <- MVC Controllers (UserServlet, VendorServlet, GuestServlet, ReviewServlet, VenueServlet, RsvpServlet)
│       │   └── util/         <- Centralized thread-safe FileUtil.java with auto-seeding
│       └── webapp/
│           ├── assets/       <- CSS, JS, and image design system
│           ├── guest/        <- guest-list.jsp, guest-form.jsp, rsvp.jsp
│           ├── includes/     <- header.jsp, footer.jsp
│           ├── review/       <- review-list.jsp, review-submit.jsp, review-admin.jsp
│           ├── user/         <- login.jsp, register.jsp, profile-edit.jsp, user-list.jsp
│           ├── vendor/       <- vendor-list.jsp, vendor-form.jsp, vendor-view.jsp
│           ├── venue/        <- venue-list.jsp
│           ├── wedding/      <- wedding-details.jsp
│           ├── WEB-INF/      <- web.xml deployment descriptor
│           └── index.jsp     <- System landing page
├── sample-data/              <- Default .txt seed data files
├── pom.xml                   <- Maven project configuration with Jetty EE10
├── run.bat                   <- One-click Windows startup script
└── README.md                 <- System documentation
```

---

## 4. How to Run Locally

### Option A: One-Click Startup (Recommended)
Double-click `run.bat` in the project root folder. It will:
1. Ensure your `%USERPROFILE%\wedding-data` directory exists.
2. Seed the necessary `.txt` sample data files if they do not already exist.
3. Automatically launch Jetty on port `8085`.
4. Open your browser at: **`http://localhost:8085/`**

### Option B: Command Line (Maven)
```bash
mvn clean compile jetty:run
```
Visit **`http://localhost:8085/`** in your browser.

---

## 5. Seed Test Credentials
| Role | Username / Email | Password | Access / Capabilities |
| :--- | :--- | :--- | :--- |
| **Regular Couple** | `nimal` / `nimal@example.com` | `pass123` | My Wedding dashboard, Profile, RSVP, Reviews |
| **Regular Couple** | `ayesha` / `ayesha@example.com` | `pass123` | Couple dashboard and booking reviews |
| **Administrator** | `admin` / `admin@example.com` | `admin123` | Full admin: User Management, Review Moderation, Vendor Registration |
