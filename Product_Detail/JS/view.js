// ====== DATA MẪU ======
const VIEWED_ITEMS = [
    {
        id: "sleepmi-z3",
        name: "Máy chống ngáy thông minh Sleepmi Z3",
        img: "https://sieuthiyte.com.vn/data/images/San-Pham/may-chong-ngay-thong-minh-sleepmi-z3-av00.jpg",
        priceNow: 690000,
        priceOld: 1230000,
        discount: 44,
    },
    {
        id: "kakusan-kb213",
        name: "Cây lăn massage mặt & body Kakusan KB-213",
        img: "https://sieuthiyte.com.vn/data/images/San-Pham/cay-lan-massage-mat-va-body-kakusan-kb-213-av1-f1.jpg",
        priceNow: 450000,
        priceOld: 790000,
        discount: 43,
    },
    {
        id: "sbs30307a",
        name: "Cân sức khỏe điện tử SBS-30307A",
        img: "https://sieuthiyte.com.vn/data/product/202111/can-suc-khoe-dien-tu-sbs-30307a-avtbs11635998009.nv.jpg",
        priceNow: 340000,
        priceOld: 610000,
        discount: 44,
    },
    {
        id: "boso-medicus-x",
        name: "Máy đo huyết áp điện tử bắp tay Boso Medicus X",
        img: "https://sieuthiyte.com.vn/data/images/San-Pham/may-do-huyet-ap-dien-tu-bap-tay-boso-medicus-x-avt-08022023.jpg",
        priceNow: 890000,
        priceOld: 1560000,
        discount: 43,
    }
];

// ====== UTILS ======
const VND = n => n.toLocaleString("vi-VN") + " đ";

// ====== RENDER ======
function renderViewedItem(p) {
    return `
    <li class="viewed-item">
      <div class="viewed-thumb">
        <span class="badge-sale">-${p.discount}%</span>
        <img src="${p.img}" alt="${p.name}" loading="lazy">
      </div>
      <div class="viewed-info">
        <a href="#" class="viewed-name">${p.name}</a>
        <div class="viewed-price">
          <span class="price-now">${VND(p.priceNow)}</span>
          <s class="price-old">${VND(p.priceOld)}</s>
        </div>
      </div>
    </li>
  `;
}

function mountViewed(rootId = "viewed-root", limit = 4) {
    const root = document.getElementById(rootId);
    if (!root) return;

    // Lấy tối đa limit sản phẩm đầu tiên
    const limitedItems = VIEWED_ITEMS.slice(0, limit);

    // Gộp HTML
    root.innerHTML = `
    <aside class="viewed-panel viewed--compact" aria-labelledby="viewed-title">
      <div class="viewed-header">
        <h3 id="viewed-title">Sản phẩm khác</h3>
        <a class="btn-view-all" href="#">Xem tất cả</a>
      </div>
      <ul class="viewed-list" role="list">
        ${limitedItems.map(renderViewedItem).join("")}
      </ul>
    </aside>
  `;
}

// ====== Gọi khi trang sẵn sàng ======
mountViewed("viewed-root", 4);
