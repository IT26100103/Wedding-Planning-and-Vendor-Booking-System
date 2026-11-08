<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>My Wedding Dashboard | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
        <div>
            <h1>My Wedding Celebration</h1>
            <p class="lead">Welcome, <strong><c:out value="${sessionScope.currentUser.name}"/></strong>! Here is an overview of your wedding coordination.</p>
        </div>
        <div style="display: flex; gap: 0.6rem;">
            <a href="${pageContext.request.contextPath}/profile" class="btn sm outline">Edit Wedding Date</a>
            <a href="${pageContext.request.contextPath}/guests?action=new" class="btn sm">+ Invite Guests</a>
        </div>
    </div>

    <!-- Metrics Cards -->
    <div class="grid" style="grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); margin-bottom: 2.5rem;">
        <div class="card" style="text-align: center;">
            <div style="font-size: 2.2rem; font-weight: 700; color: var(--forest);">${guests.size()}</div>
            <div style="color: #666; font-size: 0.9rem;">Total Invited Guests</div>
        </div>

        <div class="card" style="text-align: center;">
            <div style="font-size: 2.2rem; font-weight: 700; color: #2e6616;">
                <c:set var="acceptedCount" value="0" />
                <c:forEach var="g" items="${guests}">
                    <c:if test="${g.rsvpStatus == 'ACCEPTED'}">
                        <c:set var="acceptedCount" value="${acceptedCount + 1}" />
                    </c:if>
                </c:forEach>
                ${acceptedCount}
            </div>
            <div style="color: #666; font-size: 0.9rem;">RSVPs Confirmed</div>
        </div>

        <div class="card" style="text-align: center;">
            <div style="font-size: 2.2rem; font-weight: 700; color: #a63d2f;">
                <c:set var="pendingCount" value="0" />
                <c:forEach var="g" items="${guests}">
                    <c:if test="${g.rsvpStatus == 'PENDING'}">
                        <c:set var="pendingCount" value="${pendingCount + 1}" />
                    </c:if>
                </c:forEach>
                ${pendingCount}
            </div>
            <div style="color: #666; font-size: 0.9rem;">Pending RSVPs</div>
        </div>

        <div class="card" style="text-align: center;">
            <div style="font-size: 2.2rem; font-weight: 700; color: var(--gold);">${vendors.size()}</div>
            <div style="color: #666; font-size: 0.9rem;">Partner Vendors Available</div>
        </div>
    </div>

    <div class="split" style="gap: 2rem;">
        <section class="frame left">
            <h2>Wedding Milestones</h2>
            <p><strong>Planned Ceremony Date:</strong> 
                <c:choose>
                    <c:when test="${not empty sessionScope.currentUser.weddingDate}">
                        <span class="badge ok">${sessionScope.currentUser.weddingDate}</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge warn">Not set yet</span> &mdash; <a href="${pageContext.request.contextPath}/profile">Set your date</a>
                    </c:otherwise>
                </c:choose>
            </p>
            <p><strong>Primary Contact:</strong> <c:out value="${sessionScope.currentUser.email}"/></p>

            <h3 style="margin-top: 1.5rem;">Quick Navigation:</h3>
            <ul style="line-height: 2; padding-left: 1.2rem;">
                <li><a href="${pageContext.request.contextPath}/guests">Manage Guest List &amp; VIP Seating</a></li>
                <li><a href="${pageContext.request.contextPath}/vendors">Browse Catering &amp; Photography Vendors</a></li>
                <li><a href="${pageContext.request.contextPath}/venues">Explore Grand Ballrooms &amp; Lawns</a></li>
                <li><a href="${pageContext.request.contextPath}/reviews">Read Authentic Client Reviews</a></li>
            </ul>
        </section>

        <section class="frame left">
            <h2>Recent Community Reviews</h2>
            <c:forEach var="r" items="${reviews}" begin="0" end="2">
                <div style="border-bottom: 1px solid var(--line); padding-bottom: 0.8rem; margin-bottom: 0.8rem;">
                    <div style="display: flex; justify-content: space-between; font-size: 0.85rem;">
                        <strong>Target: ${r.targetId}</strong>
                        <span class="rating-stars">
                            <c:forEach begin="1" end="${r.rating}">★</c:forEach>
                        </span>
                    </div>
                    <p style="font-size: 0.9rem; margin: 0.3rem 0; color: #555;">&ldquo;<c:out value="${r.comment}"/>&rdquo;</p>
                </div>
            </c:forEach>
            <a href="${pageContext.request.contextPath}/reviews" class="btn sm outline">View All Reviews &rarr;</a>
        </section>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
