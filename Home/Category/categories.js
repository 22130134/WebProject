(() => {
    // dữ liệu riêng
    const DM_CATEGORIES = [
        { name: "Tivi", img: "img/tivi.png" },
        { name: "Máy giặt", img: "img/maygiat.png" },
        { name: "Máy sấy quần áo", img: "img/maysay.png" },
        { name: "Máy lọc không khí", img: "img/mayloc.png" },
        { name: "Máy làm đá viên", img: "img/mayda.png" },
        { name: "Máy hút ẩm", img: "img/mayhutam.png" },
        { name: "Tủ lạnh", img: "img/tulanh.png" },
        { name: "Điều hòa", img: "img/dieuhoa.png" },
        { name: "Tủ đông", img: "img/tudong.png" },
        { name: "Tủ mát", img: "img/tumat.png" },
        { name: "Bình nóng lạnh", img: "img/binh.png" },
        { name: "Máy rửa chén bát", img: "img/mayruachen.png" },
    ];

    const DM_PRODUCTS = [
        {
            badge: "-13%",
            name: "Tủ lạnh Funiki FR-135CD.1 (135 lít, có đông tuyết)",
            brand: "Funiki",
            img: "img/pro1.png",
            priceNew: "3.490.000 đ",
            priceOld: "3.990.000 đ",
            stars: 5,
            count: 178
        },
        {
            badge: "TẶNG QUÀ",
            name: "Máy giặt Hisense 8kg WTQ8012UT",
            brand: "Hisense",
            img: "img/pro2.png",
            priceNew: "3.290.000 đ",
            priceOld: "3.690.000 đ",
            stars: 4,
            count: 36
        },
        {
            badge: "-20%",
            name: "Máy sấy quần áo Candy CSE-V9DF-S (9kg)",
            brand: "Candy",
            img: "img/pro3.png",
            priceNew: "6.290.000 đ",
            priceOld: "7.900.000 đ",
            stars: 4,
            count: 847
        },
        {
            badge: "",
            name: "Tủ đông 1 ngăn 2 cánh Inverter Sanaky 270 lít",
            brand: "Sanaky",
            img: "img/pro4.png",
            priceNew: "7.490.000 đ",
            stars: 5,
            count: 334
        },
        {
            badge: "-38%",
            name: "Máy lọc không khí LG PuriCare AS60GHWG0",
            brand: "LG",
            img: "img/pro5.png",
            priceNew: "4.990.000 đ",
            priceOld: "7.990.000 đ",
            stars: 4,
            count: 15
        }
    ];

    // hàm render riêng
    function renderDmCategory(cat) {
        return `
      <a class="dm-cat" href="#">
        <img src="${cat.img}" alt="${cat.name}">
        <span>${cat.name}</span>
      </a>`;
    }

    function renderDmProduct(p) {
        const stars = "★".repeat(p.stars) + "☆".repeat(5 - p.stars);
        const badgeHTML = p.badge ? `<span class="badge ${p.badge.includes('%') ? 'badge--sale' : 'badge--gift'}">${p.badge}</span>` : '';
        return `
      <a class="dm-card" href="#">
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

    // gắn DOM (chạy sau khi DOM sẵn sàng)
    function mountDm() {
        const dmSection = document.getElementById("dm-section");
        if (!dmSection) return;

        dmSection.innerHTML = `
      <div class="dm-head">
        <h2>ĐIỆN MÁY - ĐIỆN LẠNH</h2>
        <a class="dm-viewall" href="#">Xem tất cả</a>
      </div>

      <div class="dm-cats">${DM_CATEGORIES.map(renderDmCategory).join("")}</div>

      <div class="dm-subhead">
        <h3>Điện máy nổi bật</h3>
        <a class="dm-btn" href="#">Xem tất cả sản phẩm</a>
      </div>

      <div class="dm-products">${DM_PRODUCTS.map(renderDmProduct).join("")}</div>
    `;
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", mountDm);
    } else {
        mountDm();
    }
})();
