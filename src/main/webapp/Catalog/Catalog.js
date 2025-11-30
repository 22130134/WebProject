// Catalog/catalog.js
(() => {
    "use strict";

    /* ===================== CẤU HÌNH ĐƯỜNG DẪN ===================== */
    // Mặc định dẫn tới: WebProject/Product_Detail/Product_Detail.html
    const DETAIL_BASE = "/WebProject/Product_Detail/ProductDetail.html";

    /* ===================== TIỆN ÍCH ===================== */
    const slugify = s => s.toLowerCase()
        .normalize("NFD").replace(/[\u0300-\u036f]/g, "")
        .replace(/[^a-z0-9]+/g, "-").replace(/^-+|-+$/g, "");

    const toDetailURL = p =>
        `${DETAIL_BASE}?id=${encodeURIComponent(p.id)}&slug=${encodeURIComponent(slugify(p.name))}`;

    /* ===================== DỮ LIỆU ===================== */
    const RAW_PRODUCTS = [
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
            name: "Máy Massage Mặt ROAMAN M7 Làm Sạch Và Trẻ Hóa",
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

    const parseVND = s => Number(String(s).replace(/[^\d]/g, "")) || 0;

    const PRODUCTS = RAW_PRODUCTS.map((p, i) => ({
        id: i + 1,
        name: p.name,
        brand: p.brand,
        img: p.img,
        price: parseVND(p.priceNew),
        oldPrice: parseVND(p.priceOld),
        rating: p.stars,
        reviews: p.count,
        badge: p.badge,
        createdAt: new Date(2025, 0, 1 + i).toISOString().slice(0, 10),
        sold: 100 - i * 7,
        recommend: i % 2,
        installment: true
    }));

    /* ===================== STATE + TIỆN ÍCH ===================== */
    const PRICE_RANGES = [
        { id: "p1", label: "Dưới 2 triệu", test: p => p.price < 2000000 },
        { id: "p2", label: "2 triệu - Dưới 4 triệu", test: p => p.price >= 2000000 && p.price < 4000000 },
        { id: "p3", label: "4 triệu - Dưới 6 triệu", test: p => p.price >= 4000000 && p.price < 6000000 },
        { id: "p4", label: "6 triệu trở lên", test: p => p.price >= 6000000 }
    ];

    const state = {
        brands: new Set(),
        priceIds: new Set(),
        sort: "best",
        brandKeyword: "",
        categorySlug: null
    };

    const fmtVND = n => n.toLocaleString("vi-VN") + " đ";
    const starHTML = (v = 0) => {
        const full = Math.floor(v), half = v - full >= 0.5 ? 1 : 0, empty = 5 - full - half;
        return `<span class="stars">${"★".repeat(full)}${half ? "☆" : ""}${"✩".repeat(empty)}</span>`;
    };
    const percentOff = p => (!p.oldPrice || p.oldPrice <= p.price) ? null : Math.round((1 - p.price / p.oldPrice) * 100);

    /* ===================== FILTER / SORT ===================== */
    function renderBrandList() {
        const wrap = document.getElementById("brandList");
        if (!wrap) return;
        const kw = state.brandKeyword.trim().toLowerCase();
        const brands = [...new Set(PRODUCTS.map(p => p.brand))].sort()
            .filter(b => !kw || b.toLowerCase().includes(kw));
        wrap.innerHTML = brands.map(b => `
      <label class="chk">
        <input type="checkbox" class="brand-chk" value="${b}" ${state.brands.has(b) ? "checked" : ""}>
        <span>${b}</span>
      </label>
    `).join("");
    }

    function renderPriceList() {
        const wrap = document.getElementById("priceList");
        if (!wrap) return;
        wrap.innerHTML = PRICE_RANGES.map(r => `
      <label class="chk">
        <input type="checkbox" class="price-chk" value="${r.id}" ${state.priceIds.has(r.id) ? "checked" : ""}>
        <span>${r.label}</span>
      </label>
    `).join("");
    }

    function applyFilters(products) {
        let out = [...products];
        if (state.brands.size) out = out.filter(p => state.brands.has(p.brand));
        if (state.priceIds.size) {
            const tests = PRICE_RANGES.filter(r => state.priceIds.has(r.id)).map(r => r.test);
            out = out.filter(p => tests.some(t => t(p)));
        }
        return out;
    }

    function sortProducts(list) {
        const map = {
            priceAsc: (a, b) => a.price - b.price,
            priceDesc: (a, b) => b.price - a.price,
            newest: (a, b) => new Date(b.createdAt) - new Date(a.createdAt),
            best: (a, b) => b.sold - a.sold,
            installment: (a, b) => (b.installment - a.installment) || (a.price - b.price),
            recommend: (a, b) => (b.recommend - a.recommend) || (b.sold - a.sold)
        };
        return list.sort(map[state.sort] || map.best);
    }

    /* ===================== RENDER ===================== */
    function renderSortBar() {
        const bar = document.getElementById("sortBar");
        if (!bar) return;
        [...bar.querySelectorAll(".sort-btn")].forEach(btn => {
            btn.classList.toggle("active", btn.dataset.sort === state.sort);
        });
    }

    function renderGrid(items) {
        const grid = document.getElementById("productGrid");
        if (!grid) return;
        grid.innerHTML = items.map(p => {
            const off = percentOff(p);
            const href = toDetailURL(p);
            return `
        <article class="p-card" data-id="${p.id}">
          ${off !== null ? `<div class="p-badge">-${off}%</div>` : ""}
          <a class="p-thumb" href="${href}">
            <img class="p-img" src="${p.img}" alt="${p.name}">
          </a>
          <h3 class="p-title"><a href="${href}" class="p-link">${p.name}</a></h3>
          <div class="p-rating">${starHTML(p.rating)} <span class="p-reviews">(${p.reviews})</span></div>
          <div class="p-price">
            <span class="price-new">${fmtVND(p.price)}</span>
            ${p.oldPrice ? `<s class="price-old">${fmtVND(p.oldPrice)}</s>` : ""}
          </div>
          <a href="${href}" class="p-buy">MUA NGAY</a>
        </article>`;
        }).join("");

        const counter = document.getElementById("resultCounter");
        if (counter) counter.textContent = `${items.length} sản phẩm`;
    }

    /* ===================== CẬP NHẬT & SỰ KIỆN ===================== */
    function update() {
        renderSortBar();
        const filtered = applyFilters(PRODUCTS);
        const sorted = sortProducts(filtered);
        renderGrid(sorted);
    }

    function mountEvents() {
        const sortBar = document.getElementById("sortBar");
        if (sortBar) {
            sortBar.addEventListener("click", e => {
                const btn = e.target.closest(".sort-btn");
                if (!btn) return;
                state.sort = btn.dataset.sort;
                update();
            });
        }

        const search = document.getElementById("brandSearch");
        const searchBtn = document.getElementById("brandSearchBtn");
        if (search && searchBtn) {
            searchBtn.addEventListener("click", () => {
                state.brandKeyword = search.value;
                renderBrandList();
            });
            search.addEventListener("input", () => {
                state.brandKeyword = search.value;
                renderBrandList();
            });
        }

        const brandList = document.getElementById("brandList");
        if (brandList) {
            brandList.addEventListener("change", e => {
                if (e.target.classList.contains("brand-chk")) {
                    const v = e.target.value;
                    e.target.checked ? state.brands.add(v) : state.brands.delete(v);
                    update();
                }
            });
        }

        const priceList = document.getElementById("priceList");
        if (priceList) {
            priceList.addEventListener("change", e => {
                if (e.target.classList.contains("price-chk")) {
                    const v = e.target.value;
                    e.target.checked ? state.priceIds.add(v) : state.priceIds.delete(v);
                    update();
                }
            });
        }
    }

    /* ===================== HASH + BREADCRUMB ===================== */
    const LABELS = [
        "Combo khuyến mãi",
        "Y tế gia đình",
        "Y tế chuyên dụng",
        "Chăm sóc sắc đẹp",
        "Chăm sóc sức khỏe",
        "Thiết bị gia đình",
        "Đồ dùng mẹ và bé",
        "Đồ thể thao",
        "Quà tặng"
    ];
    const SLUG_TO_LABEL = new Map(LABELS.map(n => [slugify(n), n]));

    function setBreadcrumb(label) {
        const el = document.getElementById("crumbCurrent");
        if (el) el.textContent = label || "Danh mục";
        if (label) document.title = `${label} | Catalog`;
    }

    function applyHash() {
        const slug = (location.hash || "").replace(/^#/, "");
        setBreadcrumb(SLUG_TO_LABEL.get(slug) || "Danh mục");
        window.scrollTo({ top: 0, behavior: "instant" });
        update();
    }

    /* ===================== KHỞI TẠO ===================== */
    document.addEventListener("DOMContentLoaded", () => {
        renderBrandList();
        renderPriceList();
        mountEvents();
        applyHash();
        window.addEventListener("hashchange", applyHash);
    });
})();
