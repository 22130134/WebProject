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
            
            // Chuyển hướng về trang chủ (ví dụ index.jsp)
            response.sendRedirect("index.jsp");
        } else {
            // Đăng nhập thất bại
            request.setAttribute("mess", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}