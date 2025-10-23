const categories = [
    { name: "Combo khuyến mãi",      icon: "https://cdn-icons-png.flaticon.com/128/2542/2542350.png" },
    { name: "Y tế gia đình",         icon: "https://cdn-icons-png.flaticon.com/128/2966/2966480.png" },
    { name: "Y tế chuyên dụng",      icon: "https://cdn-icons-png.flaticon.com/128/2966/2966482.png" },
    { name: "Chăm sóc sắc đẹp",      icon: "https://cdn-icons-png.flaticon.com/128/706/706195.png" },
    { name: "Chăm sóc sức khỏe",     icon: "https://cdn-icons-png.flaticon.com/128/2965/2965567.png" },
    { name: "Thiết bị gia đình",     icon: "https://cdn-icons-png.flaticon.com/128/599/599502.png" },
    { name: "Đồ dùng mẹ và bé",      icon: "https://cdn-icons-png.flaticon.com/128/2942/2942789.png" },
    { name: "Đồ thể thao",           icon: "https://cdn-icons-png.flaticon.com/128/1041/1041928.png" },
    { name: "Quà tặng",              icon: "https://cdn-icons-png.flaticon.com/128/891/891462.png" },
    { name: "Flash Sale",            icon: "https://cdn-icons-png.flaticon.com/128/991/991952.png", promo: true }
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

        div.appendChild(img);
        div.appendChild(text);

        div.addEventListener("click", () => {
            console.log("Clicked:", cat.name);
        });

        container.appendChild(div);
    });
}

renderCategories();
