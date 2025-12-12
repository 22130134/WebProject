<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch đặt khám</title>

    <link rel="stylesheet" href="${contextPath}/Customer/Appointment/appointment_history.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header/header.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Customer/User_sidebar/user_sidebar.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer/footer.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<jsp:include page="/style/header/header.jsp"/>

<div class="user-container">

    <jsp:include page="/Customer/User_sidebar/user_sidebar.jsp"/>

    <main class="content">
        <section class="appointment-history">
            <h2 style="padding-bottom: 10px;">Xem đặt lịch khám</h2>

            <div class="tabs">
                <button class="tab active" onclick="filterApps('all', this)">Tất cả</button>
                <button class="tab" onclick="filterApps('New', this)">Chờ xác nhận</button>
                <button class="tab" onclick="filterApps('Confirmed', this)">Đã xác nhận</button>
                <button class="tab" onclick="filterApps('Completed', this)">Đã khám xong</button>
                <button class="tab" onclick="filterApps('Cancelled', this)">Đã hủy</button>
            </div>

            <div class="search-box">
                <input type="text" id="searchInput" onkeyup="searchApps()"
                       placeholder="Tìm theo mã lịch, tên dịch vụ..."/>
                <button class="search-btn"><i class="fa-solid fa-magnifying-glass"></i></button>
            </div>

            <c:choose>
                <c:when test="${empty appointments}">
                    <div class="empty-state">
                        <img src="https://cdn-icons-png.flaticon.com/512/2907/2907150.png" alt="No Appointment"
                             width="100" style="opacity: 0.5;">
                        <p style="margin-top: 15px; color: #777;">Bạn chưa có lịch hẹn nào.</p>
                        <a href="${contextPath}/booking" class="btn-action" style="margin-top: 10px;">Đặt lịch ngay</a>
                    </div>
                </c:when>

                <c:otherwise>
                    <div id="appList">
                        <c:forEach var="app" items="${appointments}">
                            <div class="appointment-card" data-status="${app.status}">

                                <div class="app-header">
                                    <div>
                                        <span class="app-id">Lịch hẹn #${app.appointmentID}</span>
                                        <span class="app-date">
                                            <i class="fa-regular fa-clock"></i> ${app.formattedDateTime}
                                        </span>
                                    </div>
                                    <div class="status-badge status-${app.statusCssClass}">
                                            ${app.statusVietnamese}
                                    </div>
                                </div>

                                <div class="app-body">
                                    <div class="app-item">
                                        <div class="item-icon">
                                            <i class="fa-solid fa-user-doctor"></i>
                                        </div>

                                        <div class="item-info">
                                            <c:choose>
                                                <c:when test="${not empty app.productID and app.productID > 0}">
                                                    <a href="${contextPath}/product-detail?id=${app.productID}"
                                                       class="item-name search-target">
                                                            ${productNames[app.productID]}
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="item-name search-target">Tư vấn sức khỏe tổng quát</span>
                                                </c:otherwise>
                                            </c:choose>

                                            <div class="item-detail">
                                                <i class="fa-solid fa-notes-medical"
                                                   style="width: 20px; color: #777;"></i>
                                                Hình thức: <b>${app.typeVietnamese}</b>
                                            </div>

                                            <div class="item-detail">
                                                <i class="fa-solid fa-location-dot"
                                                   style="width: 20px; color: #777;"></i>
                                                Địa điểm: ${empty app.address ? 'Tại phòng khám' : app.address}
                                            </div>

                                            <c:if test="${not empty app.adminNote}">
                                                <div class="item-note">
                                                    <i class="fa-solid fa-circle-info"></i> <b>Phản hồi từ
                                                    Admin:</b> ${app.adminNote}
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>

                                <div class="app-footer">
                                    <c:if test="${app.status == 'NEW'}">
                                        <button onclick="confirmCancelApp(${app.appointmentID})"
                                                class="btn-action btn-cancel">
                                            Hủy lịch
                                        </button>
                                    </c:if>

                                    <c:if test="${app.status == 'COMPLETED' || app.status == 'CANCELLED'}">
                                        <a href="${contextPath}/booking?productId=${app.productID}" class="btn-action">
                                            <i class="fa-solid fa-rotate-right"></i> Đặt lại
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>

        </section>
    </main>
</div>

<script>
    // 1. Tự động chuyển Tab nếu URL có ?tab=...
    document.addEventListener("DOMContentLoaded", function () {
        const urlParams = new URLSearchParams(window.location.search);
        const activeTab = urlParams.get('tab');
        if (activeTab) {

            let buttons = document.querySelectorAll('.tab');
            buttons.forEach(btn => {
                if(btn.getAttribute('onclick').toLowerCase().includes(activeTab.toLowerCase())) {
                    filterApps(activeTab, btn);
                }
            });
        }
    });

    // 2. Hàm lọc Tab
    function filterApps(statusType, btnElement) {
        // Đổi active tab
        document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
        if(btnElement) btnElement.classList.add('active');

        // Lọc cards
        let cards = document.querySelectorAll('.appointment-card');

        cards.forEach(card => {
            // Lấy status từ data attribute
            let cardStatus = card.getAttribute('data-status');

            // Chuyển cả 2 về chữ IN HOA để so sánh (Khắc phục lỗi New vs NEW)
            // Kiểm tra null để tránh lỗi nếu dữ liệu bị thiếu
            if (cardStatus && statusType) {
                if (statusType === 'all' || cardStatus.toUpperCase() === statusType.toUpperCase()) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            }
        });
    }

    // 3. Hàm Tìm kiếm
    function searchApps() {
        let input = document.getElementById('searchInput').value.toLowerCase();
        let cards = document.querySelectorAll('.appointment-card');

        cards.forEach(card => {
            let idText = card.querySelector('.app-id').innerText.toLowerCase();
            let nameText = card.querySelector('.search-target').innerText.toLowerCase();

            if (idText.includes(input) || nameText.includes(input)) {
                card.classList.remove('hidden');
                card.style.display = 'block'; // Reset display
            } else {
                card.classList.add('hidden');
                card.style.display = 'none';
            }
        });
    }

    // 4. Hàm Hủy lịch hẹn
    function confirmCancelApp(appId) {
        if (confirm("Bạn có chắc chắn muốn hủy lịch hẹn #" + appId + " không?")) {
            // Bạn cần tạo Servlet CancelAppointmentController để xử lý URL này
            window.location.href = "${contextPath}/cancel-appointment?id=" + appId;
        }
    }
</script>

</body>
</html>