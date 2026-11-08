<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<<<<<<< HEAD
    <meta charset="UTF-8">
    <title>Guest Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width: 600px;">
    <h2 class="mb-3">${empty guest.guestId ? "Add Guest" : "Edit Guest"}</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/guests">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="guestId" value="${guest.guestId}">

        <div class="mb-3">
            <label class="form-label">Guest type</label>
            <select name="guestType" class="form-select">
                <option value="REGULAR" ${guest.guestType == "REGULAR" ? "selected" : ""}>Regular</option>
                <option value="VIP" ${guest.guestType == "VIP" ? "selected" : ""}>VIP</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Full name</label>
            <input type="text" name="fullName" class="form-control" value="${guest.fullName}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" value="${guest.email}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Phone (10 digits)</label>
            <input type="text" name="phone" class="form-control" value="${guest.phone}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">RSVP status</label>
            <select name="rsvpStatus" class="form-select">
                <option value="PENDING" ${guest.rsvpStatus == "PENDING" ? "selected" : ""}>Pending</option>
                <option value="ACCEPTED" ${guest.rsvpStatus == "ACCEPTED" ? "selected" : ""}>Accepted</option>
                <option value="DECLINED" ${guest.rsvpStatus == "DECLINED" ? "selected" : ""}>Declined</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Table number (VIP guests only)</label>
            <input type="text" name="tableNumber" class="form-control" value="${tableNumber}">
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
        <a href="${pageContext.request.contextPath}/guests" class="btn btn-secondary">Cancel</a>
    </form>
</div>
<script src="../assets/js/vows.js"></script>
=======
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
                    <label for="phone">Phone Number (10 digits) *</label>
                    <input type="text" id="phone" name="phone" value="${guest.phone}" required placeholder="0771234567">
                </div>

                <div class="full" id="tableGroup">
                    <label for="tableNumber">Reserved Table Identifier (VIP only)</label>
                    <input type="text" id="tableNumber" name="tableNumber" value="${tableNumber}" placeholder="e.g. T1, T2, Head Table">
                </div>

                <div class="full" style="display: flex; gap: 0.8rem; margin-top: 1rem;">
                    <button type="submit" class="btn" style="flex: 1;">Save Guest</button>
                    <a href="${pageContext.request.contextPath}/guests" class="btn outline">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

<script>
function toggleTableNumber() {
    var type = document.getElementById('guestType').value;
    var tg = document.getElementById('tableGroup');
    if (type === 'VIP') {
        tg.style.display = 'block';
    } else {
        tg.style.display = 'none';
    }
}
toggleTableNumber();
</script>
>>>>>>> origin/main
</body>
</html>
