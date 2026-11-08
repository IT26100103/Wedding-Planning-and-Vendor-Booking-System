<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Submit a Review | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="max-width: 650px; margin: 2rem auto;">
        <div class="frame">
            <h1>Leave a Review</h1>
            <p class="lead" style="margin-bottom: 2rem;">
                Share your candid feedback. Verified bookings automatically receive a trusted verified badge.
            </p>

            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <form class="form" method="post" action="${pageContext.request.contextPath}/reviews">
                <input type="hidden" name="action" value="save">

                <div class="full">
                    <label for="targetId">Select Venue or Vendor to Review *</label>
                    <select id="targetId" name="targetId" required>
                        <optgroup label="🏰 Venues">
                            <c:forEach var="vn" items="${venues}">
                                <option value="${vn.id}" ${selectedTargetId == vn.id ? 'selected' : ''}>
                                    ${vn.name} (${vn.location})
                                </option>
                            </c:forEach>
                        </optgroup>
                        <optgroup label="📸 &amp; 🍽 Vendors">
                            <c:forEach var="vd" items="${vendors}">
                                <option value="${vd.id}" ${selectedTargetId == vd.id ? 'selected' : ''}>
                                    ${vd.name} (${vd.category})
                                </option>
                            </c:forEach>
                        </optgroup>
                    </select>
                </div>

                <div class="full">
                    <label for="rating">Rating (1 to 5 Stars) *</label>
                    <select id="rating" name="rating">
                        <option value="5" selected>★★★★★ - 5 Stars (Exceptional)</option>
                        <option value="4">★★★★☆ - 4 Stars (Very Good)</option>
                        <option value="3">★★★☆☆ - 3 Stars (Average)</option>
                        <option value="2">★★☆☆☆ - 2 Stars (Below Expectations)</option>
                        <option value="1">★☆☆☆☆ - 1 Star (Unsatisfactory)</option>
                    </select>
                </div>

                <div class="full">
                    <label for="comment">Your Review Comments *</label>
                    <textarea id="comment" name="comment" rows="4" required placeholder="Describe your experience with the service, timing, staff professionalism, and presentation..."></textarea>
                </div>

                <div class="full" style="display: flex; gap: 0.8rem; margin-top: 1rem;">
                    <button type="submit" class="btn" style="flex: 1;">Publish Review</button>
                    <a href="${pageContext.request.contextPath}/reviews" class="btn outline">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
