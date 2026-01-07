package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.util.EmailUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "ForgotPasswordController", value = "/forgot-password")
public class ForgotPasswordController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Chuyển hướng đến trang nhập email
        request.getRequestDispatcher("Login/forgot_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO userDAO = new UserDAO();

        // 1. Kiểm tra email có tồn tại trong hệ thống không
        if (!userDAO.checkEmailExist(email)) {
            request.setAttribute("error", "Email này chưa được đăng ký trong hệ thống!");
            request.getRequestDispatcher("Login/forgot_password.jsp").forward(request, response);
            return;
        }

        // 2. Tạo mã OTP ngẫu nhiên (6 chữ số)
        String otp = String.format("%06d", new Random().nextInt(999999));

        // 3. Lưu OTP vào Session để lát nữa kiểm tra
        HttpSession session = request.getSession();
        session.setAttribute("otp", otp);
        session.setAttribute("email_forgot", email);
        session.setMaxInactiveInterval(300); // Session tồn tại 5 phút (300s)

        // 4. Gửi Email (Chạy luồng riêng để web không bị đơ)
        String subject = "Mã xác nhận khôi phục mật khẩu";
        String body = "<div style='padding: 20px; background-color: #f4f4f4;'>" +
                "<div style='background-color: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>" +
                "<h2 style='color: #333;'>Yêu cầu khôi phục mật khẩu</h2>" +
                "<p>Xin chào, bạn vừa yêu cầu lấy lại mật khẩu. Mã xác nhận của bạn là:</p>" +
                "<h1 style='color: #007bff; letter-spacing: 5px; text-align: center;'>" + otp + "</h1>" +
                "<p>Mã này có hiệu lực trong 5 phút. Nếu không phải bạn thực hiện, vui lòng bỏ qua email này.</p>" +
                "</div></div>";

        new Thread(() -> EmailUtils.sendEmail(email, subject, body)).start();

        // 5. Chuyển sang trang nhập mã OTP
        response.sendRedirect(request.getContextPath() + "/verify-otp");
    }
}