// DATA mẫu (đổi ảnh/giảm giá theo bạn)
const productTypes = [
    { name: "Máy chống ngáy ", discount: "-44%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/may-chong-ngay-thong-minh-sleepmi-z3-av00.jpg" },
    { name: "Cây lăn massage", discount: "-43%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/cay-lan-massage-mat-va-body-kakusan-kb-213-av1-f1.jpg" },
    { name: "Cân sức khỏe", discount: "-44%", image: "https://sieuthiyte.com.vn/data/product/202111/can-suc-khoe-dien-tu-sbs-30307a-avtbs11635998009.nv.jpg" },
    { name: "Máy đo huyết áp", discount: "-43%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/may-do-huyet-ap-dien-tu-bap-tay-boso-medicus-x-avt-08022023.jpg" },
    { name: "Máy tạo oxy", discount: "-44%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/may-tao-oxy-xach-tay-dynmed-poc5-avt-18072025.jpg" },
    { name: "Máy hút mụn", discount: "-42%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/may-hut-mun-dau-den-2-trong-1-reiwa-av1.jpg" },
    { name: "Máy massage", discount: "-44%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/may-massage-ho-tro-dieu-tri-dau-lung-wellme-hz-ybb-1-av1-1.jpg" },
    { name: "Que thử đường", discount: "-43%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/que-thu-may-do-duong-huyet-sapphire-plus-10-que-av1-1.jpg" },
    { name: "Máy xông khí ", discount: "-38%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/may-xong-khi-dung-dang-luoi-wellmed-air-pro-ii-av6-1.jpg" },
    { name: "Đai lưng cột sống", discount: "-44%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/dai-lung-cot-song-cpo-6223-av1.jpg" },
    { name: "Đai bảo vệ", discount: "-44%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/dai-bao-ve-dau-goi-cpo-6601-av5.jpg" },
    { name: "Nẹp gối", discount: "-37%", image: "https://sieuthiyte.com.vn/assets/uploads/kn096-min1566795564.nv.png" },
    { name: "Máy bơm nước", discount: "-44%", image: "img/maybom.png" },
    { name: "Loa nghe nhạc", discount: "-44%", image: "img/loa.png" },
    { name: "Các loại bếp", discount: "-44%", image: "img/bep.png" },
    { name: "Thang nhôm", discount: "-43%", image: "img/thang.png" }
];

const grid = document.getElementById("product-type-grid");
const btnToggle = document.getElementById("toggle-more");
// Render cards
function render() {
    grid.innerHTML = "";
    productTypes.forEach(p => {
        const card = document.createElement("div");
        card.className = "product-type-card";

        const badge = document.createElement("div");
        badge.className = "product-type-badge";
        badge.textContent = p.discount;

        const thumb = document.createElement("div");
        thumb.className = "product-type-thumb";
        const img = document.createElement("img");
        img.src = p.image; img.alt = p.name;
        thumb.appendChild(img);

        const name = document.createElement("div");
        name.className = "product-type-name";
        name.textContent = p.name;

        card.append(badge, thumb, name);
        grid.appendChild(card);
    });
}

// Lấy số cột thật sự từ CSS (nếu bạn đổi CSS từ 6 sang số khác vẫn đúng)
function getColumnCount() {
    const style = getComputedStyle(grid);
    return style.gridTemplateColumns.split(" ").length || 6;
}
// Hiển thị đúng 2 dòng, ẩn phần còn lại
const VISIBLE_ROWS = 2;
function applyClamp(collapsed) {
    const cols = getColumnCount();
    const showCount = collapsed ? cols * VISIBLE_ROWS : Infinity;
    const cards = [...grid.children];
    cards.forEach((el, i) => {
        if (i >= showCount) el.classList.add("is-hidden");
        else el.classList.remove("is-hidden");
    });
    btnToggle.textContent = collapsed ? "Xem thêm chuyên mục" : "Thu gọn";
    btnToggle.setAttribute("aria-expanded", String(!collapsed));
}

// Re-apply khi thay số cột (nếu bạn sửa CSS hoặc viewport thay đổi)
let resizeTimer;
function onResize() {
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(() => {
        const collapsed = btnToggle.getAttribute("aria-expanded") === "false";
        applyClamp(collapsed);
    }, 120);
}

btnToggle.addEventListener("click", () => {
    const expanded = btnToggle.getAttribute("aria-expanded") === "true";
    applyClamp(expanded); // nếu đang mở → thu; đang thu → mở
});

render();
applyClamp(true);
window.addEventListener("resize", onResize);
