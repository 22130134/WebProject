document.addEventListener("DOMContentLoaded", () => {
    const container = document.querySelector(".product-info");
    if (!container) return;

    const html = `
  <article class="product-card">
    <header class="hero">
      <h1>Máy sấy thông hơi Whirlpool 3LWED4815FW0 — 15kg</h1>
      <p class="lead">
        Dòng máy sấy thông hơi hiện đại, cao cấp của Whirlpool (Mỹ), ra mắt năm 2017. 
        Công suất 4.725W, khối lượng sấy tối đa 15kg — phù hợp gia đình từ 7 người trở lên.
      </p>
    </header>

    <!-- Chỉ khối này sẽ bị thu gọn -->
    <div id="descWrap" class="collapse-wrap">
      <section class="intro">
        <p>
          Bên cạnh thiết kế tinh tế, máy còn ghi điểm với loạt công nghệ thuận tiện và bền bỉ. 
          Dưới đây là thông tin chi tiết để bạn tham khảo.
        </p>
      </section>

      <section class="specs">
        <h2>Kích thước của máy sấy Whirlpool 3LWED4815FW0</h2>
        <ul>
          <li><strong>Kích thước:</strong> Đang cập nhật</li>
          <li><strong>Khối lượng sấy:</strong> 15kg</li>
          <li><strong>Công suất danh định:</strong> 4.725W</li>
          <li><strong>Công nghệ sấy:</strong> Thông hơi</li>
          <li><strong>Nhiệt độ sấy tối đa:</strong> tới ~70°C</li>
          <li><strong>Chất liệu lồng sấy:</strong> Sứ Porcelain bền bỉ</li>
          <li><strong>Chương trình sấy:</strong> 14 chương trình</li>
          <li><strong>Bảng điều khiển:</strong> Núm vặn (3 núm xoay)</li>
          <li><strong>Xuất xứ:</strong> Nhập khẩu nguyên chiếc từ Mỹ</li>
        </ul>
      </section>

      <section class="highlights">
        <h2>Ưu điểm nổi bật</h2>
        <ul>
          <li>Thiết kế hiện đại, dễ kê đặt trong nhiều không gian.</li>
          <li>Khối lượng sấy 15kg — phù hợp gia đình ≥ 7 thành viên.</li>
          <li>Công nghệ sấy thông hơi + công suất 4.725W cho tốc độ sấy nhanh, hiệu quả.</li>
          <li>14 chương trình sấy đa dạng cho nhiều chất liệu vải.</li>
          <li>Bảng điều khiển dạng núm vặn, thao tác đơn giản.</li>
          <li>Lồng sấy sứ Porcelain cứng cáp, chống bám bẩn, dễ vệ sinh.</li>
          <li>Có chống nhăn, hẹn giờ, tùy chỉnh thời gian và nhiệt độ sấy.</li>
        </ul>
      </section>
      <img src="https://sieuthiyte.com.vn/data/images/San-Pham/que-thu-may-do-duong-huyet-sapphire-plus-10-que-nd1.jpg" alt="Máy sấy Whirlpool 3LWED4815FW0">

      <section>
        <h2>Đánh giá chi tiết</h2>
        <h3>Công nghệ sấy thông hơi hiện đại</h3>
        <p>Quạt gió thổi không khí nóng vào buồng sấy, kết hợp công suất 4.725W…</p>
        <p>Nhiệt độ sấy có thể đạt tới ~70°C…</p>
        <h3>Công nghệ chống nhăn</h3>
        <p>Tính năng chống nhăn giúp giảm nhăn…</p>
        <h3>Tùy chỉnh thời gian và nhiệt độ</h3>
        <p>Tự chọn thời gian/nhiệt độ…</p>
        <h3>14 chương trình sấy</h3>
        <p>Từ sấy nhanh…</p>
        <h3>Khối lượng sấy 15kg</h3>
        <p>Phù hợp hộ gia đình đông người…</p>
        <h3>Lồng sấy sứ Porcelain</h3>
        <p>Bền bỉ, chịu lực tốt…</p>
        <h3>Bảng điều khiển núm vặn</h3>
        <p>3 núm xoay trực quan…</p>
        <h3>Thiết kế màu trắng sang trọng</h3>
        <p>Vỏ kim loại sơn tĩnh điện…</p>
      </section>

      <section class="notice">
        <h2>Lưu ý lắp đặt & vận hành</h2>
        <ul>
          <li>Sản phẩm <strong>chưa bao gồm</strong> ống thoát hơi, aptomat, dây điện nguồn.</li>
          <li>Không cắm trực tiếp 220V…</li>
          <li>Nên lắp ống thoát hơi đúng kỹ thuật…</li>
          <li>Hình ảnh chỉ mang tính minh họa…</li>
        </ul>
      </section>
    </div>

    <!-- Nút đặt ngoài khối bị cắt, luôn hiển thị -->
    <div class="toggle-btn-wrap">
      <button id="toggleDesc" class="toggle-btn">Xem tất cả ▼</button>
    </div>

 
  </article>
  `;

    container.innerHTML = html;

    // Logic toggle
    const wrap = container.querySelector("#descWrap");
    const btn  = container.querySelector("#toggleDesc");
    let expanded = false;

    // trạng thái mặc định: thu gọn
    setCollapsed(false);

    btn.addEventListener("click", () => {
        expanded = !expanded;
        setCollapsed(expanded);
    });

    // Cập nhật maxHeight khi đổi kích thước cửa sổ để mở ra vừa đủ
    window.addEventListener("resize", () => {
        if (expanded) wrap.style.maxHeight = wrap.scrollHeight + "px";
    });

    function setCollapsed(open) {
        if (open) {
            wrap.classList.add("expanded");
            wrap.style.maxHeight = wrap.scrollHeight + "px";
            btn.textContent = "Thu gọn ▲";
        } else {
            wrap.classList.remove("expanded");
            wrap.style.maxHeight = "300px"; // chiều cao bạn muốn khi thu gọn
            btn.textContent = "Xem tất cả ▼";
        }
    }
});
