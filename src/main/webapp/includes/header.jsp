<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<<<<<<< HEAD
<header class="topbar">
    <a class="logo" href="${pageContext.request.contextPath}/">Vows &amp; Vendors</a>
    <nav class="nav">
        <a href="${pageContext.request.contextPath}/">Home</a>
        <a href="${pageContext.request.contextPath}/users">Users</a>
        <a href="${pageContext.request.contextPath}/vendors">Vendors</a>
        <a href="${pageContext.request.contextPath}/venues">Venues</a>
        <a href="${pageContext.request.contextPath}/bookings">Bookings</a>
        <a href="${pageContext.request.contextPath}/guests">Guests</a>
        <a href="${pageContext.request.contextPath}/reviews">Reviews</a>
    </nav>
</header>
<a class="book-tab" href="${pageContext.request.contextPath}/bookings?action=new">Book your wedding</a>
=======
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="topbar">
    <div class="topbar-brand">
        <a class="logo" href="${pageContext.request.contextPath}/">
            <span class="logo-mark">❦</span> Vows &amp; Vendors
        </a>
        <span class="tagline">Wedding &amp; Event Concierge</span>
    </div>

    <nav class="nav">
        <a href="${pageContext.request.contextPath}/" class="nav-item">Home</a>
        <a href="${pageContext.request.contextPath}/wedding" class="nav-item">My Wedding</a>
        <a href="${pageContext.request.contextPath}/venues" class="nav-item">Venues</a>
        <a href="${pageContext.request.contextPath}/vendors" class="nav-item">Vendors</a>
        <a href="${pageContext.request.contextPath}/bookings" class="nav-item">Bookings</a>
        <a href="${pageContext.request.contextPath}/guests" class="nav-item">Guests</a>
        <a href="${pageContext.request.contextPath}/reviews" class="nav-item">Reviews</a>
        <a href="${pageContext.request.contextPath}/budget" class="nav-item">Budget</a>
    </nav>

    <div class="topbar-user">
        <c:choose>
            <c:when test="${not empty sessionScope.currentUser}">
                <div class="user-pill">
                    <span class="user-avatar">${sessionScope.currentUser.name.substring(0, 1)}</span>
                    <span class="user-name"><c:out value="${sessionScope.currentUser.name}"/></span>
                    <c:if test="${sessionScope.currentUser.role == 'ADMIN'}">
                        <span class="badge ok admin-badge">ADMIN</span>
                        <a href="${pageContext.request.contextPath}/users" class="nav-sub-link">Users</a>
                        <a href="${pageContext.request.contextPath}/reviews?action=admin" class="nav-sub-link">Mod</a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/profile" class="nav-sub-link">Profile</a>
                    <a href="${pageContext.request.contextPath}/logout" class="btn sm danger logout-btn">Sign out</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="auth-buttons">
                    <a href="${pageContext.request.contextPath}/login" class="btn sm alt">Sign in</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn sm">Register</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>
<a class="book-tab" href="${pageContext.request.contextPath}/bookings?action=new">✦ Book Wedding</a>
>>>>>>> origin/main
