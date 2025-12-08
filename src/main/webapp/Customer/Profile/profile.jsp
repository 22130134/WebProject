<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%-- Lấy dữ liệu từ Session --%>
<c:set var="user" value="${sessionScope.auth}"/>
<c:set var="customer" value="${sessionScope.customer}"/>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin tài khoản - ${user.username}</title>

    <%-- CSS riêng của trang Profile --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Customer/Profile/profile.css">

    <%-- CSS của các thành phần chung (Header, Footer, Sidebar) --%>
    <%-- Lưu ý: Vẫn cần giữ các link CSS này để style hiển thị đúng --%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header/header.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Customer/User_sidebar/user_sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer/footer.css"/>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>

<body>

<%-- 1. Kiểm tra đăng nhập --%>
<c:if test="${empty user}">
    <c:redirect url="${pageContext.request.contextPath}/login"/>
</c:if>

<%-- 2. NHÚNG HEADER --%>
<%-- Giả sử file header.jsp nằm ở thư mục gốc (webapp/header.jsp) --%>
<%-- Nếu file nằm ở thư mục khác, hãy sửa đường dẫn bên dưới (VD: /includes/header.jsp) --%>
<jsp:include page="/style/header/header.jsp"/>

<main class="container">

    <%-- 3. NHÚNG SIDEBAR --%>
    <%-- File này nằm ở thư mục webapp/User_sidebar/user_sidebar.jsp --%>
    <jsp:include page="/Customer/User_sidebar/user_sidebar.jsp"/>

    <%-- 4. NỘI DUNG CHÍNH (FORM PROFILE) --%>
    <section class="profile">

        <%-- Hiển thị thông báo --%>
        <c:if test="${not empty message}">
            <div style="color: green; padding: 10px; border: 1px solid green; margin-bottom: 20px;">
                    ${message}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div style="color: red; padding: 10px; border: 1px solid red; margin-bottom: 20px;">
                    ${error}
            </div>
        </c:if>

        <div class="profile-header">
            <%-- Avatar chữ cái đầu --%>
            <div class="avatar-large">${fn:substring(user.username, 0, 1)}</div>
            <button class="change-avatar">Thay ảnh</button>
        </div>

        <div class="info-table">
            <div class="info-row">
                <span class="label">Tên đăng nhập</span>
                <span class="value">${user.username}</span>
            </div>

            <div class="info-row">
                <span class="label">Email</span>
                <span class="value">${user.email} <i class="fa fa-check verified"></i></span>
                <a class="action" href="#">Sửa</a>
            </div>

            <%-- FORM CẬP NHẬT --%>
            <form action="${pageContext.request.contextPath}/update-profile" method="post">

                <div class="info-row editable">
                    <span class="label">Họ tên</span>
                    <input type="text" name="fullName" class="value" value="${customer.fullName}"
                           placeholder="Cập nhật họ tên">
                    <button type="submit" class="action">Lưu</button>
                </div>

                <div class="info-row editable">
                    <span class="label">Điện thoại</span>
                    <input type="text" name="phone" class="value" value="${customer.phoneNumber}"
                           placeholder="Cập nhật số điện thoại">
                    <button type="submit" class="action">Lưu</button>
                </div>

                <div class="info-row editable">
                    <span class="label">Địa chỉ</span>
                    <input type="text" name="address" class="value" value="${customer.address}"
                           placeholder="Cập nhật địa chỉ">
                    <button type="submit" class="action">Lưu</button>
                </div>
            </form>

            <div class="info-row">
                <span class="label">Mật khẩu</span>
                <span class="value">*****</span>
                <a class="action" href="#">Đổi mật khẩu</a>
            </div>

            <div class="info-row">
                <span class="label">Vô hiệu hóa và xóa</span>
                <span class="value long-text">Vô hiệu hóa tạm thời hoặc xóa vĩnh viễn tài khoản.</span>
                <a class="action" href="#">Xem</a>
            </div>
        </div>

    </section>

</main>

<%-- 5. NHÚNG FOOTER (Nếu bạn có file footer.jsp) --%>
<%-- <jsp:include page="/footer.jsp" /> --%>
<%--<div class="content">--%>
<%--</div>--%>
</body>

</html>