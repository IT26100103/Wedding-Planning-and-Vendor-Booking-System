# Feedback and Review Management Component
> **Vows & Vendors Wedding Planning System — SE1020 OOP Project**
> **Assigned Role:** `Team Member 6 - Reviews & Feedback Management`

---

## 1. Component Overview
The **Feedback and Review Management** module is a standalone, fully-functional component of the Vows & Vendors Wedding Planning System. It encapsulates all backend business logic, object-oriented data models, data access objects (DAOs), servlets, and modern JSP frontend views for this specific subsystem.

### Key Capabilities:
- **Port:** Runs locally on `http://localhost:8086/`
- **Primary Welcome Page:** `review/review-list.jsp`
- **Data Persistence:** Thread-safe flat-file storage in `%USERPROFILE%\wedding-data\`
- **Sample Data Included:** Seeded automatically from `sample-data/`

---

## 2. Object-Oriented Programming (OOP) Architecture
This component is crafted specifically to demonstrate core OOP principles evaluated in the coursework:


### OOP Concepts in this Module
1. **Abstraction**:
   - `Review.java` is an `abstract class` defining customer feedback, ratings (1 to 5), comments, and moderation status, declaring abstract `getWeight()` and `getReviewType()`.
2. **Inheritance**:
   - `PublicReview.java` and `VerifiedReview.java` extend `Review.java`.
3. **Polymorphism**:
   - `getWeight()` provides weighted mathematical scoring:
     - `PublicReview`: weight = 1 (normal visitor score).
     - `VerifiedReview`: weight = 2 (verified buyer with confirmed booking ID counts double).
4. **Encapsulation**:
   - Star ratings, comments, and visibility flags are encapsulated with validation rules.
5. **Architectural Patterns**:
   - **DAO Pattern**: `ReviewDao.java` manages `reviews.txt` and computes weighted average ratings.
   - **MVC Pattern**: `ReviewServlet.java` handles customer review submission and admin moderation.


---

## 3. Directory & File Structure
```text
06_Feedback_Review_Management/
├── backend/                  <- Direct link to Java source files
├── frontend/                 <- Direct link to JSP & Web assets
├── sample-data/              <- Default .txt data files for this module
├── src/
│   └── main/
│       ├── java/com/wedding/
│       │   ├── model/        <- OOP Abstract & Concrete entity models
│       │   ├── dao/          <- Data Access Objects for flat-file persistence
│       │   ├── servlet/      <- MVC Controllers handling HTTP requests
│       │   └── util/         <- Shared file utilities (FileUtil.java)
│       └── webapp/
│           ├── assets/       <- CSS, JS, and image design system
│           ├── includes/     <- Reusable header and footer JSP partials
│           ├── WEB-INF/      <- web.xml deployment descriptor
│           └── index.jsp     <- Module landing page
├── pom.xml                   <- Maven project configuration with Jetty EE10
├── run.bat                   <- One-click Windows startup script
└── README.md                 <- This documentation guide
```

---

## 4. How to Run Locally

### Option A: One-Click Script (Recommended)
Double-click `run.bat` in the project root folder. It will:
1. Ensure your `%USERPROFILE%\wedding-data` directory exists.
2. Seed the necessary `.txt` sample data files if they do not already exist.
3. Automatically launch Jetty on port `8086`.
4. Open your browser at: **`http://localhost:8086/`**

### Option B: Command Line (Maven)
```bash
# 1. Open Terminal in this module's folder
cd 06_Feedback_Review_Management

# 2. Compile and run
mvn clean compile jetty:run
```
Visit **`http://localhost:8086/`** in your browser.

---

## 5. Step-by-Step GitHub Setup & Push Guide
Each team member can push this component to their own individual GitHub repository. Follow these exact terminal commands:

### Step 1: Create a New GitHub Repository
1. Log in to [GitHub](https://github.com/).
2. Click the **+** icon in the top-right corner and select **New repository**.
3. Repository name: `06-feedback-review-management` (or your preferred name).
4. Description: `Vows & Vendors - Feedback and Review Management Component`.
5. Visibility: **Public** (or Private if required by your instructor).
6. **Do NOT check** "Add a README", ".gitignore", or "license" (we already provide them).
7. Click **Create repository**.
8. Copy your repository URL (e.g., `https://github.com/<your-username>/06-feedback-review-management.git`).

### Step 2: Initialize Git and Commit Your Code
Open PowerShell or Git Bash inside this extracted module folder:

```bash
# 1. Initialize Git repository
git init

# 2. Set default branch to main
git branch -M main

# 3. Stage all files (frontend, backend, configs, README)
git add .

# 4. Create your initial commit
git commit -m "feat(06_Feedback_Review_Management): complete implementation of Feedback and Review Management component"

# 5. Link to your GitHub repository (replace with your actual GitHub URL)
git remote add origin https://github.com/<your-username>/06-feedback-review-management.git

# 6. Push your code to GitHub
git push -u origin main
```

### Step 3: Verifying Your GitHub Repository
1. Refresh your GitHub repository page in your browser.
2. You will see the entire codebase with the `README.md` beautifully rendered.
3. You can share your repository link with your instructor or project lead.

---

## 6. How to Integrate Back into the Main Project
When all team members finish their individual features:
1. The backend classes (`model/`, `dao/`, `servlet/`) can be copied directly into the master `src/main/java/com/wedding/` directory.
2. The frontend views can be copied into `src/main/webapp/`.
3. The integrated project will compile seamlessly because all naming conventions and data storage schemas (`%USERPROFILE%\wedding-data\`) are standardized across all 6 modules.

---
*Created for the Vows & Vendors Wedding Planning System Team.*
