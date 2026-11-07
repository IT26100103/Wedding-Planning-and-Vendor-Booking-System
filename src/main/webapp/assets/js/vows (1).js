/* vows.js - adds the video background and the photos to EVERY page.
   Put this file in src/main/webapp/assets/js/ and link it at the end of each page. */
(function () {
  var me = document.currentScript || document.querySelector('script[src*="vows.js"]');
  var base = me.src.replace(/js\/vows\.js.*$/, '');      // .../assets/
  var img = function (n) { return "url('" + base + "img/" + n + "')"; };

  // 0. Styles for the video background, hero split and slideshow (so no css file edit is needed)
  var st = document.createElement('style');
  st.textContent =
    '.page-bg{position:fixed;inset:0;z-index:-1;overflow:hidden;background:#fbf9f2}' +
    '.page-bg video{width:100%;height:100%;object-fit:cover}' +
    '.page-bg::after{content:"";position:absolute;inset:0;background:rgba(255,255,255,.66)}' +
    'body{background:transparent}' +
    '.card,.stat,.tbl{background:rgba(255,255,255,.92)}' +
    '.photo{background-size:cover;background-position:center}' +
    '.hero-split{display:grid;grid-template-columns:1fr 1fr;gap:30px;align-items:stretch;margin:2rem 0}' +
    '.hero-split>.frame{margin:0;display:flex;flex-direction:column;justify-content:center;padding:2rem 1.5rem}' +
    '.hero-split>.photo{margin:0;min-height:460px;position:relative;overflow:hidden;padding:0}' +
    '.slides{position:absolute;inset:0}' +
    '.slides span{position:absolute;inset:0;background-size:cover;background-position:center;opacity:0;animation:slidefade 18s infinite}' +
    '.slides span:nth-child(2){animation-delay:6s}.slides span:nth-child(3){animation-delay:12s}' +
    '@keyframes slidefade{0%{opacity:0}8%{opacity:1}33%{opacity:1}41%{opacity:0}100%{opacity:0}}' +
    '.gallery .photo{min-height:200px}' +
    '@media(max-width:700px){.hero-split{grid-template-columns:1fr}.hero-split>.photo{min-height:300px}}';
  document.head.appendChild(st);

  // 1. Video background on every page
  if (!document.querySelector('.page-bg')) {
    var bg = document.createElement('div');
    bg.className = 'page-bg';
    bg.setAttribute('aria-hidden', 'true');
    bg.innerHTML = '<video autoplay muted loop playsinline preload="metadata" poster="' + base +
      'img/venue-poster.jpg"><source src="' + base + 'video/venue.mp4" type="video/mp4"></video>';
    document.body.insertBefore(bg, document.body.firstChild);
  }

  function empty(el) { return !el.children.length && !el.textContent.trim(); }
  function hasImage(el) { return /url\(/.test(el.style.backgroundImage || ''); }

  // 2. Hero: text box background + photo slideshow (home page)
  document.querySelectorAll('.hero-split').forEach(function (hero) {
    var frame = hero.querySelector('.frame');
    if (frame && !hasImage(frame)) {
      frame.style.backgroundImage = img('hero-bg.jpg');
      frame.style.backgroundSize = 'cover';
      frame.style.backgroundPosition = 'center';
    }
    var ph = hero.querySelector('.photo');
    if (ph && !ph.querySelector('.slides') && !hasImage(ph)) {
      var s = document.createElement('div');
      s.className = 'slides';
      s.setAttribute('aria-hidden', 'true');
      ['hero.jpg', 'hero2.jpg', 'hero3.jpg'].forEach(function (n) {
        var sp = document.createElement('span');
        sp.style.backgroundImage = img(n);
        s.appendChild(sp);
      });
      ph.appendChild(s);
    }
  });

  // 3. Card photos: choose the picture from the card text
  var rules = [
    [/photo/i, 'vendor-photo.jpg'], [/cater|buffet|spice|food|menu/i, 'vendor-buffet.jpg'],
    [/decor|flor|flower/i, 'vendor-decor.jpg'], [/staff|service/i, 'vendor-staff.jpg'],
    [/night|evening/i, 'venue-night.jpg'], [/aisle|garden|outdoor/i, 'venue-aisle.jpg'],
    [/venue|hall|table|indoor/i, 'venue-table.jpg'], [/pay|confirm|budget/i, 'toast.jpg'],
    [/wedding|couple|profile/i, 'wedding-mrmrs.jpg']
  ];
  var spare = ['vendor-buffet2.jpg', 'venue-table.jpg', 'vendor-decor.jpg', 'toast.jpg', 'venue-aisle.jpg'];
  var k = 0;
  document.querySelectorAll('.card').forEach(function (card) {
    var ph = card.querySelector('.photo') || (card.firstElementChild && empty(card.firstElementChild) ? card.firstElementChild : null);
    if (!ph || hasImage(ph)) return;
    var file = null, t = card.textContent;
    for (var i = 0; i < rules.length; i++) { if (rules[i][0].test(t)) { file = rules[i][1]; break; } }
    ph.style.backgroundImage = img(file || spare[k++ % spare.length]);
    ph.style.backgroundSize = 'cover';
    ph.style.backgroundPosition = 'center';
  });
  // gallery photos (not inside a card)
  document.querySelectorAll('.gallery .photo').forEach(function (ph) {
    if (hasImage(ph)) return;
    ph.style.backgroundImage = img(spare[k++ % spare.length]);
    ph.style.backgroundSize = 'cover';
    ph.style.backgroundPosition = 'center';
  });
})();
