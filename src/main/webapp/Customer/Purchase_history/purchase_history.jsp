<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- Lấy đối tượng User và Customer từ Session --%>
<c:set var="user" value="${sessionScope.auth}"/>
<c:set var="customer" value="${sessionScope.customer}"/>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Lịch sử mua hàng</title>

    <%-- SỬA ĐƯỜNG DẪN CSS TUYỆT ĐỐI (Dùng contextPath) --%>
    <%-- LƯU Ý: Nếu CSS của bạn nằm ở thư mục "purchase_history.css" thì cần sửa tên folder --%>
    <%-- Tôi giả định bạn đã đổi tên folder chứa CSS thành "purchasehistory" để tránh nhầm lẫn --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Customer/Purchase_history/purchase_history.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header/header.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Customer/User_sidebar/user_sidebar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer/footer.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <style>
        /* CSS BỔ SUNG CHO CÁC MỤC ĐƠN HÀNG */
        .order-card {
            border: 1px solid #ddd;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
            background: white;
        }

        .order-header {
            display: flex;
            justify-content: space-between;
            background: #f9f9f9;
            padding: 10px;
            border-bottom: 1px solid #eee;
            margin-bottom: 10px;
        }

        .order-item {
            display: flex;
            gap: 15px;
            margin-bottom: 10px;
            align-items: center;
            border-bottom: 1px dashed #eee;
            padding-bottom: 10px;
        }

        .item-img {
            width: 70px;
            height: 70px;
            object-fit: cover;
            border: 1px solid #eee;
        }

        /* Màu trạng thái */
        .status-Pending {
            color: #f39c12;
            font-weight: bold;
        }

        .status-Completed {
            color: #2ecc71;
            font-weight: bold;
        }

        .status-Cancelled {
            color: #e74c3c;
            font-weight: bold;
        }

        .status-Shipping {
            color: #3498db;
            font-weight: bold;
        }

        .item-info {
            flex-grow: 1;
        }
    </style>
</head>

<body>
<jsp:include page="/style/header/header.jsp"/>
<%-- Dùng jsp:include để nhúng header.jsp --%>

<div class="user-container">

    <jsp:include page="/Customer/User_sidebar/user_sidebar.jsp"/>
    <%-- Dùng jsp:include để nhúng sidebar.jsp --%>

    <main class="content">
        <section class="order-history">
            <h2 style="padding-bottom: 10px;">Lịch sử đơn hàng</h2>

            <div class="tabs">
                <button class="tab active" onclick="filterOrders('all', this)">Tất cả</button>
                <button class="tab" onclick="filterOrders('Pending', this)">Chờ xác nhận</button>
                <button class="tab" onclick="filterOrders('Processing', this)">Đã xác nhận</button>
                <button class="tab" onclick="filterOrders('Shipping', this)">Đang giao</button>
                <button class="tab" onclick="filterOrders('Completed', this)">Đã giao</button>
                <button class="tab" onclick="filterOrders('Cancelled', this)">Đã hủy</button>
            </div>

            <div class="search-box">
                <input type="text" placeholder="Tìm theo mã đơn hàng, tên sản phẩm..."/>
                <button class="search-btn"><i class="fa-solid fa-magnifying-glass"></i></button>
            </div>

            <c:choose>
                <%-- 1. Trường hợp: Không có đơn hàng nào --%>
                <c:when test="${empty orders}">
                    <div class="empty-state">
                        <img src="https://cdn-icons-png.flaticon.com/512/1170/1170678.png" alt="No Orders"/>
                        <p>Chưa có đơn hàng nào</p>
                    </div>
                </c:when>

                <%-- 2. Trường hợp: Có đơn hàng (Lặp qua List<Order>) --%>
                <c:otherwise>
                    <c:forEach var="order" items="${orders}">
                        <div class="order-card">
                            <div class="order-header">
                                <div>
                                    <b>Đơn hàng #${order.orderId}</b>
                                    <span style="font-size: 0.9em; color: #666; margin-left: 10px;">
                                            <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
                                        </span>
                                </div>
                                <div class="status-${order.status}">
                                        ${order.status}
                                </div>
                            </div>

                            <c:forEach var="item" items="${orderItemsMap[order.orderId]}">

                                <%-- Lấy Product từ Map dựa vào ProductID --%>
                                <c:set var="product" value="${productsMap[item.productId]}"/>

                                <div class="order-item">
                                    <img src="${product.img}" alt="${product.name}" class="item-img"
                                         onerror="this.src='https://placehold.co/70x70?text=No+Image'">

                                    <div class="item-info">
                                        <div><b>${product.name}</b></div>
                                        <div>
                                            SL: ${item.quantity} x
                                            <span style="color: #d70018;">
                                                    <fmt:formatNumber value="${item.priceAtOrder}" type="currency"
                                                                      currencySymbol="₫"/>
                                                </span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <div style="display: flex; justify-content: space-between; align-items: flex-end; margin-top: 10px; padding-top: 10px; border-top: 1px solid #eee;">
                                <div style="font-size: 0.9em; color: #555;">
                                    <i class="fa-solid fa-location-dot"></i> ${order.shippingAddress}
                                </div>
                                <div style="text-align: right;">
                                    Tổng tiền:
                                    <span style="font-size: 1.2em; color: #d70018; font-weight: bold;">
                                            <fmt:formatNumber value="${order.totalAmount}" type="currency"
                                                              currencySymbol="₫"/>
                                        </span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

        </section>
    </main>
</div>

<%--<jsp:include page="/style/footer/footer.jsp"/>--%>

<script>
    // 1. Tự động chuyển Tab nếu URL có ?tab=...
    document.addEventListener("DOMContentLoaded", function () {
        const urlParams = new URLSearchParams(window.location.search);
        const activeTab = urlParams.get('tab');
        if (activeTab) {
            // Tìm nút tab có chứa giá trị tương ứng
            // Lưu ý: activeTab từ URL cần khớp với tham số trong onclick (ví dụ: ?tab=Pending)
            let buttons = document.querySelectorAll('.tab');
            buttons.forEach(btn => {
                // Kiểm tra xem nút có chứa chuỗi status đó không
                if (btn.getAttribute('onclick').toLowerCase().includes(activeTab.toLowerCase())) {
                    filterOrders(activeTab, btn);
                }
            });
        }
    });

    // 2. Hàm lọc Tab
    function filterOrders(statusType, btnElement) {
        // Active nút tab
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        if (btnElement) btnElement.classList.add('active');

        // Lọc danh sách
        let cards = document.querySelectorAll('.order-card');

        cards.forEach(card => {
            let cardStatus = card.getAttribute('data-status');

            // So sánh không phân biệt hoa thường
            if (statusType === 'all' ||
                (cardStatus && cardStatus.toUpperCase() === statusType.toUpperCase())) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });
    }

    // 3. Hàm Tìm kiếm
    function searchOrders() {
        let input = document.getElementById('searchInput').value.toLowerCase();
        let cards = document.querySelectorAll('.order-card');

        cards.forEach(card => {
            // Lấy text mã đơn hàng
            let idText = card.querySelector('.order-id-text').innerText.toLowerCase();

            // Lấy text tên sản phẩm (có thể có nhiều sản phẩm trong 1 đơn)
            let hasProduct = false;
            let productNames = card.querySelectorAll('.search-target');
            productNames.forEach(name => {
                if (name.innerText.toLowerCase().includes(input)) hasProduct = true;
            });

            if (idText.includes(input) || hasProduct) {
                card.classList.remove('hidden');
                card.style.display = 'block';
            } else {
                card.classList.add('hidden');
                card.style.display = 'none';
            }
        });
    }

    // 4. Hàm Hủy Đơn Hàng
    function confirmCancelOrder(orderId) {
        if (confirm("Bạn có chắc chắn muốn hủy đơn hàng #" + orderId + " không?")) {
            // Gọi Servlet CancelOrderController
            window.location.href = "${contextPath}/cancel-order?id=" + orderId;
        }
    }
</script>
</body>

</html>