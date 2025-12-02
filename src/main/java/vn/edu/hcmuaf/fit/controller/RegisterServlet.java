package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.dao.AccountDAO;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    // Hiển thị trang đăng ký (register.jsp)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    // Xử lý logic đăng ký khi người dùng bấm nút Submit
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy dữ liệu từ form
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String rePass = request.getParameter("repassword"); // Mật khẩu nhập lại
        String email = request.getParameter("email");

        // 2. Kiểm tra mật khẩu nhập lại (Validation cơ bản)
        if (!pass.equals(rePass)) {
            request.setAttribute("mess", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // 3. Kiểm tra xem username đã tồn tại chưa
        AccountDAO dao = new AccountDAO();
        boolean exist = dao.checkUsernameExist(user);

        if (exist) {
            request.setAttribute("mess", "Tài khoản đã tồn tại!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            // 4. Nếu chưa tồn tại -> Gọi hàm register trong DAO
            boolean isSuccess = dao.register(user, pass, email);
            if(isSuccess) {
                // Đăng ký thành công -> Chuyển hướng về trang đăng nhập
                response.sendRedirect("login"); // Có thể thêm tham số ?success=true để hiện thông báo bên login
            } else {
                // Lỗi hệ thống hoặc database
                request.setAttribute("mess", "Lỗi hệ thống, vui lòng thử lại!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }
}