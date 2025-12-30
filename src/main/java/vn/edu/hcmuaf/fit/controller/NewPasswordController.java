package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "NewPasswordController", value = "/new-password")
public class NewPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("email_forgot") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("Login/new_password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newPass = request.getParameter("newPassword");
        String confirmPass = request.getParameter("confirmPassword");
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email_forgot");

        // 1. Kiểm tra 2 mật khẩu có khớp không
        if (newPass == null || !newPass.equals(confirmPass)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher("Login/new_password.jsp").forward(request, response);
            return;
        }

        // 2. Thực hiện đổi mật khẩu
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        if (user != null) {

            String passwordHash = UserService.getInstance().hashPassword(newPass);

            boolean isUpdated = userDAO.changePassword(user.getAccountID(), passwordHash);

            if (isUpdated) {
                session.removeAttribute("otp");
                session.removeAttribute("email_forgot");

                request.setAttribute("message", "Đổi mật khẩu thành công! Hãy đăng nhập lại.");
                request.getRequestDispatcher("Login/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Lỗi hệ thống! Không thể cập nhật mật khẩu.");
                request.getRequestDispatcher("Login/new_password.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Tài khoản không tồn tại hoặc đã bị khóa.");
            request.getRequestDispatcher("Login/new_password.jsp").forward(request, response);
        }
    }

}