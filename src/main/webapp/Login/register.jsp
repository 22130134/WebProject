<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <!DOCTYPE html>
  <html lang="vi">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký META.vn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Login/register.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer/footer.css" />
  </head>

  <body>
    <!-- HEADER -->
    <jsp:include page="/style/header/header.jsp" />

    <!-- MAIN -->
    <main class="container">
      <!-- Banner -->
      <div class="banner">
        <img src="https://i.imgur.com/fNNz2Kt.png" alt="META banner">
      </div>

      <!-- Login Form -->
      <div class="register-box">
        <h2>Đăng ký</h2>
        <p style="color: red; text-align: center;">${error}</p>
        <form action="${pageContext.request.contextPath}/register" method="post">
          <input type="text" name="username" placeholder="Tên đăng nhập" required>
          <input type="email" name="email" placeholder="Email" required>
          <input type="password" name="password" placeholder="Mật khẩu" required>
          <input type="password" name="repassword" placeholder="Nhập lại mật khẩu" required>

          <div class="links">
            <a href="#" style="float:right;">Bạn cần hỗ trợ?</a>
          </div>

          <button type="submit" class="btn-submit">ĐĂNG KÝ</button>
        </form>

        <p>Bạn đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập</a></p>

        <div class="divider">HOẶC</div>

        <button class="btn-social zalo">💬 Đăng ký bằng Zalo</button>
        <button class="btn-social google">🌐 Đăng ký bằng Google</button>
      </div>
    </main>

  </body>

  </html>