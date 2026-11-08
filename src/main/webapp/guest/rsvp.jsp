<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<<<<<<< HEAD
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
=======
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Wedding RSVP Portal | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="max-width: 520px; margin: 3rem auto;">
        <div class="frame" style="text-align: center;">
            <div style="font-size: 2.5rem; margin-bottom: 0.5rem;">💌</div>
            <h1 style="margin-bottom: 0.3rem;">Wedding RSVP</h1>
            <p class="lead" style="margin-bottom: 2rem; font-size: 0.95rem;">
                Kindly confirm your presence for our upcoming wedding celebration.
            </p>

            <c:if test="${not empty message}">
                <div class="alert info">✓ ${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <c:if test="${not empty guest}">
                <div style="background: var(--sand); border: 1px dashed var(--line); border-radius: var(--radius-sm); padding: 1rem; margin-bottom: 1.5rem; text-align: left;">
                    <p style="margin: 0; font-size: 0.92rem;">
                        Invited Guest: <strong><c:out value="${guest.fullName}"/></strong><br>
                        Seating Category: <strong>${guest.guestType}</strong> &middot; <c:out value="${guest.seatingInfo}"/>
                    </p>
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/rsvp" style="text-align: left;">
                <div style="margin-bottom: 1.5rem;">
                    <label for="guestId">Invitation Guest Code / ID *</label>
                    <input type="text" id="guestId" name="guestId" value="${not empty guest ? guest.guestId : param.id}" required placeholder="e.g. G001">
                </div>

                <div style="margin-bottom: 2rem;">
                    <label style="margin-bottom: 0.8rem;">Your Response *</label>
                    <div style="display: flex; gap: 1.5rem;">
                        <label style="display: flex; align-items: center; gap: 0.5rem; cursor: pointer;">
                            <input type="radio" name="rsvpStatus" value="ACCEPTED" required ${guest.rsvpStatus == 'ACCEPTED' ? 'checked' : ''}>
                            <span style="font-weight: 600; color: #2e6616;">🎉 Happily Accepts</span>
                        </label>
                        <label style="display: flex; align-items: center; gap: 0.5rem; cursor: pointer;">
                            <input type="radio" name="rsvpStatus" value="DECLINED" ${guest.rsvpStatus == 'DECLINED' ? 'checked' : ''}>
                            <span style="font-weight: 600; color: #882218;">Regretfully Declines</span>
                        </label>
                    </div>
                </div>

                <button type="submit" class="btn" style="width: 100%;">Submit RSVP Response</button>
            </form>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

>>>>>>> origin/main
</body>
</html>
