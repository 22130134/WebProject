// DATA mẫu (đổi ảnh/giảm giá theo bạn)
const productTypes = [
    { name: "Tivi", discount: "-44%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/may-chong-ngay-thong-minh-sleepmi-z3-av00.jpg" },
    { name: "Tủ lạnh", discount: "-43%", image: "https://sieuthiyte.com.vn/data/images/San-Pham/cay-lan-massage-mat-va-body-kakusan-kb-213-av1-f1.jpg" },
    { name: "Điều hòa", discount: "-44%", image: "img/dieuhoa.png" },
    { name: "Máy giặt", discount: "-43%", image: "img/maygiat.png" },
    { name: "Quạt, Máy làm mát", discount: "-44%", image: "img/maylammat.png" },
    { name: "Máy pha cà phê", discount: "-42%", image: "img/mayphacaphe.png" },
    { name: "Máy hút bụi", discount: "-44%", image: "img/mayhutbui.png" },
    { name: "Nồi cơm điện", discount: "-43%", image: "img/noicom.png" },
    { name: "Tủ đông", discount: "-38%", image: "img/tudong.png" },
    { name: "Máy xay đa năng", discount: "-44%", image: "img/mayxay.png" },
    { name: "Máy massage", discount: "-44%", image: "img/massage.png" },
    { name: "Tủ mát", discount: "-37%", image: "img/tumat.png" },
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
