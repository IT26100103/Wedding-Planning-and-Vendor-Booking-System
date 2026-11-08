<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Sign In | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="max-width: 480px; margin: 3rem auto;">
        <div class="frame">
            <h1 style="margin-bottom: 0.5rem;">Welcome Back</h1>
            <p class="lead" style="margin-bottom: 2rem;">Sign in to manage your wedding planning journey.</p>

            <c:if test="${not empty info}">
                <div class="alert info">✓ ${info}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <form class="form" method="post" action="${pageContext.request.contextPath}/login" style="display: block;">
                <div style="margin-bottom: 1.2rem;">
                    <label for="username">Username or Email *</label>
                    <input type="text" id="username" name="username" value="${enteredUsername}" required placeholder="e.g. nimal or nimal@example.com">
                </div>

                <div style="margin-bottom: 1.8rem;">
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" required placeholder="Enter your password">
                </div>

                <button type="submit" class="btn" style="width: 100%; margin-bottom: 1rem;">Sign In</button>
            </form>

            <div style="border-top: 1px solid var(--line); padding-top: 1.2rem; margin-top: 1rem; font-size: 0.9rem;">
                Don't have an account yet? 
                <a href="${pageContext.request.contextPath}/register" style="font-weight: 600;">Create an account</a>
            </div>

            <div style="background: var(--sand); border-radius: var(--radius-sm); padding: 0.8rem; margin-top: 1.5rem; font-size: 0.82rem; color: #666; text-align: left;">
                <strong>Demo Credentials:</strong><br>
                &bull; User: <code>nimal</code> / <code>pass123</code><br>
                &bull; Admin: <code>admin</code> / <code>admin123</code>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
