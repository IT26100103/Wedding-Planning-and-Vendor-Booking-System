<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title><c:out value="${vendor.name}"/> | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="margin-bottom: 1.5rem;">
        <a href="${pageContext.request.contextPath}/vendors" style="font-size: 0.9rem;">&larr; Back to Vendors</a>
    </div>

    <div class="split">
        <section class="frame">
            <div style="display: flex; justify-content: space-between; align-items: flex-start;">
                <h1><c:out value="${vendor.name}"/></h1>
                <span class="badge ${vendor.category == 'CATERING' ? 'ok' : 'vip'}" style="font-size: 0.9rem;">${vendor.category}</span>
            </div>

            <p style="font-size: 1.1rem; color: #555; margin-bottom: 1.2rem;">
                📞 Direct Contact: <strong><c:out value="${vendor.phone}"/></strong>
            </p>

            <c:choose>
                <c:when test="${vendor.category == 'CATERING'}">
                    <h3>Catering Package Rates:</h3>
                    <p style="font-size: 1.25rem; color: var(--forest); font-weight: 700; margin: 0.5rem 0;">
                        LKR ${vendor.basePrice} <span style="font-size: 0.85rem; font-weight: 400; color: #777;">per guest / plate</span>
                    </p>
                    <div class="stats" style="grid-template-columns: repeat(3, 1fr); margin: 1.2rem 0;">
                        <div class="stat">
                            <strong style="font-size: 1.5rem;">LKR ${vendor.calculateCost(100)}</strong>
                            <span>100 Guests</span>
                        </div>
                        <div class="stat">
                            <strong style="font-size: 1.5rem;">LKR ${vendor.calculateCost(200)}</strong>
                            <span>200 Guests</span>
                        </div>
                        <div class="stat">
                            <strong style="font-size: 1.5rem;">LKR ${vendor.calculateCost(350)}</strong>
                            <span>350 Guests</span>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <h3>Photography &amp; Cinematic Coverage:</h3>
                    <p style="font-size: 1.4rem; color: var(--forest); font-weight: 700; margin: 0.8rem 0;">
                        LKR ${vendor.basePrice} <span style="font-size: 0.85rem; font-weight: 400; color: #777;">complete wedding coverage package</span>
                    </p>
                    <ul style="color: #555; font-size: 0.95rem; line-height: 1.8;">
                        <li>Full day continuous photo &amp; video shooting</li>
                        <li>High-definition drone footage &amp; teaser reel</li>
                        <li>Master edited photo book album &amp; digital cloud vault</li>
                    </ul>
                </c:otherwise>
            </c:choose>

            <div style="margin-top: 2rem; display: flex; gap: 0.8rem;">
                <a href="${pageContext.request.contextPath}/bookings?action=new&resourceId=${vendor.id}" class="btn">Book This Vendor</a>
                <a href="${pageContext.request.contextPath}/reviews?action=new&targetId=${vendor.id}" class="btn alt">Write a Review</a>
            </div>
        </section>

        <div>
            <div class="frame">
                <h3>Client Reviews</h3>
                <div style="display: flex; align-items: baseline; gap: 0.8rem; margin: 1rem 0;">
                    <span style="font-family: 'Playfair Display', serif; font-size: 3rem; color: var(--forest); font-weight: 700;">
                        ${avgRating > 0 ? String.format("%.1f", avgRating) : "N/A"}
                    </span>
                    <span class="rating-stars" style="font-size: 1.5rem;">
                        <c:choose>
                            <c:when test="${avgRating >= 4.5}">★★★★★</c:when>
                            <c:when test="${avgRating >= 3.5}">★★★★☆</c:when>
                            <c:when test="${avgRating >= 2.5}">★★★☆☆</c:when>
                            <c:when test="${avgRating >= 1.5}">★★☆☆☆</c:when>
                            <c:otherwise>★☆☆☆☆</c:otherwise>
                        </c:choose>
                    </span>
                    <span style="color: #777; font-size: 0.88rem;">(${reviews.size()} reviews)</span>
                </div>

                <div style="max-height: 400px; overflow-y: auto;">
                    <c:forEach var="r" items="${reviews}">
                        <div class="review-item">
                            <div class="review-header">
                                <span class="rating-stars">
                                    <c:forEach begin="1" end="${r.rating}">★</c:forEach>
                                </span>
                                <span class="badge ${r.reviewType == 'VERIFIED' ? 'ok' : ''}">
                                    ${r.reviewType == 'VERIFIED' ? '✓ Verified Booking' : 'Public Review'}
                                </span>
                            </div>
                            <p style="font-size: 0.95rem; margin: 0.3rem 0;"><c:out value="${r.comment}"/></p>
                            <span style="font-size: 0.78rem; color: #888;">By User: ${r.userId}</span>
                        </div>
                    </c:forEach>

                    <c:if test="${empty reviews}">
                        <p style="color: #777; font-size: 0.9rem;">No reviews yet for this vendor. Be the first to share your experience!</p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
