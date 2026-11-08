<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
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
</body>
</html>
