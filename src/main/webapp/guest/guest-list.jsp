<%--
============================================================================
VIEW COMPONENT: guest-list.jsp
============================================================================
ARCHITECTURAL ROLE: MVC View (JSP Presentation Layer)
- Rendered by: GuestServlet (GET /guests)
- Model attributes consumed:
    * ${guests} : List of polymorphic Guest objects (RegularGuest and VipGuest)
    * ${param.keyword} : Optional search filter query
- Features:
    * Guest table with responsive badge styling (Accepted, Declined, Pending)
    * Polymorphic seating display via ${g.seatingInfo} (Table # for VIP, Open for Regular)
    * Quick search toolbar and CRUD action triggers (+ Add Guest, Edit, Delete)
============================================================================
--%>
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
                        <td>${g.fullName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${g.guestType == 'VIP'}">
                                    <span class="badge" style="background:#fef3c7; color:#92400e; border:1px solid #fde68a;">VIP Guest</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge" style="background:#f1f5f9; color:#475569;">Regular</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${g.email}</td>
                        <td>${g.phone}</td>
                        <td>
                            <c:choose>
                                <c:when test="${g.rsvpStatus == 'ACCEPTED'}">
                                    <span class="badge success">Accepted</span>
                                </c:when>
                                <c:when test="${g.rsvpStatus == 'DECLINED'}">
                                    <span class="badge error">Declined</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge warn">Pending</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <em>${g.seatingInfo}</em>
                        </td>
                        <td>
                            <div style="display:flex; gap:0.4rem; align-items:center;">
                                <a href="${pageContext.request.contextPath}/guests?action=edit&amp;id=${g.guestId}" class="btn sm outline" style="padding:0.25rem 0.6rem;">Edit</a>
                                <form method="post" action="${pageContext.request.contextPath}/guests" style="display:inline; margin:0;" onsubmit="return confirm('Are you sure you want to remove this guest?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${g.guestId}">
                                    <button type="submit" class="btn sm outline" style="padding:0.25rem 0.6rem; color:#dc2626; border-color:#fca5a5;">Delete</button>
                                </form>
                                <a href="${pageContext.request.contextPath}/rsvp?id=${g.guestId}" target="_blank" class="btn sm outline" style="padding:0.25rem 0.6rem; font-size:0.75rem;">RSVP Link</a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty guests}">
                    <tr>
                        <td colspan="8" style="text-align:center; padding:2rem; color:var(--muted);">
                            No guests found. Click <strong>+ Add New Guest</strong> to create your first invitation.
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
