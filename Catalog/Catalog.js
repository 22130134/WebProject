// catalog.js
// ====== Data mẫu (bạn thay bằng API/DB sau) ======
// ===== DỮ LIỆU GỐC (giữ nguyên format bạn đưa) =====
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

// ===== Chuẩn hoá sang schema hiện có =====
const parseVND = s => Number(String(s).replace(/[^\d]/g, "")) || 0;

const PRODUCTS = RAW_PRODUCTS.map((p, i) => ({
    id: i + 1,
    name: p.name,
    brand: p.brand,
    img: p.img,
    price: parseVND(p.priceNew),       // số để sort/lọc
    oldPrice: parseVND(p.priceOld),
    rating: p.stars,
    reviews: p.count,
    badge: p.badge,                    // giữ badge text nếu muốn hiển thị đúng % bạn cung cấp
    createdAt: new Date(2025, 0, 1 + i).toISOString().slice(0,10), // demo
    sold: 100 - i * 7,                 // demo cho nút "Bán chạy"
    recommend: i % 2,                  // demo
    installment: true,                 // demo
    cat: "beauty"                      // gán chung 1 danh mục nếu đang lọc theo cat
}));


const PRICE_RANGES = [
    {id:"p1", label:"Dưới 2 triệu", test:p=>p.price < 2000000},
    {id:"p2", label:"2 triệu - Dưới 4 triệu", test:p=>p.price >= 2000000 && p.price < 4000000},
    {id:"p3", label:"4 triệu - Dưới 6 triệu", test:p=>p.price >= 4000000 && p.price < 6000000},
    {id:"p4", label:"6 triệu trở lên", test:p=>p.price >= 6000000}
];

// ====== State ======
const state = {
    brands: new Set(),   // tên thương hiệu đã chọn
    priceIds: new Set(), // id khoảng giá đã chọn
    sort: "best",        // key sắp xếp
    brandKeyword: ""
};

// ====== Utils ======
const fmtVND = n => n.toLocaleString("vi-VN") + " đ";
const starHTML = (v=0) => {
    const full = Math.floor(v), half = v - full >= 0.5 ? 1 : 0, empty = 5 - full - half;
    return `<span class="stars">${"★".repeat(full)}${half?"☆":""}${"✩".repeat(empty)}</span>`;
};
const percentOff = (p) => {
    if (!p.oldPrice || p.oldPrice <= p.price) return null;
    return Math.round((1 - p.price/p.oldPrice) * 100);
};

// ====== Render LEFT: Brand & Price ======
function renderBrandList() {
    const wrap = document.getElementById("brandList");
    const kw = state.brandKeyword.trim().toLowerCase();
    const brands = [...new Set(PRODUCTS.map(p=>p.brand))].sort()
        .filter(b => !kw || b.toLowerCase().includes(kw));
    wrap.innerHTML = brands.map(b => `
    <label class="chk">
      <input type="checkbox" class="brand-chk" value="${b}" ${state.brands.has(b)?"checked":""}>
      <span>${b}</span>
    </label>
  `).join("");
}
function renderPriceList() {
    const wrap = document.getElementById("priceList");
    wrap.innerHTML = PRICE_RANGES.map(r => `
    <label class="chk">
      <input type="checkbox" class="price-chk" value="${r.id}" ${state.priceIds.has(r.id)?"checked":""}>
      <span>${r.label}</span>
    </label>
  `).join("");
}

// ====== Filter + Sort ======
function applyFilters(products) {
    let out = [...products];

    // brand
    if (state.brands.size) out = out.filter(p => state.brands.has(p.brand));

    // price
    if (state.priceIds.size) {
        const tests = PRICE_RANGES.filter(r=>state.priceIds.has(r.id)).map(r=>r.test);
        out = out.filter(p => tests.some(t => t(p)));
    }
    return out;
}

function sortProducts(list) {
    const by = state.sort;
    const map = {
        priceAsc: (a,b)=>a.price-b.price,
        priceDesc: (a,b)=>b.price-a.price,
        newest: (a,b)=>new Date(b.createdAt)-new Date(a.createdAt),
        best: (a,b)=>b.sold-a.sold,
        installment: (a,b)=> (b.installment - a.installment) || (a.price - b.price),
        recommend: (a,b)=> (b.recommend - a.recommend) || (b.sold - a.sold)
    };
    return list.sort(map[by] || map.best);
}

// ====== Render RIGHT: Sort bar + Grid ======
function renderSortBar() {
    const bar = document.getElementById("sortBar");
    [...bar.querySelectorAll(".sort-btn")].forEach(btn=>{
        btn.classList.toggle("active", btn.dataset.sort === state.sort);
    });
}

function renderGrid(items) {
    const grid = document.getElementById("productGrid");
    grid.innerHTML = items.map(p => {
        const off = percentOff(p);
        return `
    <article class="p-card">
      ${off!==null?`<div class="p-badge">-${off}%</div>`:""}
      <div class="p-thumb"><img class="p-img" src="${p.img}" alt="${p.name}"></div>
      <h3 class="p-title">${p.name}</h3>
      <div class="p-rating">${starHTML(p.rating)} <span class="p-reviews">(${p.reviews})</span></div>
      <div class="p-price">
        <span class="price-new">${fmtVND(p.price)}</span>
        ${p.oldPrice?`<s class="price-old">${fmtVND(p.oldPrice)}</s>`:""}
      </div>
      <button class="p-buy">MUA NGAY</button>
    </article>`;
    }).join("");

    document.getElementById("resultCounter").textContent = `${items.length} sản phẩm`;
}

// ====== Mount & Events ======
function update() {
    renderSortBar();
    const filtered = applyFilters(PRODUCTS);
    const sorted = sortProducts(filtered);
    renderGrid(sorted);
}

function mountEvents() {
    // Sort
    document.getElementById("sortBar").addEventListener("click", e=>{
        const btn = e.target.closest(".sort-btn");
        if (!btn) return;
        state.sort = btn.dataset.sort;
        update();
    });

    // Brand search
    const search = document.getElementById("brandSearch");
    document.getElementById("brandSearchBtn").addEventListener("click", ()=>{
        state.brandKeyword = search.value;
        renderBrandList();
    });
    search.addEventListener("input", ()=>{
        state.brandKeyword = search.value;
        renderBrandList();
    });

    // Brand checkboxes
    document.getElementById("brandList").addEventListener("change", e=>{
        if (e.target.classList.contains("brand-chk")) {
            const v = e.target.value;
            e.target.checked ? state.brands.add(v) : state.brands.delete(v);
            update();
        }
    });

    // Price checkboxes
    document.getElementById("priceList").addEventListener("change", e=>{
        if (e.target.classList.contains("price-chk")) {
            const v = e.target.value;
            e.target.checked ? state.priceIds.add(v) : state.priceIds.delete(v);
            update();
        }
    });
}

// ====== Init ======
(function init(){
    renderBrandList();
    renderPriceList();
    mountEvents();
    update();
})();
