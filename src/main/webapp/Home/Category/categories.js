const DM_CATEGORIES = [
    { name: "Tivi", img: "https://sieuthiyte.com.vn/data/images/San-Pham/may-do-huyet-ap-bap-tay-dien-tu-vofonn-av1-2.jpg" },
    { name: "Máy giặt", img: "https://sieuthiyte.com.vn/data/product/201705/may-do-axit-uric1495244480.nv.jpg" },
    { name: "Máy sấy quần áo", img: "https://sieuthiyte.com.vn/data/images/San-Pham/ay-xong-khi-dung-wellmed-axd-304-avbs1-3.jpg" },
    { name: "Máy lọc không khí", img: "https://sieuthiyte.com.vn/data/images/San-Pham/ay-xong-khi-dung-wellmed-axd-304-avbs1-3.jpg" },
    { name: "Máy làm đá viên", img: "https://sieuthiyte.com.vn/data/images/San-Pham/dai-keo-gian-cot-song-co-alphay-jqah-6-khong-co-bom-hoi-av5.jpg" },
    { name: "Máy hút ẩm", img: "https://sieuthiyte.com.vn/data/images/San-Pham/may-tro-thinh-deo-trong-tai-cao-cap-enno-en-ia013a-av1-1.jpg" },
    { name: "Tủ lạnh", img: "https://sieuthiyte.com.vn/data/images/San-Pham/may-tro-thinh-deo-trong-tai-cao-cap-enno-en-ia013a-av1-1.jpg" },
    { name: "Điều hòa", img: "https://sieuthiyte.com.vn/data/product/201812/31545187539.nv.png" },
    { name: "Tủ đông", img: "https://sieuthiyte.com.vn/data/product/202007/nhiet-ke-hong-ngoai-do-tran-khong-tiep-xuc-reiwa-jxb-183-av11596013459.nv.png" },
    { name: "Tủ mát", img: "https://sieuthiyte.com.vn/data/product/201809/31538037377.nv.png" },
    { name: "Bình nóng lạnh", img: "https://sieuthiyte.com.vn/data/images/San-Pham/may-hut-mui-cho-be-reiwa-dq8-av1.jpg" },
    { name: "Máy rửa chén bát", img: "https://sieuthiyte.com.vn/data/product/202112/den-soi-tai-wellmed-ot-10j-av11639544942.nv.jpg" },
];

const DM_PRODUCTS = [
    {
        badge: "45%",
        name: "Máy Xông Hơi Mặt Công Nghệ Ion Reiwa WT-300",
        brand: "Reiwa",
        img: "https://sieuthiyte.com.vn/data/images/San-Pham/combo-reiwa-may-xong-hoi-mat-may-rua-mat-avt1.jpg",
        priceNew: "499.000đ",
        priceOld: "900.000đ",
        stars: 5,
        count: 0
    },
    {
        badge: "45%",
        name: "Máy Rửa Mặt Làm Sạch Sâu 3 Trong 1 Reiwa WT-222-2",
        brand: "Reiwa",
        img: "https://sieuthiyte.com.vn/data/product/201910/touchbeauty-tb1581-11571905034.nv.jpg",
        priceNew: "499.000đ",
        priceOld: "900.000đ",
        stars: 4,
        count: 0
    },
    {
        badge: "41%",
        name: "Máy Hút Mụn Đầu Đen 2 Trong 1 Reiwa BR-150",
        brand: "Reiwa",
        img: "https://sieuthiyte.com.vn/data/images/San-Pham/may-hut-mun-dau-den-2-trong-1-reiwa-av1.jpg",
        priceNew: "650.000đ",
        priceOld: "1.100.000đ",
        stars: 5,
        count: 0
    },
    {
        badge: "46%",
        name: "Cây Lăn Massage Mặt Và Body Kakusan KB-213",
        brand: "Kakusan",
        img: "https://sieuthiyte.com.vn/data/images/San-Pham/cay-lan-massage-mat-va-body-kakusan-kb-213-av1-f1.jpg",
        priceNew: "590.000đ",
        priceOld: "1.100.000đ",
        stars: 4,
        count: 0
    },
    {
        badge: "44%",
        name: "Máy Xông Hơi Mặt TouchBeauty TB-1586",
        brand: "TouchBeauty",
        img: "https://sieuthiyte.com.vn/data/product/201910/touchbeauty-tb14838-1-2-1571906196.nv.jpg",
        priceNew: "650.000đ",
        priceOld: "1.160.000đ",
        stars: 3,
        count: 0
    },
    {
        badge: "50%",
        name: "Máy Massage Mặt ROAMAN M7 Làm Sạch Và Trẻ Hóa Làn Da Bằng Công Nghệ Ion",
        brand: "ROAMAN",
        img: "https://sieuthiyte.com.vn/assets/uploads/roamanm7-101572593119.nv.png",
        priceNew: "450.000đ",
        priceOld: "900.000đ",
        stars: 3,
        count: 0
    },
    {
        badge: "59%",
        name: "Máy Cạo Râu ROAMAN RMS7109",
        brand: "ROAMAN",
        img: "https://sieuthiyte.com.vn/data/product/202207/may-cao-rau-roaman-rms7109-av-22072022-v31658463335.nv.jpg",
        priceNew: "450.000đ",
        priceOld: "1.090.000đ",
        stars: 4,
        count: 0
    },
    {
        badge: "49%",
        name: "Gen Nịt Bụng Tạo Dáng Microfiber Art.605",
        brand: "Microfiber",
        img: "https://sieuthiyte.com.vn/assets/uploads/21545706638.nv.png",
        priceNew: "199.000đ",
        priceOld: "390.000đ",
        stars: 4,
        count: 0
    }
];

// ===== util slug =====
const slugify = (s) =>
    s.toLowerCase()
        .normalize('NFD').replace(/[\u0300-\u036f]/g,'') // bỏ dấu tiếng Việt
        .replace(/[^a-z0-9]+/g,'-')
        .replace(/-+/g,'-')
        .replace(/^-|-$/g,'');

// ===== render =====
function renderDmCategory(cat) {
    return `
      <a class="dm-cat" href="#">
        <img src="${cat.img}" alt="${cat.name}">
        <span>${cat.name}</span>
      </a>`;
}

// Giữ nguyên tên hàm, chỉ thêm href từ slug (THẺ <a> LIÊN KẾT)
function renderDmProduct(p) {
    const stars = "★".repeat(p.stars) + "☆".repeat(5 - p.stars);
    const badgeHTML = p.badge ? `<span class="badge ${p.badge.includes('%') ? 'badge--sale' : 'badge--gift'}">${p.badge}</span>` : '';
    const url = `/product-detail.html?slug=${encodeURIComponent(slugify(p.name))}`;
    return `
<a class="dm-card" href="/WebProject/Product_Detail/ProductDetail.html">
        ${badgeHTML}
        <div class="thumb"><img src="${p.img}" alt="${p.name}"></div>
        <div class="brand">${p.brand}</div>
        <h4 class="name">${p.name}</h4>
        <div class="price">
          <span class="new">${p.priceNew}</span>
          ${p.priceOld ? `<span class="old">${p.priceOld}</span>` : ''}
        </div>
        <div class="rating">
          <span class="stars">${stars}</span>
          <span class="count">(${p.count})</span>
        </div>
      </a>`;
}

function mountDm() {
    const dmSection = document.getElementById("dm-section");
    if (!dmSection) return;

    dmSection.innerHTML = `
      <div class="dm-head">
        <h2>Y Tế Gia Đình</h2>
        <a class="dm-viewall" href="/danh-muc/y-te-gia-dinh">Xem tất cả &gt;</a>
      </div>

      <div class="dm-cats">${DM_CATEGORIES.map(renderDmCategory).join("")}</div>

      <div class="dm-subhead">
        <h3>Sản phẩm nổi bật</h3>
        <a class="dm-btn" href="/san-pham/noi-bat">Xem tất cả sản phẩm</a>
      </div>

      <div class="dm-slider" id="dm-slider">
        <button class="dm-nav dm-prev" aria-label="Trước">&#10094;</button>
        <div class="dm-track" id="dm-track" style="--ppv:5">
          ${DM_PRODUCTS.map(renderDmProduct).join("")}
        </div>
        <button class="dm-nav dm-next" aria-label="Sau">&#10095;</button>
      </div>
    `;

    initDmSlider();
}

// ===== logic slider =====
function initDmSlider(){
    const track = document.getElementById("dm-track");
    const prevBtn = document.querySelector(".dm-prev");
    const nextBtn = document.querySelector(".dm-next");

    const updatePPV = () => {
        let ppv = 5;
        const w = window.innerWidth;
        if (w <= 480) ppv = 2;
        else if (w <= 768) ppv = 2;
        else if (w <= 992) ppv = 3;
        else if (w <= 1200) ppv = 4;
        track.style.setProperty("--ppv", ppv);
        cardsPerView = ppv;
        clampIndex();
        scrollToIndex(false);
    };

    const cards = Array.from(track.children);
    let cardsPerView = 5;
    let index = 0;

    const maxIndex = () => Math.max(0, cards.length - cardsPerView);

    const clampIndex = () => { index = Math.min(Math.max(0, index), maxIndex()); };
    const setDisabled = () => {
        if (index === 0) prevBtn.setAttribute("disabled", "");
        else prevBtn.removeAttribute("disabled");
        if (index === maxIndex()) nextBtn.setAttribute("disabled", "");
        else nextBtn.removeAttribute("disabled");
    };

    const scrollToIndex = (smooth = true) => {
        const gap = parseFloat(getComputedStyle(track).getPropertyValue("--gap")) || 14;
        const cardW = cards[0]?.getBoundingClientRect().width || 0;
        const x = index * (cardW + gap);
        track.scrollTo({ left: x, behavior: smooth ? "smooth" : "instant" });
        setDisabled();
    };

    prevBtn.addEventListener("click", () => { index--; clampIndex(); scrollToIndex(); });
    nextBtn.addEventListener("click", () => { index++; clampIndex(); scrollToIndex(); });

    // Drag / Touch
    let isDown = false, startX = 0, startScroll = 0;
    const onDown = (e) => {
        isDown = true;
        startX = (e.touches ? e.touches[0].pageX : e.pageX);
        startScroll = track.scrollLeft;
    };
    const onMove = (e) => {
        if (!isDown) return;
        const x = (e.touches ? e.touches[0].pageX : e.pageX);
        track.scrollLeft = startScroll - (x - startX);
    };
    const onUp = () => {
        if (!isDown) return; isDown = false;
        const gap = parseFloat(getComputedStyle(track).getPropertyValue("--gap")) || 14;
        const cardW = cards[0]?.getBoundingClientRect().width || 0;
        const rawIndex = Math.round(track.scrollLeft / (cardW + gap));
        index = Math.min(Math.max(0, rawIndex), maxIndex());
        scrollToIndex();
    };

    track.addEventListener("mousedown", onDown);
    track.addEventListener("mousemove", onMove);
    window.addEventListener("mouseup", onUp);
    track.addEventListener("touchstart", onDown, {passive:true});
    track.addEventListener("touchmove", onMove, {passive:true});
    track.addEventListener("touchend", onUp);

    window.addEventListener("resize", updatePPV);

    // init
    updatePPV();
    setDisabled();
}

if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", mountDm);
} else {
    mountDm();
}
;