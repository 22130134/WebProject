// ===== Header + CartStore (dùng chung cho mọi trang) =====
(function () {
    // ---- 0) CartStore: quản lý localStorage ----
    const KEY = 'cart';
    const format = n => n.toLocaleString('vi-VN') + 'đ';

    function read() {
        try { return JSON.parse(localStorage.getItem(KEY)) || []; }
        catch { return []; }
    }
    function write(data) { localStorage.setItem(KEY, JSON.stringify(data)); }

    // Seed 2 sản phẩm nếu giỏ đang trống (để bạn xem UI ngay)
    const seedIfEmpty = () => {
        const cur = read();
        if (cur.length) return;
        write([
            {
                id: 'bp-01',
                name: 'Máy đo huyết áp cổ tay',
                price: 650000,
                qty: 1,
                img: 'https://via.placeholder.com/200x200.png?text=Huyet+ap'
            },
            {
                id: 'ox-01',
                name: 'Máy tạo oxy mini',
                price: 2590000,
                qty: 1,
                img: 'https://via.placeholder.com/200x200.png?text=Oxy+mini'
            }
        ]);
    };

    // public API
    const CartStore = {
        get: read,
        set: write,
        clear() { write([]); },
        add(item) {
            const list = read();
            const idx = list.findIndex(p => p.id === item.id);
            if (idx >= 0) list[idx].qty += item.qty || 1;
            else list.push({ qty: 1, ...item });
            write(list);
        },
        remove(id) {
            write(read().filter(p => p.id !== id));
        },
        updateQty(id, qty) {
            const list = read().map(p =>
                p.id === id ? { ...p, qty: Math.max(1, qty) } : p
            );
            write(list);
        },
        totalQty() { return read().reduce((s, p) => s + p.qty, 0); },
        totalPrice() { return read().reduce((s, p) => s + p.qty * p.price, 0); },
        format
    };
    window.CartStore = CartStore;

    // ---- 1) Sticky header ----
    function setupStickyHeader() {
        const header = document.querySelector('.site-header');
        if (!header) return;
        const updateHeader = () => {
            const removePadding = (window.scrollY || document.documentElement.scrollTop) > 50;
            header.classList.toggle('no-padding', removePadding);
        };
        window.addEventListener('scroll', updateHeader, { passive: true });
        updateHeader();
    }

    // ---- 2) Mini-cart render + badge ----
    function renderMiniCart() {
        seedIfEmpty(); // chỉ seed lần đầu
        const listEl = document.getElementById('mini-cart-list');
        const totalEl = document.getElementById('mini-cart-total');
        const badge = document.querySelector('.cart-badge');
        if (!listEl || !totalEl || !badge) return;

        const items = CartStore.get();
        badge.textContent = CartStore.totalQty();

        if (!items.length) {
            listEl.innerHTML = `<li class="mini-cart-item" style="justify-content:center">Giỏ hàng trống</li>`;
            totalEl.textContent = '0đ';
            return;
        }

        listEl.innerHTML = items.map(p => `
            <li class="mini-cart-item">
                <img class="mini-cart-thumb" src="${p.img}" alt="${p.name}">
                <div>
                    <div class="mini-cart-title">${p.name}</div>
                    <div class="mini-cart-meta">Số lượng: ${p.qty}</div>
                </div>
                <div class="mini-cart-price">${CartStore.format(p.price * p.qty)}</div>
            </li>
        `).join('');

        totalEl.textContent = CartStore.format(CartStore.totalPrice());
    }

    // ---- 3) Hàm init dùng chung + export cho module khác nếu cần ----
    function initHeader() {
        setupStickyHeader();
        renderMiniCart();
    }

    // Cho header.js (hoặc file khác) gọi sau khi chèn HTML header xong
    window.setupStickyHeader = initHeader;

    // Nếu header HTML đã có sẵn trong trang, tự init luôn sau khi DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initHeader);
    } else {
        initHeader();
    }
})();
