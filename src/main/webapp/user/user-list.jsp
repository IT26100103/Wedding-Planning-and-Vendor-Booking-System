<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>User Accounts Management | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
        <div>
            <h1>Registered User Accounts</h1>
            <p class="lead">System administrator console for reviewing client couples and administrators.</p>
        </div>
        <a href="${pageContext.request.contextPath}/register" class="btn sm">+ Register New User</a>
    </div>

    <c:if test="${not empty info}">
        <div class="alert info">✓ ${info}</div>
    </c:if>

    <div class="tbl">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Full / Couple Name</th>
                    <th>Email Address</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Role Details</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td><strong>${u.id}</strong></td>
                        <td><strong><c:out value="${u.name}"/></strong></td>
                        <td><c:out value="${u.email}"/></td>
                        <td><code>${u.username}</code></td>
                        <td>
                            <span class="badge ${u.role == 'ADMIN' ? 'ok' : ''}">
                                ${u.role}
                            </span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${u.role == 'ADMIN'}">
                                    <span style="font-size: 0.85rem; color: var(--forest); font-weight: 600;">
                                        Perm: ${u.permissionLevel}
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span style="font-size: 0.85rem; color: #666;">
                                        Date: ${not empty u.weddingDate ? u.weddingDate : 'Not set'}
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <div style="display: flex; gap: 0.3rem;">
                                <a class="btn sm alt" href="${pageContext.request.contextPath}/users?action=edit&id=${u.id}">Edit</a>
                                <c:if test="${sessionScope.currentUser.id != u.id}">
                                    <form method="post" action="${pageContext.request.contextPath}/users" style="display:inline;" onsubmit="return confirm('Permanently delete account ${u.username}?');">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="${u.id}">
                                        <button type="submit" class="btn sm danger">Delete</button>
                                    </form>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
