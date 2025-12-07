package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.enums.Role;
import vn.edu.hcmuaf.fit.model.Account;
import vn.edu.hcmuaf.fit.service.AccountService;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Login/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý tiếng Việt đầu vào
        request.setCharacterEncoding("UTF-8");

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Sử dụng Service để xử lý logic (tìm user -> mã hóa pass nhập vào -> so sánh)
        AccountService service = new AccountService();
        Account account = service.login(user, pass);
        // --------------------------------

        if (account != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("acc", account);

            // Thiết lập thời gian sống của session (ví dụ: 30 phút)
            session.setMaxInactiveInterval(30 * 60);

            // Phân quyền và Chuyển hướng an toàn
            // Dùng request.getContextPath() để đảm bảo đường dẫn đúng tuyệt đối
            if (account.getRole() == Role.ADMIN) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        } else {
            // Đăng nhập thất bại
            request.setAttribute("mess", "Sai tài khoản hoặc mật khẩu!");
            request.getRequestDispatcher("Login/login.jsp").forward(request, response);
        }
    }
}