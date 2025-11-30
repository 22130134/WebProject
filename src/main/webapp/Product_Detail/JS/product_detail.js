document.addEventListener("DOMContentLoaded", () => {
    // Logic cho nút tăng giảm số lượng
    const qtyInput = document.getElementById("qtyInput");
    const btnMinus = document.querySelector(".qty-minus");
    const btnPlus = document.querySelector(".qty-plus");

    if (btnMinus && btnPlus && qtyInput) {
        btnMinus.addEventListener("click", () => {
            let val = parseInt(qtyInput.value) || 1;
            if (val > 1) {
                qtyInput.value = val - 1;
            }
        });

        btnPlus.addEventListener("click", () => {
            let val = parseInt(qtyInput.value) || 1;
            qtyInput.value = val + 1;
        });
    }

    // Logic cho gallery ảnh (nếu chưa có)
    const thumbs = document.querySelectorAll(".thumb");
    const mainImg = document.getElementById("mainImg");
    if (thumbs.length > 0 && mainImg) {
        thumbs.forEach(thumb => {
            thumb.addEventListener("click", () => {
                // Xóa active cũ
                document.querySelector(".thumb.is-active")?.classList.remove("is-active");
                // Thêm active mới
                thumb.classList.add("is-active");
                // Đổi ảnh chính
                const img = thumb.querySelector("img");
                if (img) {
                    mainImg.src = img.src;
                }
            });
        });
    }
});
