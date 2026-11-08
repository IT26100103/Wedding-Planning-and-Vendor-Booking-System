<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Wedding Venues | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
        <div>
            <h1>Luxury Wedding Venues</h1>
            <p class="lead">Grand ballrooms, beachfront lawns, and heritage estates across Sri Lanka.</p>
        </div>
        <div style="display: flex; gap: 0.5rem;">
            <a href="${pageContext.request.contextPath}/wedding" class="btn sm outline">✦ My Wedding</a>
        </div>
    </div>

    <!-- Search & Filter Toolbar -->
    <form class="toolbar" method="get" action="${pageContext.request.contextPath}/venues">
        <input type="text" name="keyword" placeholder="Search by venue name, location, or type..." value="${param.keyword}">
        <input type="number" name="guests" placeholder="Min guest capacity (e.g. 200)" value="${param.guests}" min="1" style="max-width: 220px;">
        <button type="submit" class="btn sm">Find Venues</button>
        <a href="${pageContext.request.contextPath}/venues" class="btn sm outline">Show All</a>
    </form>

    <div class="grid" style="grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));">
        <c:forEach var="v" items="${venues}">
            <div class="card">
                <div class="photo"></div>
                <div>
                    <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.5rem;">
                        <h3 style="margin-bottom: 0.2rem;"><c:out value="${v.name}"/></h3>
                        <span class="badge ${v.type == 'INDOOR' ? 'ok' : 'vip'}">${v.type}</span>
                    </div>

                    <p style="color: #635f56; font-size: 0.9rem; margin-bottom: 0.8rem;">
                        📍 Location: <strong><c:out value="${v.location}"/></strong><br>
                        👥 Guest Capacity: <strong>${v.capacity} guests</strong>
                    </p>

                    <p style="font-size: 1.15rem; color: var(--forest); font-weight: 700; margin: 0.5rem 0;">
                        LKR ${v.pricePerHour} <span style="font-size: 0.82rem; font-weight: 400; color: #777;">/ hour</span>
                    </p>

                    <p style="font-size: 0.84rem; color: #888;">
                        Estimated 6-hour reception: <strong>LKR ${v.calculateCost(6)}</strong>
                    </p>
                </div>

                <div class="card-footer" style="margin-top: 1rem; border-top: 1px solid var(--line); padding-top: 0.8rem; display: flex; justify-content: space-between; align-items: center;">
                    <span style="font-size: 0.82rem; color: #777;">ID: <code>${v.id}</code></span>
                    <a href="${pageContext.request.contextPath}/reviews?targetId=${v.id}" class="btn sm alt">Reviews &rarr;</a>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty venues}">
        <div class="frame" style="text-align: center; margin-top: 2rem;">
            <h3>No matching venues found</h3>
            <p>Try broadening your search or resetting filters.</p>
            <a href="${pageContext.request.contextPath}/venues" class="btn">View All Venues</a>
        </div>
    </c:if>

    <div style="background: var(--sand); border: 1px dashed var(--line); border-radius: var(--radius-sm); padding: 1.2rem; margin-top: 3rem; text-align: center; font-size: 0.88rem; color: #666;">
        ℹ️ <em>Venue booking &amp; reservation management module will be expanded with the upcoming team release.</em>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
