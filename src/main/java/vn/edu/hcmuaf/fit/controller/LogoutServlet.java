package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy session hiện tại
        // Tham số false: Nếu session không tồn tại thì trả về null (không tạo mới)
        // Chúng ta chỉ muốn hủy session cũ, không cần tạo cái mới để hủy
        HttpSession session = request.getSession(false);

        if (session != null) {
            // 2. Hủy session
            // Lệnh này sẽ xóa sạch các attribute ("acc", "cart",...) và hủy phiên làm việc
            session.invalidate();
        }

        // 3. Chuyển hướng người dùng
        // Sau khi đăng xuất, chuyển về trang Đăng nhập hoặc Trang chủ
        response.sendRedirect(request.getContextPath() + "Login/login.jsp");
    }
}