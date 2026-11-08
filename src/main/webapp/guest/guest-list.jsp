<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Guest List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <h2 class="mb-3">Guest List</h2>

    <form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/guests">
        <div class="col-auto">
            <input type="text" name="keyword" class="form-control" placeholder="Search by name" value="${param.keyword}">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
        <div class="col-auto">
            <a class="btn btn-success" href="${pageContext.request.contextPath}/guests?action=new">Add Guest</a>
        </div>
    </form>

    <table class="table table-striped table-bordered bg-white">
        <thead>
        <tr>
            <th>ID</th><th>Name</th><th>Type</th><th>Email</th><th>Phone</th><th>RSVP</th><th>Seating</th><th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="g" items="${guests}">
            <tr>
                <td>${g.guestId}</td>
                <td><c:out value="${g.fullName}"/></td>
                <td>${g.guestType}</td>
                <td><c:out value="${g.email}"/></td>
                <td>${g.phone}</td>
                <td>${g.rsvpStatus}</td>
                <td>${g.seatingInfo}</td>
                <td>
                    <a class="btn btn-sm btn-warning"
                       href="${pageContext.request.contextPath}/guests?action=edit&id=${g.guestId}">Edit</a>
                    <form method="post" action="${pageContext.request.contextPath}/guests" style="display:inline"
                          onsubmit="return confirm(&quot;Delete this guest?&quot;);">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${g.guestId}">
                        <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty guests}">
            <tr><td colspan="8" class="text-center">No guests found.</td></tr>
        </c:if>
        </tbody>
    </table>

    <a href="${pageContext.request.contextPath}/">Back to home</a>
</div>
<script src="../assets/js/vows.js"></script>
</body>
</html>
