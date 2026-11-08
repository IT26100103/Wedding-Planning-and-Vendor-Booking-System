<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="u" value="${not empty editUser ? editUser : profileUser}" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Edit Profile | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="max-width: 620px; margin: 2rem auto;">
        <div class="frame">
            <h1 style="margin-bottom: 0.5rem;">User Account Profile</h1>
            <p class="lead" style="margin-bottom: 2rem;">
                <c:choose>
                    <c:when test="${not empty editUser}">
                        Administrative editing for user account <strong>${editUser.id}</strong>.
                    </c:when>
                    <c:otherwise>
                        Manage your wedding details, credentials, and contact information.
                    </c:otherwise>
                </c:choose>
            </p>

            <c:if test="${not empty info}">
                <div class="alert info">✓ ${info}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <form class="form" method="post" action="${pageContext.request.contextPath}/profile">
                <input type="hidden" name="userId" value="${u.id}">

                <div>
                    <label>Account ID</label>
                    <input type="text" value="${u.id}" readonly style="background: var(--sand);">
                </div>

                <div>
                    <label>Account Role</label>
                    <input type="text" value="${u.role}" readonly style="background: var(--sand);">
                </div>

                <div class="full">
                    <label for="fullName">Full / Couple Name *</label>
                    <input type="text" id="fullName" name="fullName" value="${u.name}" required>
                </div>

                <div>
                    <label for="email">Email Address *</label>
                    <input type="email" id="email" name="email" value="${u.email}" required>
                </div>

                <div>
                    <label>Username</label>
                    <input type="text" value="${u.username}" readonly style="background: var(--sand);">
                </div>

                <div class="full">
                    <label for="password">Password (leave unchanged to keep current)</label>
                    <input type="password" id="password" name="password" value="${u.password}" required>
                </div>

                <c:choose>
                    <c:when test="${u.role == 'ADMIN'}">
                        <div class="full">
                            <label for="permissionLevel">Administrative Permission Level</label>
                            <input type="text" id="permissionLevel" name="permissionLevel" value="${u.permissionLevel}" placeholder="e.g. FULL, MODERATOR">
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="full">
                            <label for="weddingDate">Planned Wedding Date</label>
                            <input type="date" id="weddingDate" name="weddingDate" value="${u.weddingDate}">
                        </div>
                    </c:otherwise>
                </c:choose>

                <div class="full" style="display: flex; gap: 0.8rem; margin-top: 1.2rem;">
                    <button type="submit" class="btn" style="flex: 1;">Save Changes</button>
                    <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/users" class="btn outline">Back to Users</a>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
