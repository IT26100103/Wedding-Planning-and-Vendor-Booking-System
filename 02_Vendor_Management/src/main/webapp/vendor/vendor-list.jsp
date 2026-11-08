<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Wedding Vendors &amp; Services | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
        <div>
            <h1>Curated Wedding Vendors</h1>
            <p class="lead">Gourmet catering masters, visionary photographers, and stylists.</p>
        </div>
        <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/vendors?action=new" class="btn sm">+ Register Vendor</a>
        </c:if>
    </div>

    <!-- Search Toolbar -->
    <form class="toolbar" method="get" action="${pageContext.request.contextPath}/vendors">
        <input type="text" name="keyword" placeholder="Search by vendor name or category..." value="${param.keyword}">

        <select name="category">
            <option value="ALL">All Categories</option>
            <option value="CATERING" ${param.category == 'CATERING' ? 'selected' : ''}>Catering &amp; Menus</option>
            <option value="PHOTOGRAPHY" ${param.category == 'PHOTOGRAPHY' ? 'selected' : ''}>Photography &amp; Cinema</option>
        </select>

        <button type="submit" class="btn sm">Filter Vendors</button>
        <a href="${pageContext.request.contextPath}/vendors" class="btn sm outline">Reset</a>
    </form>

    <div class="grid">
        <c:forEach var="v" items="${vendors}">
            <div class="card">
                <div class="photo"></div>
                <div>
                    <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 0.5rem;">
                        <h3 style="margin-bottom: 0.2rem;"><c:out value="${v.name}"/></h3>
                        <span class="badge ${v.category == 'CATERING' ? 'ok' : 'vip'}">${v.category}</span>
                    </div>

                    <p style="color: #635f56; font-size: 0.9rem; margin-bottom: 0.8rem;">
                        📞 Contact: <strong><c:out value="${v.phone}"/></strong>
                    </p>

                    <c:choose>
                        <c:when test="${v.category == 'CATERING'}">
                            <p style="font-size: 1.15rem; color: var(--forest); font-weight: 700; margin: 0.5rem 0;">
                                LKR ${v.basePrice} <span style="font-size: 0.82rem; font-weight: 400; color: #777;">/ plate</span>
                            </p>
                            <p style="font-size: 0.84rem; color: #888;">
                                150 guests banquet: <strong>LKR ${v.calculateCost(150)}</strong>
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p style="font-size: 1.15rem; color: var(--forest); font-weight: 700; margin: 0.5rem 0;">
                                LKR ${v.basePrice} <span style="font-size: 0.82rem; font-weight: 400; color: #777;">package</span>
                            </p>
                            <p style="font-size: 0.84rem; color: #888;">
                                Complete wedding day photography &amp; album
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="card-footer">
                    <div style="display: flex; gap: 0.4rem;">
                        <a href="${pageContext.request.contextPath}/vendors?action=view&id=${v.id}" class="btn sm outline">View &amp; Reviews</a>
                        <a href="${pageContext.request.contextPath}/bookings?action=new&resourceId=${v.id}" class="btn sm">Book Vendor</a>
                    </div>
                    <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                        <div style="display: flex; gap: 0.3rem;">
                            <a href="${pageContext.request.contextPath}/vendors?action=edit&id=${v.id}" class="btn sm alt">Edit</a>
                            <form method="post" action="${pageContext.request.contextPath}/vendors" style="display:inline;" onsubmit="return confirm('Delete this vendor?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${v.id}">
                                <button type="submit" class="btn sm danger">X</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty vendors}">
        <div class="frame" style="text-align: center; margin-top: 2rem;">
            <h3>No vendors found</h3>
            <p>Try clearing your search query or selecting a different category filter.</p>
            <a href="${pageContext.request.contextPath}/vendors" class="btn sm">Show All Vendors</a>
        </div>
    </c:if>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
