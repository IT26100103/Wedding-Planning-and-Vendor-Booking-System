<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, viewport-fit=cover">
    <title>Vows &amp; Vendors | Wedding Planning &amp; Vendor Booking System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/vows.css">
</head>
<body>

<jsp:include page="includes/header.jsp"/>

<main>
    <section class="frame" style="text-align: center; padding: 3.5rem 1.5rem; margin-bottom: 2.5rem; background: linear-gradient(180deg, #ffffff 0%, var(--sand-light) 100%);">
        <span class="badge ok" style="margin-bottom: 1rem; display: inline-block;">✦ Sri Lanka's Premier Wedding Platform</span>
        <h1 style="font-size: clamp(2.2rem, 5vw, 3.8rem); margin-bottom: 1rem;">Craft Your Dream Wedding Celebration</h1>
        <p class="lead" style="max-width: 680px; margin: 0 auto 2rem; color: #555;">
            Seamlessly coordinate luxury venues, bespoke caterers, visionary photographers, and real-time guest RSVP seating management.
        </p>
        <div style="display: flex; justify-content: center; gap: 1rem; flex-wrap: wrap;">
            <a href="${pageContext.request.contextPath}/wedding" class="btn">✦ Plan Your Wedding</a>
            <a href="${pageContext.request.contextPath}/vendors" class="btn alt">Browse Vendors</a>
            <a href="${pageContext.request.contextPath}/venues" class="btn outline">Explore Venues</a>
        </div>
    </section>

    <h2 style="text-align: center; margin-bottom: 1.5rem;">Explore System Services</h2>

    <div class="grid" style="grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 1.5rem;">
        <!-- Card 1: Vendors -->
        <div class="card">
            <div>
                <div style="font-size: 2rem; margin-bottom: 0.5rem;">📸 🍽</div>
                <h3>Wedding Vendors</h3>
                <p style="color: #666; font-size: 0.92rem; margin-bottom: 1.2rem;">
                    Find verified catering masters and photographers with dynamic per-guest and package pricing calculators.
                </p>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/vendors" class="btn sm" style="width: 100%; text-align: center;">View Vendors &rarr;</a>
            </div>
        </div>

        <!-- Card 2: Venues -->
        <div class="card">
            <div>
                <div style="font-size: 2rem; margin-bottom: 0.5rem;">🏰 🌴</div>
                <h3>Grand Venues</h3>
                <p style="color: #666; font-size: 0.92rem; margin-bottom: 1.2rem;">
                    Explore majestic indoor ballrooms and seaside outdoor lawns with capacity matching and amenities.
                </p>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/venues" class="btn sm" style="width: 100%; text-align: center;">View Venues &rarr;</a>
            </div>
        </div>

        <!-- Card 3: Guests & RSVP -->
        <div class="card">
            <div>
                <div style="font-size: 2rem; margin-bottom: 0.5rem;">💌 🥂</div>
                <h3>Guest List &amp; RSVP</h3>
                <p style="color: #666; font-size: 0.92rem; margin-bottom: 1.2rem;">
                    Manage your guest invites, assign VIP banquet tables, and let attendees RSVP online with one click.
                </p>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/guests" class="btn sm" style="width: 100%; text-align: center;">Guest Manager &rarr;</a>
            </div>
        </div>

        <!-- Card 4: Reviews -->
        <div class="card">
            <div>
                <div style="font-size: 2rem; margin-bottom: 0.5rem;">⭐ 💬</div>
                <h3>Reviews &amp; Ratings</h3>
                <p style="color: #666; font-size: 0.92rem; margin-bottom: 1.2rem;">
                    Read transparent client testimonials with weighted ratings and verified booking badges.
                </p>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/reviews" class="btn sm" style="width: 100%; text-align: center;">Read Reviews &rarr;</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="includes/footer.jsp"/>

</body>
</html>
