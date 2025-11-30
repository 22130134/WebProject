// Trang giỏ hàng: demo 2 sản phẩm có sẵn
(function () {
    // ==== 0) Tạo CartStore đơn giản nếu chưa có ====
    if (!window.CartStore) {
        const format = n => n.toLocaleString('vi-VN') + 'đ';

        let list = [
            {
                id: 'bp-01',
                name: 'Máy đo huyết áp cổ tay',
                price: 650000,
                qty: 1,
                img: 'https://sieuthiyte.com.vn/data/images/San-Pham/may-do-huyet-ap-dien-tu-bap-tay-boso-medicus-x-avt-08022023.jpg'
            },
            {
                id: 'ox-01',
                name: 'Máy tạo oxy mini',
                price: 2590000,
                qty: 1,
                img: 'https://sieuthiyte.com.vn/data/images/San-Pham/may-do-huyet-ap-dien-tu-bap-tay-boso-medicus-x-avt-08022023.jpg'
            }
        ];

        window.CartStore = {
            get() { return list; },
            set(arr) { list = arr; },
            remove(id) { list = list.filter(p => p.id !== id); },
            updateQty(id, qty) {
                list = list.map(p => p.id === id ? { ...p, qty: Math.max(1, qty) } : p);
            },
            totalQty() { return list.reduce((s, p) => s + p.qty, 0); },
            totalPrice() { return list.reduce((s, p) => s + p.qty * p.price, 0); },
            format
        };
    }

    // ====== 1) Tiện ích DOM ======
    function qs(sel) { return document.querySelector(sel); }
    function qsa(sel) { return Array.from(document.querySelectorAll(sel)); }

    // ====== 2) Render bảng giỏ hàng ======
    function render() {
        const tbody = qs('#cart-rows');
        const items = (window.CartStore?.get() || []);
        if (!tbody) return;

        if (!items.length) {
            tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;padding:24px">Giỏ hàng trống</td></tr>`;
            updateSummary();
            return;
        }

        tbody.innerHTML = items.map(p => `
          <tr data-id="${p.id}">
            <td>
              <div class="cart-item">
                <img class="cart-thumb" src="${p.img}" alt="${p.name}">
                <div class="cart-name">${p.name}</div>
              </div>
            </td>
            <td>${CartStore.format(p.price)}</td>
            <td>
              <div class="qty-box">
                <button class="btn-dec" aria-label="Giảm">−</button>
                <input class="qty-input" type="number" min="1" value="${p.qty}">
                <button class="btn-inc" aria-label="Tăng">+</button>
              </div>
            </td>
            <td class="line-total">${CartStore.format(p.price * p.qty)}</td>
            <td><button class="remove-btn" title="Xóa"><i class="fa-solid fa-trash"></i></button></td>
          </tr>
        `).join('');

        bindRowEvents();
        updateSummary();
    }

    // ====== 3) Gắn sự kiện tăng/giảm/xóa ======
    function bindRowEvents() {
        qsa('#cart-rows tr').forEach(tr => {
            const id = tr.getAttribute('data-id');
            tr.querySelector('.btn-dec').addEventListener('click', () => changeQty(id, -1, tr));
            tr.querySelector('.btn-inc').addEventListener('click', () => changeQty(id, +1, tr));
            tr.querySelector('.qty-input').addEventListener('change', (e) => setQty(id, +e.target.value, tr));
            tr.querySelector('.remove-btn').addEventListener('click', () => {
                CartStore.remove(id);
                render(); // re-render sau khi xóa
            });
        });
    }

    function changeQty(id, delta, tr) {
        const input = tr.querySelector('.qty-input');
        const newVal = Math.max(1, (+input.value || 1) + delta);
        input.value = newVal;
        setQty(id, newVal, tr);
    }

    function setQty(id, qty, tr) {
        CartStore.updateQty(id, qty);
        const items = CartStore.get();
        const item = items.find(p => p.id === id);
        tr.querySelector('.line-total').textContent = CartStore.format(item.price * item.qty);
        updateSummary();
    }

    // ====== 4) Cập nhật tổng bên phải ======
    function updateSummary() {
        qs('#sum-qty').textContent = CartStore.totalQty();
        qs('#sum-price').textContent = CartStore.format(CartStore.totalPrice());
    }

    // Demo nút “Tiến hành đặt hàng”
    document.addEventListener('click', (e) => {
        if (e.target.id === 'btn-checkout') {
            e.preventDefault();
            window.location.href = '../Checkout/checkout.html';
            // hoặc 'checkout.html' tùy cấu trúc thư mục của bạn
        }
    });


    // ====== 5) Khởi tạo ======
    document.addEventListener('DOMContentLoaded', render);
})();
