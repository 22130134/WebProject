<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
        <%@ page contentType="text/html;charset=UTF-8" language="java" %>
            <!doctype html>
            <html lang="vi">

            <head>
                <meta charset="utf-8" />
                <title>MedHome Admin — Sản phẩm</title>
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <link rel="stylesheet" href="${pageContext.request.contextPath}/Admin/admin.css" />
            </head>

            <body>

                <!-- HEADER -->
                <header class="site-header">

                    <button id="btn-toggle" class="hamburger" aria-label="Mở/đóng menu" aria-controls="sidebar"
                        aria-expanded="true">☰</button>

                    <a href="overview.html" class="logo">HKH</a>

                    <form class="searchbar" action="#" role="search">
                        <input type="text" placeholder="Tìm sản phẩm..." />
                        <button type="submit">Tìm</button>
                    </form>

                    <nav class="header-right">
                        <a class="topbtn" href="#" title="Thông báo">🔔</a>
                        <a class="topbtn" href="#" title="Tài khoản">👤</a>
                    </nav>

                </header>

                <!-- LAYOUT -->
                <div class="layout">

                    <!-- SIDEBAR -->
                    <aside id="sidebar" class="sidebar" aria-hidden="false">

                        <div class="sidebar-title">Quản trị</div>

                        <nav class="menu">
                            <a class="menu-item" href="overview.html">🏠 Tổng quan</a>

                            <a class="menu-item active" href="products">🧰 Sản phẩm</a>
                            <a class="menu-item" href="accounts">👥 Tài khoản</a>
                            <a class="menu-item" href="orders.html">🧾 Đơn hàng</a>
                            <a class="menu-item" href="calendar.html">💹 Lịch Khám</a>
                            <a class="menu-item" href="revenue.html">💹 Doanh thu</a>
                            <a class="menu-item" href="settings.html">⚙️ Cài đặt</a>
                            <a class="menu-item danger" href="#">🚪 Đăng xuất</a>

                        </nav>

                    </aside>

                    <!-- CONTENT -->
                    <main class="content">

                        <h2>Quản lý sản phẩm</h2>

                        <!-- BỘ LỌC -->
                        <section class="card" style="padding:12px; margin:10px 0 14px;">

                            <form class="form" action="products" method="get"
                                style="display:grid; grid-template-columns:repeat(auto-fit,minmax(160px,1fr)); gap:10px; align-items:end;">

                                <label>
                                    Tên / Mã
                                    <input class="input" type="text" name="q" value="${msgName}"
                                        placeholder="Ví dụ: HEM-7120, nhiệt kế..." />
                                </label>

                                <label>
                                    Thương hiệu
                                    <select class="input" name="brand">
                                        <option value="">Tất cả</option>
                                        <option ${msgBrand=='Omron' ? 'selected' : '' }>Omron</option>
                                        <option ${msgBrand=='Microlife' ? 'selected' : '' }>Microlife</option>
                                        <option ${msgBrand=='Khác' ? 'selected' : '' }>Khác</option>
                                    </select>
                                </label>

                                <label>
                                    Trạng thái
                                    <select class="input" name="status">
                                        <option value="">Tất cả</option>
                                        <option ${msgStatus=='Còn hàng' ? 'selected' : '' }>Còn hàng</option>
                                        <option ${msgStatus=='Hết hàng' ? 'selected' : '' }>Hết hàng</option>
                                    </select>
                                </label>

                                <label>
                                    Khoảng giá (₫)
                                    <input class="input" type="text" name="price" value="${msgPrice}"
                                        placeholder="vd: 100000-1000000" />
                                </label>

                                <div class="actions" style="margin:0;">
                                    <button class="btn btn-ghost" type="submit">Lọc</button>
                                    <a class="btn btn-ghost" href="products">Reset</a>
                                </div>

                            </form>

                        </section>

                        <!-- ACTIONS -->
                        <div class="actions">
                            <a class="btn" href="#modal-add">+ Thêm sản phẩm</a>
                            <a class="btn btn-ghost" href="#modal-edit">Sửa</a>
                            <a class="btn btn-ghost" href="#modal-hide">Ẩn/Hiện</a>
                            <a class="btn btn-danger" href="#modal-delete">Xóa</a>
                        </div>

                        <!-- BẢNG SẢN PHẨM -->
                        <section class="card">

                            <div class="table-wrap">

                                <table class="table">

                                    <thead>
                                        <tr>
                                            <th><input type="checkbox" aria-label="Chọn tất cả" /></th>
                                            <th>Mã</th>
                                            <th>Hình ảnh</th>
                                            <th>Tên</th>
                                            <th>Thương hiệu</th>
                                            <th>Giá (₫)</th>
                                            <th>Tồn kho</th>
                                            <th>Trạng thái</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${listP}" var="p">
                                            <tr>
                                                <td><input type="checkbox" aria-label="Chọn" /></td>
                                                <td>SP${p.id}</td>
                                                <td>
                                                    <img src="${p.img}" alt=""
                                                        style="width:40px; height:40px; object-fit:contain; border:1px solid #eee; border-radius:4px;">
                                                </td>
                                                <td>${p.name}</td>
                                                <td>${p.brand}</td>
                                                <td>
                                                    <fmt:formatNumber value="${p.price}" type="currency"
                                                        currencySymbol="" />
                                                </td>
                                                <td>${p.stock}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${p.stock > 0}">
                                                            <span class="badge ok">Còn hàng</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge danger">Hết hàng</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>

                                </table>

                            </div>

                        </section>

                        <footer class="foot">© 2025 MedHome Admin</footer>

                    </main>

                </div>

                <!-- MODALS CRUD -->

                <!-- THÊM -->
                <div id="modal-add" class="modal">
                    <a href="#" class="modal-overlay" aria-label="Đóng"></a>

                    <div class="modal-body">

                        <h3>Thêm sản phẩm</h3>

                        <form class="form" action="#" method="post">

                            <label>Tên
                                <input class="input" name="name" required />
                            </label>

                            <label>Hình ảnh (URL)
                                <input class="input" name="img" placeholder="http://..." />
                            </label>

                            <label>Thương hiệu
                                <input class="input" name="brand" />
                            </label>

                            <label>Giá (₫)
                                <input class="input" type="number" name="price" min="0" step="1000" required />
                            </label>

                            <label>Tồn kho
                                <input class="input" type="number" name="stock" min="0" required />
                            </label>

                            <label>Mô tả chi tiết
                                <textarea class="input" name="description" rows="3"></textarea>
                            </label>

                            <div class="actions">
                                <a class="btn btn-ghost" href="#">Hủy</a>
                                <button class="btn" type="submit">Lưu</button>
                            </div>

                        </form>

                    </div>

                </div>

                <!-- SỬA (Example structure, logic needs JS) -->
                <div id="modal-edit" class="modal">
                    <a href="#" class="modal-overlay" aria-label="Đóng"></a>
                    <div class="modal-body">
                        <h3>Sửa sản phẩm</h3>
                        <!-- Placeholder form -->
                        <p>Chức năng đang được cập nhật...</p>
                        <div class="actions">
                            <a class="btn btn-ghost" href="#">Đóng</a>
                        </div>
                    </div>
                </div>

                <!-- ẨN / HIỆN -->
                <div id="modal-hide" class="modal modal-sm">
                    <a href="#" class="modal-overlay" aria-label="Đóng"></a>
                    <div class="modal-body">
                        <h3>Ẩn/Hiện sản phẩm?</h3>
                        <p>Chức năng đang được cập nhật...</p>
                        <div class="actions">
                            <a class="btn btn-ghost" href="#">Hủy</a>
                        </div>
                    </div>
                </div>

                <!-- XÓA -->
                <div id="modal-delete" class="modal modal-sm">
                    <a href="#" class="modal-overlay" aria-label="Đóng"></a>
                    <div class="modal-body">
                        <h3>Xóa sản phẩm?</h3>
                        <p>Chức năng đang được cập nhật...</p>
                        <div class="actions">
                            <a class="btn btn-ghost" href="#">Hủy</a>
                        </div>
                    </div>
                </div>

                <script src="${pageContext.request.contextPath}/Admin/app.js"></script>

            </body>

            </html>