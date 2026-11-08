<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>${empty vendor ? 'Register New Vendor' : 'Edit Vendor'} | Vows &amp; Vendors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<main>
    <div style="max-width: 650px; margin: 2rem auto;">
        <div class="frame">
            <h1>${empty vendor ? 'Register New Vendor' : 'Edit Vendor'}</h1>
            <p class="lead" style="margin-bottom: 2rem;">Manage service offerings, pricing structures, and contact details.</p>

            <c:if test="${not empty error}">
                <div class="alert error">${error}</div>
            </c:if>

            <form class="form" method="post" action="${pageContext.request.contextPath}/vendors">
                <input type="hidden" name="action" value="save">
                <input type="hidden" name="id" value="${vendor.id}">

                <div class="full">
                    <label for="name">Business / Vendor Name *</label>
                    <input type="text" id="name" name="name" value="${vendor.name}" required placeholder="e.g. Spice Table Catering">
                </div>

                <div>
                    <label for="category">Category *</label>
                    <select id="category" name="category" onchange="updatePriceLabel()">
                        <option value="CATERING" ${vendor.category == 'CATERING' ? 'selected' : ''}>Catering &amp; Cuisine</option>
                        <option value="PHOTOGRAPHY" ${vendor.category == 'PHOTOGRAPHY' ? 'selected' : ''}>Photography &amp; Media</option>
                    </select>
                </div>

                <div>
                    <label for="phone">Phone Number (10 digits) *</label>
                    <input type="text" id="phone" name="phone" value="${vendor.phone}" required placeholder="0771111111">
                </div>

                <div class="full">
                    <label id="priceLabel">Base Price (LKR) *</label>
                    <input type="number" id="basePrice" name="basePrice" value="${vendor.basePrice}" required min="0" step="100" placeholder="e.g. 2500">
                </div>

                <div class="full" style="display: flex; gap: 0.8rem; margin-top: 1rem;">
                    <button type="submit" class="btn">Save Vendor</button>
                    <a href="${pageContext.request.contextPath}/vendors" class="btn outline">Cancel</a>
                </div>
            </form>
        </div>
    </div>
</main>

<jsp:include page="../includes/footer.jsp"/>

<script>
function updatePriceLabel() {
    var cat = document.getElementById('category').value;
    var lbl = document.getElementById('priceLabel');
    if (cat === 'CATERING') {
        lbl.textContent = 'Price Per Plate (LKR) *';
    } else {
        lbl.textContent = 'Fixed Package Price (LKR) *';
    }
}
updatePriceLabel();
</script>
</body>
</html>
