const IMAGES = [
    // ví dụ ảnh mẫu: thay bằng ảnh của bạn
    'https://sieuthiyte.com.vn/data/images/San-Pham/que-thu-may-do-duong-huyet-sapphire-plus-10-que-av1-1.jpg',
    'https://sieuthiyte.com.vn/data/images/San-Pham/que-thu-may-do-duong-huyet-sapphire-plus-10-que-av3.jpg',
    'https://sieuthiyte.com.vn/data/images/San-Pham/que-thu-may-do-duong-huyet-sapphire-plus-10-que-av2.jpg',

];

// Nếu muốn dùng ảnh minh hoạ bạn đã gửi (tệp trong dự án hiện tại),
// có thể tạm thời đặt vào đầu mảng:
// IMAGES.unshift('images/durex-cover.jpg');

const mainImg = document.getElementById('mainImg');
const thumbsEl = document.getElementById('thumbs');
const btnPrev = document.getElementById('btnPrev');
const btnNext = document.getElementById('btnNext');

// Render thumbnail từ mảng IMAGES
function renderThumbs(list){
    thumbsEl.innerHTML = '';
    list.forEach((src, idx) => {
        const item = document.createElement('div');
        item.className = 'thumb';
        item.setAttribute('role','listitem');
        item.dataset.index = idx;
        item.innerHTML = `<img src="${src}" alt="Ảnh ${idx+1}">`;

        // Hover (desktop): show ảnh chính
        item.addEventListener('mouseenter', () => setMain(idx));

        // Click (mobile + desktop): set ảnh chính và đánh dấu active
        item.addEventListener('click', () => setMain(idx));

        thumbsEl.appendChild(item);
    });
}

function setMain(index){
    const src = IMAGES[index];
    if (!src) return;
    mainImg.src = src;

    // set active border cho thumb đang chọn
    thumbsEl.querySelectorAll('.thumb').forEach((t,i)=>{
        t.classList.toggle('is-active', i===index);
    });
}

// Nút cuộn danh sách (kéo dải ảnh)
const SCROLL_STEP = 3; // số thumb mỗi lần cuộn
function scrollThumbs(dir){ // dir = -1 trái, 1 phải
    const thumbSize = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--thumb-size'));
    const gap = parseFloat(getComputedStyle(document.documentElement).getPropertyValue('--gap'));
    const delta = dir * (thumbSize + gap) * SCROLL_STEP;
    thumbsEl.scrollBy({left: delta, behavior: 'smooth'});
}
btnPrev.addEventListener('click', ()=>scrollThumbs(-1));
btnNext.addEventListener('click', ()=>scrollThumbs(1));

// Khởi tạo
const PRODUCT = {
    name: "Que thử đường huyết máy đo Sapphire Plus (10 que)",
    brand: { name: "Toshiba", url: "#" },
    brandLineUrl: "#",
    rating: 4.6,
    ratingCount: 76,
    priceNorth: 1990000,
    priceSouthDelta: 200000,   // +200k khi chọn Miền Nam
    oldPrice: 2900000,
    inStock: true
};

// ===== STATE =====
const state = {
    region: "north", // 'north' | 'south'
    qty: 1
};

// ===== UTILS =====
const fmtVND = n => (n || 0).toLocaleString("vi-VN") + "đ";
const calcPrice = () =>
    state.region === "south" ? PRODUCT.priceNorth + PRODUCT.priceSouthDelta : PRODUCT.priceNorth;
const discountPercent = () => PRODUCT.oldPrice ? Math.round((1 - calcPrice()/PRODUCT.oldPrice) * 100) : 0;
const stars = (value=0) => {
    const full = Math.floor(value);
    let s = "★".repeat(Math.min(full,5));
    while (s.length < 5) s += "☆";
    return s;
};

// ===== RENDER =====
function renderPanel(){
    const el = document.getElementById("product-panel");
    const price = calcPrice();

    el.innerHTML = `
        <h1 class="prod-title">${PRODUCT.name}</h1>

        <div class="divider"></div>

        <div class="price-wrap">
          <div class="price-new" id="price-new">${fmtVND(price)}</div>
          ${PRODUCT.oldPrice ? `<div class="price-old" id="price-old">${fmtVND(PRODUCT.oldPrice)}</div>` : ""}
          ${PRODUCT.oldPrice ? `<div class="price-off" id="price-off">-${discountPercent()}%</div>` : ""}
          <div class="vat-note">(Đã gồm VAT)</div>
        </div>

        <div class="grid">
          <div class="label">Chọn kho hàng:</div>
          <div class="seg" role="tablist" aria-label="Kho hàng">
            <button class="seg-btn ${state.region==='north' ? 'active':''}" data-region="north" role="tab" aria-selected="${state.region==='north'}">Miền Bắc</button>
            <button class="seg-btn ${state.region==='south' ? 'active':''}" data-region="south" role="tab" aria-selected="${state.region==='south'}">Miền Nam: +${fmtVND(PRODUCT.priceSouthDelta)}</button>
          </div>

          <div class="label">Trạng thái:</div>
          <div class="status">${PRODUCT.inStock ? "Còn hàng" : "Hết hàng"}</div>
        </div>

        <div class="qty-row">
          <div class="label">Chọn số lượng:</div>
          <div class="qty" aria-label="Số lượng">
            <button class="qty-minus" aria-label="Giảm">−</button>
            <input class="qty-input" type="text" value="${state.qty}" readonly>
            <button class="qty-plus" aria-label="Tăng">+</button>
          </div>
          <button class="add-cart"><i class="fa-solid fa-cart-plus"></i> Cho vào giỏ</button>
        </div>

        <div class="actions">
          <button class="btn btn-buy"><i class="fa-solid fa-cart-shopping"></i>Đặt mua
          </button>
          <button class="btn btn-call"><i class="fa-solid fa-headset"></i>Tư vấn
          </button>
          <button class="btn btn-credit"><i class="fa-solid fa-coins"></i>Trả góp
          </button>
        </div>

        <div class="benefits">
          <div class="benefit"><i class="fa-solid fa-truck-fast"></i>Miễn phí giao hàng trong nội thành Hà Nội và nội thành TP. Hồ Chí Minh. <a href="#" style="margin-left:6px;color:#0ea5e9;text-decoration:none;">(Xem thêm)</a></div>
          <div class="benefit"><i class="fa-solid fa-file-invoice"></i>Được hàng trăm ngàn Doanh nghiệp tại Việt Nam lựa chọn: đầy đủ hóa đơn, hợp đồng, không chi phí ẩn <a href="#" style="margin-left:6px;color:#0ea5e9;text-decoration:none;">(Xem thêm)</a></div>
          <div class="benefit"><i class="fa-solid fa-shield-heart"></i>Bảo hành toàn quốc. <a href="#" style="margin-left:6px;color:#0ea5e9;text-decoration:none;">(Xem trung tâm bảo hành)</a></div>
        </div>

        <div class="foot">META.vn – Điện Máy Giá Cực Tốt, 18 Năm Uy Tín Bán Hàng Toàn Quốc</div>
      `;

    bindEvents();
}

// ===== EVENTS =====
function bindEvents(){
    // chọn kho hàng
    document.querySelectorAll(".seg-btn").forEach(btn=>{
        btn.addEventListener("click", ()=>{
            state.region = btn.dataset.region;
            renderPanel(); // re-render để cập nhật active + giá
        });
    });

    // qty
    const minus = document.querySelector(".qty-minus");
    const plus  = document.querySelector(".qty-plus");
    const input = document.querySelector(".qty-input");

    minus.addEventListener("click", ()=>{
        state.qty = Math.max(1, state.qty - 1);
        input.value = state.qty;
    });
    plus.addEventListener("click", ()=>{
        state.qty = Math.max(1, state.qty + 1);
        input.value = state.qty;
    });

    // add to cart (demo)
    document.querySelector(".add-cart").addEventListener("click", ()=>{
        const price = calcPrice();
        console.log("ADD_TO_CART", { product: PRODUCT.name, region: state.region, qty: state.qty, price });
        alert(`Đã thêm ${state.qty} x "${PRODUCT.name}" (${state.region==='north'?'Miền Bắc':'Miền Nam'}) – ${fmtVND(price)} vào giỏ (demo).`);
        // TODO: gọi addToCart(PRODUCT_ID, state.qty, state.region)
    });

    // 3 nút lớn (demo)
    document.querySelector(".btn-buy")?.addEventListener("click", ()=> alert("Đặt mua (demo)"));
    document.querySelector(".btn-call")?.addEventListener("click", ()=> alert("Tư vấn (demo)"));
    document.querySelector(".btn-credit")?.addEventListener("click", ()=> alert("Trả góp (demo)"));
}

// ===== INIT =====
renderPanel();
renderThumbs(IMAGES);
setMain(0);