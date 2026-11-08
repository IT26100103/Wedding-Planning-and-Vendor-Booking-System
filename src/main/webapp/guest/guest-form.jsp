<%--
============================================================================
VIEW COMPONENT: guest-form.jsp
============================================================================
ARCHITECTURAL ROLE: MVC View (JSP Presentation Layer)
- Rendered by: GuestServlet (GET /guests?action=new, GET /guests?action=edit)
- Target POST: GuestServlet (POST /guests with action=save)
- Model attributes consumed:
    * ${guest} : Existing Guest instance when editing, or null when adding new
    * ${error} : Validation error message string if form input is invalid
- Dynamic Behavior:
    * JavaScript toggleTableNumber() shows/hides table assignment field
      based on polymorphic Guest type (VIP vs Regular).
============================================================================
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>${empty guest.guestId ? "Add New Guest" : "Edit Guest"} | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="max-width: 650px; margin: 2rem auto;">
        <div class="frame">
            <h1>${empty guest.guestId ? "Add New Guest" : "Edit Guest"}</h1>
            <p class="lead" style="margin-bottom: 2rem;">Manage RSVP invitation, contact details, and VIP seating.</p>

            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <form class="form" method="post" action="${pageContext.request.contextPath}/guests">
                <input type="hidden" name="action" value="save">
                <input type="hidden" name="guestId" value="${guest.guestId}">

                <div>
                    <label for="guestType">Guest Classification *</label>
                    <select id="guestType" name="guestType" onchange="toggleTableNumber()">
                        <option value="REGULAR" ${guest.guestType == "REGULAR" ? "selected" : ""}>Regular Guest</option>
                        <option value="VIP" ${guest.guestType == "VIP" ? "selected" : ""}>VIP Guest (Reserved Table)</option>
                    </select>
                </div>

                <div>
                    <label for="rsvpStatus">RSVP Response</label>
                    <select id="rsvpStatus" name="rsvpStatus">
                        <option value="PENDING" ${guest.rsvpStatus == "PENDING" ? "selected" : ""}>Pending</option>
                        <option value="ACCEPTED" ${guest.rsvpStatus == "ACCEPTED" ? "selected" : ""}>Accepted</option>
                        <option value="DECLINED" ${guest.rsvpStatus == "DECLINED" ? "selected" : ""}>Declined</option>
                    </select>
                </div>

                <div class="full">
                    <label for="fullName">Guest Full Name *</label>
                    <input type="text" id="fullName" name="fullName" value="${guest.fullName}" required placeholder="e.g. Nimal Perera">
                </div>

                <div>
                    <label for="email">Email Address *</label>
                    <input type="email" id="email" name="email" value="${guest.email}" required placeholder="guest@example.com">
                </div>

                <div>
                    <label for="phone">Phone Number *</label>
                    <input type="tel" id="phone" name="phone" value="${guest.phone}" required placeholder="077-1234567">
                </div>

                <div class="full" id="tableGroup" style="display: none;">
                    <label for="tableNumber">Assigned Table Number (VIP Only)</label>
                    <input type="text" id="tableNumber" name="tableNumber" value="${guest.tableNumber}" placeholder="e.g. Table 01 or Head Table">
                    <small style="color: var(--muted); font-size: 0.82rem; margin-top: 0.25rem; display: block;">
                        Required for VIP guests to guarantee reserved seating near the couple.
                    </small>
                </div>

                <div class="full" style="display: flex; gap: 0.8rem; margin-top: 1rem;">
                    <button type="submit" class="btn">Save Guest Invitation</button>
                    <a href="${pageContext.request.contextPath}/guests" class="btn outline">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</main>

<script>
function toggleTableNumber() {
    var type = document.getElementById("guestType").value;
    var group = document.getElementById("tableGroup");
    group.style.display = (type === "VIP") ? "block" : "none";
}
// Run on initial page load to reflect current selection
toggleTableNumber();
</script>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
