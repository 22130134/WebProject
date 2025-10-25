const categories = [
    { name: "Combo khuyến mãi",      icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Qua-tang-suc-khoe.png" },
    { name: "Y tế gia đình",         icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Y-te-gia-dinh.png" },
    { name: "Y tế chuyên dụng",      icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Y-te-chuyen-dung.png"},
    { name: "Chăm sóc sắc đẹp",      icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Cham-soc-sac-dep.png" },
    { name: "Chăm sóc sức khỏe",     icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Cham-soc-suc-khoe.png" },
    { name: "Thiết bị gia đình",     icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Thiet-bi-gia-dinh.png" },
    { name: "Đồ dùng mẹ và bé",      icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Do-dung-me-be.png" },
    { name: "Đồ thể thao",           icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Do-the-thao.png" },
    { name: "Quà tặng",              icon: "https://sieuthiyte.com.vn/data/images/San-Pham/icon-qua-tang-phong-thuy-70x70.png" },
    { name: "Flash Sale",            icon: "https://sieuthiyte.com.vn/themes/images/flash-sale-styt.png?t=1", promo: true }
];


const container = document.getElementById("category-list");

function renderCategories() {
    categories.forEach(cat => {
        const div = document.createElement("div");
        div.className = "category-item" + (cat.all ? " all" : "");

        const img = document.createElement("img");
        img.src = cat.icon;
        img.alt = cat.name;

        const text = document.createElement("span");
        text.textContent = cat.name;

        // Thêm dấu ">" bên phải
        const chev = document.createElement("span");
        chev.className = "chevron";
        chev.textContent = ">";

        // Tạo bảng trắng (submenu panel)
        const panel = document.createElement("div");
        panel.className = "submenu-panel";
        panel.innerHTML = `
      <div style="padding:16px; color:#999; font-size:14px;">
        (Chưa có dữ liệu)
      </div>
    `;

        // Gắn các phần tử vào .category-item
        div.appendChild(img);
        div.appendChild(text);
        div.appendChild(chev);
        div.appendChild(panel);

        // Tạm thời giữ sự kiện click nếu cần
        div.addEventListener("click", () => {
            console.log("Clicked:", cat.name);
        });

        container.appendChild(div);
    });
}

renderCategories();
