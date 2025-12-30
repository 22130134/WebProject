package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "VerifyOtpController", value = "/verify-otp")
public class VerifyOtpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Login/verify_otp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userOtp = request.getParameter("otp");
        HttpSession session = request.getSession();

        String systemOtp = (String) session.getAttribute("otp");
        String email = (String) session.getAttribute("email_forgot");

        // 1. Kiểm tra session hết hạn
        if (systemOtp == null || email == null) {
            request.setAttribute("error", "Mã xác nhận đã hết hạn. Vui lòng thực hiện lại từ đầu!");
            request.getRequestDispatcher("Login/forgot_password.jsp").forward(request, response);
            return;
        }

        // 2. So sánh OTP
        if (userOtp != null && userOtp.equals(systemOtp)) {
            session.setAttribute("otp_verified", true);
            // Chuyển hướng sang trang Nhập mật khẩu mới (URL này do NewPasswordController xử lý)
            response.sendRedirect(request.getContextPath() + "/new-password");

        } else {
            // OTP Sai
            request.setAttribute("error", "Mã xác nhận không chính xác!");
            request.getRequestDispatcher("Login/verify_otp.jsp").forward(request, response);
        }
    }
}