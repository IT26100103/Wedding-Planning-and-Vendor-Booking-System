<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RSVP</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width: 500px;">
    <h2 class="mb-3">Wedding RSVP</h2>

    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/rsvp">
        <div class="mb-3">
            <label class="form-label">Your guest ID</label>
            <input type="text" name="guestId" class="form-control" placeholder="For example G001"
                   value="${param.id}" required>
        </div>

        <div class="mb-3">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="rsvpStatus" value="ACCEPTED" required>
                <label class="form-check-label">I will attend</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="rsvpStatus" value="DECLINED">
                <label class="form-check-label">I cannot attend</label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Send reply</button>
    </form>
</div>
<script src="../assets/js/vows.js"></script>
</body>
</html>
