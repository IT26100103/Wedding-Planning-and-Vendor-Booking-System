<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Reviews &amp; Client Testimonials | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
        <div>
            <h1>Client Reviews &amp; Experiences</h1>
            <p class="lead">Authentic feedback from couples across Sri Lanka.</p>
        </div>
        <div style="display: flex; gap: 0.5rem;">
            <a href="${pageContext.request.contextPath}/reviews?action=new" class="btn sm">+ Write a Review</a>
            <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/reviews?action=admin" class="btn sm outline">🛡 Admin Moderation</a>
            </c:if>
        </div>
    </div>

    <!-- Filter by Service -->
    <form class="toolbar" method="get" action="${pageContext.request.contextPath}/reviews">
        <select name="targetId" onchange="this.form.submit()">
            <option value="">All Venues &amp; Vendors</option>
            <optgroup label="🏰 Venues">
                <c:forEach var="vn" items="${venues}">
                    <option value="${vn.id}" ${selectedTargetId == vn.id ? 'selected' : ''}>${vn.name} (${vn.location})</option>
                </c:forEach>
            </optgroup>
            <optgroup label="📸 &amp; 🍽 Vendors">
                <c:forEach var="vd" items="${vendors}">
                    <option value="${vd.id}" ${selectedTargetId == vd.id ? 'selected' : ''}>${vd.name} (${vd.category})</option>
                </c:forEach>
            </optgroup>
        </select>
        <button type="submit" class="btn sm">Filter Reviews</button>
        <a href="${pageContext.request.contextPath}/reviews" class="btn sm outline">Show All</a>
    </form>

    <c:if test="${not empty selectedTargetId and avgRating > 0}">
        <div class="frame" style="margin-bottom: 2rem; display: flex; align-items: center; justify-content: space-between; flex-wrap: wrap; gap: 1rem;">
            <div>
                <span class="badge ok">Weighted Average Rating</span>
                <h2 style="margin: 0.3rem 0;">${targetNames[selectedTargetId]}</h2>
                <p style="margin: 0; color: #666; font-size: 0.9rem;">
                    Verified reviews carry double weight in calculating the score.
                </p>
            </div>
            <div style="text-align: right;">
                <div style="font-size: 2.8rem; font-family: 'Playfair Display', serif; font-weight: 700; color: var(--forest);">
                    ${String.format("%.1f", avgRating)} / 5.0
                </div>
                <div class="rating-stars" style="font-size: 1.3rem;">
                    <c:choose>
                        <c:when test="${avgRating >= 4.5}">★★★★★</c:when>
                        <c:when test="${avgRating >= 3.5}">★★★★☆</c:when>
                        <c:when test="${avgRating >= 2.5}">★★★☆☆</c:when>
                        <c:when test="${avgRating >= 1.5}">★★☆☆☆</c:when>
                        <c:otherwise>★☆☆☆☆</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:if>

    <div class="grid" style="grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));">
        <c:forEach var="r" items="${reviews}">
            <div class="card">
                <div>
                    <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.5rem;">
                        <h3 style="margin-bottom: 0;"><c:out value="${targetNames[r.targetId]}"/></h3>
                        <span class="badge ${r.reviewType == 'VERIFIED' ? 'ok' : ''}">
                            ${r.reviewType == 'VERIFIED' ? '✓ Verified Booking' : 'Public Review'}
                        </span>
                    </div>

                    <div class="rating-stars" style="margin-bottom: 0.8rem;">
                        <c:forEach begin="1" end="${r.rating}">★</c:forEach>
                    </div>

                    <p style="font-size: 0.95rem; line-height: 1.6; color: #444;">
                        &ldquo;<c:out value="${r.comment}"/>&rdquo;
                    </p>
                </div>

                <div class="card-footer" style="font-size: 0.82rem; color: #777;">
                    <span>Reviewed by User: <strong>${r.userId}</strong></span>
                    <c:if test="${r.reviewType == 'VERIFIED'}">
                        <span style="color: #2b6118; font-weight: 600;">Booking #${r.bookingId}</span>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty reviews}">
        <div class="frame" style="text-align: center; margin-top: 2rem;">
            <h3>No reviews available</h3>
            <p>Be the first couple to leave a review for our wedding services!</p>
            <a href="${pageContext.request.contextPath}/reviews?action=new" class="btn">Write Review</a>
        </div>
    </c:if>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
