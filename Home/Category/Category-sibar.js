// Home/Category/categories.js
(() => {
    // DATA
    const categories = [
        { name: "Combo khuyến mãi",  icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Qua-tang-suc-khoe.png" },
        { name: "Y tế gia đình",     icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Y-te-gia-dinh.png" },
        { name: "Y tế chuyên dụng",  icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Y-te-chuyen-dung.png"},
        { name: "Chăm sóc sắc đẹp",  icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Cham-soc-sac-dep.png" },
        { name: "Chăm sóc sức khỏe", icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Cham-soc-suc-khoe.png" },
        { name: "Thiết bị gia đình", icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Thiet-bi-gia-dinh.png" },
        { name: "Đồ dùng mẹ và bé",  icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Do-dung-me-be.png" },
        { name: "Đồ thể thao",       icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Do-the-thao.png" },
        { name: "Quà tặng",          icon: "https://sieuthiyte.com.vn/data/images/San-Pham/icon-qua-tang-phong-thuy-70x70.png" },
        { name: "Chăm sóc sức khỏe", icon: "https://sieuthiyte.com.vn/data/images/icon-menu-mobile/icon-new-2-1/Cham-soc-suc-khoe.png" },

    ];

    // Util: bỏ dấu + tạo slug
    const slugify = s => s.toLowerCase()
        .normalize('NFD').replace(/[\u0300-\u036f]/g, '')
        .replace(/[^a-z0-9]+/g, '-').replace(/^-+|-+$/g, '');

    function renderCategories() {
        const container = document.getElementById("category-list");
        if (!container) return;

        container.innerHTML = "";
        categories.forEach(cat => {
            const a = document.createElement("a");
            a.className = "category-link";

            // ====== QUAN TRỌNG: đường dẫn tới Catalog ======
            // Nếu catalog.html nằm trong thư mục Catalog (WebProject/Catalog/catalog.html)
            a.href = `Catalog/catalog.html#${slugify(cat.name)}`;

            // Nếu catalog.html nằm ở gốc WebProject thì dùng:
            // a.href = `catalog.html#${slugify(cat.name)}`;

            const div = document.createElement("div");
            div.className = "category-item";
            div.innerHTML = `
        <img src="${cat.icon}" alt="${cat.name}">
        <span>${cat.name}</span>
      `;
            a.appendChild(div);
            container.appendChild(a);
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", renderCategories, { once: true });
    } else {
        renderCategories();
    }
})();
