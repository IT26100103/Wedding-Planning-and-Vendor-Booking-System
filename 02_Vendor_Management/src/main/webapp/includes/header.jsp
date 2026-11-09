<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    // DEMO ONLY (standalone Vendor module): auto admin session so Add/Edit/Delete buttons show.
    // Remove this block after merging with the real login module.
    if (session.getAttribute("currentUser") == null) {
        java.util.Map<String, String> demo = new java.util.HashMap<>();
        demo.put("name", "Demo Admin");
        demo.put("role", "ADMIN");
        session.setAttribute("currentUser", demo);
    }
%>
<header class="topbar">
    <div class="topbar-brand">
        <a class="logo" href="${pageContext.request.contextPath}/">
            <span class="logo-mark">❦</span> Vows &amp; Vendors
        </a>
        <span class="tagline">Wedding &amp; Event Concierge</span>
    </div>

    <nav class="nav">
        <a href="${pageContext.request.contextPath}/" class="nav-item">Home</a>
        <a href="${pageContext.request.contextPath}/vendors" class="nav-item">Vendors</a>
    </nav>

    <div class="topbar-user">
        <div class="user-pill">
            <span class="user-avatar">${sessionScope.currentUser.name.substring(0, 1)}</span>
            <span class="user-name"></span>
            <span class="badge ok admin-badge">ADMIN</span>
        </div>
    </div>
</header>