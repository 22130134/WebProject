<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập META.vn</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Login/login.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header/header.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer/footer.css" />
    </head>

    <body>
        <jsp:include page="/style/header/header.jsp" />


        <!-- MAIN -->
        <main class="container">
            <!-- Banner -->
            <div class="banner">
                <img src="https://i.imgur.com/fNNz2Kt.png" alt="META banner">
            </div>

            <!-- Login Form -->
            <div class="login-box">
                <h2>Đăng nhập</h2>
                <p style="color: red; text-align: center;">${error}</p>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <input type="text" name="username" placeholder="Nhập tên đăng nhập" required>
                    <input type="password" name="password" placeholder="Mật khẩu" required>
                    <a href="recover_password.html" class="forgot">Quên mật khẩu?</a>
                    <button type="submit" class="btn-login"> Đăng nhập</button>
                </form>

                <p>Bạn chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></p>

                <div class="divider">HOẶC</div>

                <button class="btn-social email">✉️ Đăng nhập bằng email</button>
                <button class="btn-social zalo">💬 Đăng nhập bằng Zalo</button>
                <button class="btn-social google">🌐 Đăng nhập bằng Google</button>
            </div>
        </main>
        <script src="${pageContext.request.contextPath}/style/header/header.js"></script>

    </body>

    </html>