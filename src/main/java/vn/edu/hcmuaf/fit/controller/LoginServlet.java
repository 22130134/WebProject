package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.dao.AccountDAO;
import vn.edu.hcmuaf.fit.model.Account;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginServlet extends HttpServlet {

    // Phương thức GET để hiện trang login.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    // Phương thức POST để xử lý khi người dùng bấm nút "Đăng nhập"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        AccountDAO dao = new AccountDAO();
        Account account = dao.login(user, pass);

        if (account != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("acc", account); // Lưu thông tin user vào session

            // --- Bổ sung Logic Kiểm tra Role ---

            if (account.getRole() == 1) { // Giả sử 1 là Admin (Admin Role ID)
                // Đây là tài khoản Admin
                response.sendRedirect("admin/dashboard.jsp"); // Chuyển hướng đến trang Admin
            } else { // Role = 0 (hoặc bất kỳ role nào khác)
                // Đây là tài khoản User bình thường
                response.sendRedirect("index.jsp"); // Chuyển hướng về trang chủ User
            }

        } else {
            // Đăng nhập thất bại
            request.setAttribute("mess", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}