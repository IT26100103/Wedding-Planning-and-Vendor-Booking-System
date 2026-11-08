<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Create an Account | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="max-width: 580px; margin: 2rem auto;">
        <div class="frame">
            <h1 style="margin-bottom: 0.5rem;">Join Vows &amp; Vendors</h1>
            <p class="lead" style="margin-bottom: 2rem;">Create your couple account to begin planning your wedding.</p>

            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <form class="form" method="post" action="${pageContext.request.contextPath}/register">
                <div class="full">
                    <label for="fullName">Couple / Full Name *</label>
                    <input type="text" id="fullName" name="fullName" required placeholder="e.g. Kasun &amp; Nimali">
                </div>

                <div>
                    <label for="email">Email Address *</label>
                    <input type="email" id="email" name="email" required placeholder="couple@example.com">
                </div>

                <div>
                    <label for="username">Choose Username *</label>
                    <input type="text" id="username" name="username" required placeholder="e.g. kasun_nimali">
                </div>

                <div>
                    <label for="password">Password *</label>
                    <input type="password" id="password" name="password" required placeholder="Create password">
                </div>

                <div>
                    <label for="confirmPassword">Confirm Password *</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="Re-enter password">
                </div>

                <div class="full">
                    <label for="weddingDate">Planned Wedding Date</label>
                    <input type="date" id="weddingDate" name="weddingDate">
                </div>

                <div class="full" style="margin-top: 1rem;">
                    <button type="submit" class="btn" style="width: 100%;">Create Account</button>
                </div>
            </form>

            <div style="border-top: 1px solid var(--line); padding-top: 1.2rem; margin-top: 1.5rem; font-size: 0.9rem;">
                Already have an account? 
                <a href="${pageContext.request.contextPath}/login" style="font-weight: 600;">Sign in here</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

</body>
</html>
