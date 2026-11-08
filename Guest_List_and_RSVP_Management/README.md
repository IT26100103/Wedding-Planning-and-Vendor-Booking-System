# Guest List & RSVP Management Module
**Component of the Wedding Planning and Vendor Booking System (SE1020 OOP Project)**

---

## 📌 Module Overview
The **Guest List and RSVP Management Module** handles the invitation lifecycle for a wedding celebration:
1. **Guest Directory & Management (Back Office):**
   - Add new guests, categorizing them as **Regular Guests** (open seating) or **VIP Guests** (table assignments).
   - Real-time RSVP monitoring badge updates (`ACCEPTED`, `DECLINED`, `PENDING`).
   - Guest contact directory (Full name, Email, Telephone).
   - Filter and search guests by keyword.
2. **Online RSVP Confirmation Portal (Public):**
   - Individual invitation portal where invitees look up their reservation code (`G001`, `G002`, etc.).
   - Displays designated seating tier (VIP reserved table vs open seating).
   - Interactive attendance response submission (`Joyfully Accepts` vs `Regretfully Declines`).

---

## 🏗️ Object-Oriented Programming (OOP) Architecture

```
                    ┌───────────────────────────┐
                    │       abstract Guest      │
                    ├───────────────────────────┤
                    │ - guestId: String         │
                    │ - fullName: String        │
                    │ - email: String           │
                    │ - phone: String           │
                    │ - rsvpStatus: String      │
                    ├───────────────────────────┤
                    │ + getType(): String       │
                    │ + getSeatingInfo(): String│
                    │ + toFileLine(): String    │
                    └─────────────┬─────────────┘
                                  │
                 ┌────────────────┴────────────────┐
                 ▼                                 ▼
    ┌───────────────────────────┐    ┌───────────────────────────┐
    │       RegularGuest        │    │         VipGuest          │
    ├───────────────────────────┤    ├───────────────────────────┤
    │ (inherits base properties)│    │ - tableNumber: String     │
    ├───────────────────────────┤    ├───────────────────────────┤
    │ + getType(): "REGULAR"    │    │ + getType(): "VIP"        │
    │ + getSeatingInfo():       │    │ + getSeatingInfo():       │
    │   "General / Open Seating"│    │   "Table {X} (Reserved)"  │
    └───────────────────────────┘    └───────────────────────────┘
```

### 1. Abstraction
- `Guest`: Abstract base class specifying template methods `getType()`, `getSeatingInfo()`, and `toFileLine()`.

### 2. Inheritance & Specialization
- `RegularGuest`: Inherits standard attendee behavior.
- `VipGuest`: Specializes `Guest` by adding `tableNumber` attribute for designated table seating.

### 3. Polymorphism
- `getSeatingInfo()` dynamically formats seating display based on the concrete instance type.
- `toFileLine()` serializes each concrete subtype according to its specific flat-file format.

### 4. Encapsulation
- All instance fields are strictly `private`.
- Access is provided via public getter and setter methods.
- Input data is sanitized using `FileUtil.clean()` to avoid delimiter collision in pipe-delimited flat storage.

---

## 🏛️ MVC Architecture

| Layer | Component | Description |
|---|---|---|
| **Model** | `Guest`, `RegularGuest`, `VipGuest` | Core domain entities encapsulating attendee data and seating rules |
| **Data Access** | `GuestDao`, `FileUtil` | Handles CRUD operations and flat-file persistence (`guests.txt`) |
| **Controller** | `GuestServlet` (`/guests`), `RsvpServlet` (`/rsvp`) | Routes HTTP requests, validates form inputs, coordinates DAOs, and directs to views |
| **View (JSP)** | `guest-list.jsp`, `guest-form.jsp`, `rsvp.jsp` | Responsive presentation pages utilizing JSTL and CSS design system |

---

## 🚀 How to Run Standalone

### Prerequisites
- **JDK 17+** (JDK 21 recommended)
- **Apache Maven 3.8+**

### Execution
Run via Maven command line:
```bash
mvn clean jetty:run
```
Or double-click `run.bat` on Windows.

### Access Endpoints
- **Guest Management Portal:** [http://localhost:8085/guests](http://localhost:8085/guests)
- **Add New Guest:** [http://localhost:8085/guests?action=new](http://localhost:8085/guests?action=new)
- **Public RSVP Portal:** [http://localhost:8085/rsvp](http://localhost:8085/rsvp)
- **RSVP Direct Link (e.g., Guest G001):** [http://localhost:8085/rsvp?id=G001](http://localhost:8085/rsvp?id=G001)

---

## 💾 Storage & Data Format
Records are saved to `%USERPROFILE%/wedding-data/guests.txt`.

Sample records:
```
REGULAR|G001|Kasun Silva|kasun@example.com|0771234567|ACCEPTED
VIP|G002|Kamal Perera|kamal@example.com|0777654321|ACCEPTED|Table 01
REGULAR|G003|Dilani Fernando|dilani@example.com|0712345678|PENDING
VIP|G004|Sunil Wickrama|sunil@example.com|0789012345|DECLINED|Head Table
```
