// Trang giỏ hàng: render từ CartStore (đã được header.js khai báo)
(function(){
    function qs(sel){ return document.querySelector(sel); }
    function qsa(sel){ return Array.from(document.querySelectorAll(sel)); }

    function render(){
        const tbody = qs('#cart-rows');
        const items = (window.CartStore?.get() || []);
        if (!tbody) return;

        if (!items.length){
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

    function bindRowEvents(){
        qsa('#cart-rows tr').forEach(tr => {
            const id = tr.getAttribute('data-id');
            tr.querySelector('.btn-dec').addEventListener('click', () => changeQty(id, -1, tr));
            tr.querySelector('.btn-inc').addEventListener('click', () => changeQty(id, +1, tr));
            tr.querySelector('.qty-input').addEventListener('change', (e) => setQty(id, +e.target.value, tr));
            tr.querySelector('.remove-btn').addEventListener('click', () => {
                CartStore.remove(id); render(); // re-render
                // cập nhật lại mini-cart (nếu trang vẫn có header)
                if (window.setupStickyHeader) window.setupStickyHeader();
            });
        });
    }

    function changeQty(id, delta, tr){
        const input = tr.querySelector('.qty-input');
        const newVal = Math.max(1, (+input.value || 1) + delta);
        input.value = newVal;
        setQty(id, newVal, tr);
    }

    function setQty(id, qty, tr){
        CartStore.updateQty(id, qty);
        const items = CartStore.get();
        const item = items.find(p => p.id === id);
        tr.querySelector('.line-total').textContent = CartStore.format(item.price * item.qty);
        updateSummary();
        if (window.setupStickyHeader) window.setupStickyHeader();
    }

    function updateSummary(){
        qs('#sum-qty').textContent = CartStore.totalQty();
        qs('#sum-price').textContent = CartStore.format(CartStore.totalPrice());
    }

    // demo: click mua (ở đây chỉ alert)
    document.addEventListener('click', (e) => {
        if (e.target.id === 'btn-checkout'){
            e.preventDefault();
            alert('Demo: Tiến hành đặt hàng (bạn có thể nối API sau).');
        }
    });

    // Khởi tạo
    document.addEventListener('DOMContentLoaded', render);
})();
