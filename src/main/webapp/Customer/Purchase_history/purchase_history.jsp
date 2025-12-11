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
                <button class="tab active">Tất cả</button>
                <%-- Thêm tab Tất cả --%>
                <button class="tab">Chờ xác nhận</button>
                <button class="tab">Đã xác nhận</button>
                <button class="tab">Đang giao</button>
                <button class="tab">Đã giao</button>
                <button class="tab">Đã hủy</button>
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
</body>

</html>