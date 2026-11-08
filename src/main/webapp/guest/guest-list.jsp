<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Guest List &amp; Seating | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
        <div>
            <h1>Guest List &amp; Invitations</h1>
            <p class="lead">Manage invitations, table assignments, and track real-time RSVP responses.</p>
        </div>
        <div style="display: flex; gap: 0.6rem;">
            <a class="btn sm" href="${pageContext.request.contextPath}/guests?action=new">+ Add New Guest</a>
        </div>
    </div>

    <!-- Search Toolbar -->
    <form class="toolbar" method="get" action="${pageContext.request.contextPath}/guests">
        <input type="text" name="keyword" placeholder="Search by guest name..." value="${param.keyword}">
        <button type="submit" class="btn sm">Search Guests</button>
        <a href="${pageContext.request.contextPath}/guests" class="btn sm outline">Show All</a>
    </form>

    <div class="tbl">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Full Name</th>
                    <th>Guest Type</th>
                    <th>Email Address</th>
                    <th>Phone</th>
                    <th>RSVP Status</th>
                    <th>Seating Assignment</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="g" items="${guests}">
                    <tr>
                        <td><strong>${g.guestId}</strong></td>
                        <td>
                            <strong><c:out value="${g.fullName}"/></strong>
                        </td>
                        <td>
                            <span class="badge ${g.guestType == 'VIP' ? 'vip' : ''}">
                                ${g.guestType}
                            </span>
                        </td>
                        <td><c:out value="${g.email}"/></td>
                        <td><code>${g.phone}</code></td>
                        <td>
                            <c:choose>
                                <c:when test="${g.rsvpStatus == 'ACCEPTED'}">
                                    <span class="badge ok">✓ Accepted</span>
                                </c:when>
                                <c:when test="${g.rsvpStatus == 'DECLINED'}">
                                    <span class="badge bad">✗ Declined</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge warn">⌛ Pending</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:out value="${g.seatingInfo}"/>
                        </td>
                        <td>
                            <div style="display: flex; gap: 0.3rem;">
                                <a class="btn sm outline" href="${pageContext.request.contextPath}/rsvp?id=${g.guestId}" target="_blank" title="Open RSVP portal">RSVP</a>
                                <a class="btn sm alt" href="${pageContext.request.contextPath}/guests?action=edit&id=${g.guestId}">Edit</a>
                                <form method="post" action="${pageContext.request.contextPath}/guests" style="display:inline" onsubmit="return confirm('Delete this guest from the invitation list?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${g.guestId}">
                                    <button type="submit" class="btn sm danger">Delete</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty guests}">
                    <tr>
                        <td colspan="8" style="text-align: center; color: #888; padding: 2.5rem;">
                            No guests found. Start adding your wedding invitations!
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
