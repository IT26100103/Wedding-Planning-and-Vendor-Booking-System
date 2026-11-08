<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Review Moderation | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 2rem; flex-wrap: wrap; gap: 1rem;">
        <div>
            <h1>Review Moderation Console</h1>
            <p class="lead">Audit client reviews and enforce community standards.</p>
        </div>
        <a href="${pageContext.request.contextPath}/reviews" class="btn sm outline">&larr; Public Reviews</a>
    </div>

    <div class="tbl">
        <table>
            <thead>
                <tr>
                    <th>Review ID</th>
                    <th>Target Resource</th>
                    <th>User</th>
                    <th>Type</th>
                    <th>Rating</th>
                    <th>Comment</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${reviews}">
                    <tr>
                        <td><strong>${r.id}</strong></td>
                        <td><strong><c:out value="${targetNames[r.targetId]}"/></strong> (${r.targetId})</td>
                        <td>${r.userId}</td>
                        <td>
                            <span class="badge ${r.reviewType == 'VERIFIED' ? 'ok' : ''}">${r.reviewType}</span>
                        </td>
                        <td>
                            <span class="rating-stars">
                                <c:forEach begin="1" end="${r.rating}">★</c:forEach>
                            </span>
                        </td>
                        <td style="max-width: 320px;"><c:out value="${r.comment}"/></td>
                        <td>
                            <span class="badge ${r.status == 'PUBLISHED' ? 'ok' : 'bad'}">${r.status}</span>
                        </td>
                        <td>
                            <div style="display: flex; gap: 0.3rem;">
                                <c:choose>
                                    <c:when test="${r.status == 'PUBLISHED'}">
                                        <form method="post" action="${pageContext.request.contextPath}/reviews" style="display:inline;">
                                            <input type="hidden" name="action" value="moderate">
                                            <input type="hidden" name="id" value="${r.id}">
                                            <input type="hidden" name="status" value="HIDDEN">
                                            <button type="submit" class="btn sm alt">Hide</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form method="post" action="${pageContext.request.contextPath}/reviews" style="display:inline;">
                                            <input type="hidden" name="action" value="moderate">
                                            <input type="hidden" name="id" value="${r.id}">
                                            <input type="hidden" name="status" value="PUBLISHED">
                                            <button type="submit" class="btn sm">Publish</button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>

                                <form method="post" action="${pageContext.request.contextPath}/reviews" style="display:inline;" onsubmit="return confirm('Permanently remove this review?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${r.id}">
                                    <button type="submit" class="btn sm danger">Delete</button>
                                </form>
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
