<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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