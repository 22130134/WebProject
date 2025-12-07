package vn.edu.hcmuaf.fit.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.service.AccountService;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        String rePass = request.getParameter("repassword");
        String email = request.getParameter("email");

        // Validation (Kiểm tra dữ liệu đầu vào)
        String error = null;
        if (user == null || user.trim().isEmpty() || pass == null || pass.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            error = "Vui lòng điền đầy đủ các trường.";
        } else if (pass.length() < 6) {
            error = "Mật khẩu phải có ít nhất 6 ký tự.";
        } else if (!pass.equals(rePass)) {
            error = "Mật khẩu nhập lại không khớp!";
        } else if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            error = "Địa chỉ email không hợp lệ.";
        }

        if (error != null) {
            request.setAttribute("mess", error);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // GỌI SERVICE (Thay vì gọi DAO và BCrypt thủ công)
        AccountService service = new AccountService();

        // Service sẽ tự lo việc: Kiểm tra user tồn tại -> Mã hóa SHA-256 -> Gọi DAO lưu
        boolean isSuccess = service.register(user, pass, email);

        if (isSuccess) {
            // Thành công
            response.sendRedirect("login?status=success");
        } else {
            // Thất bại (Có thể do User đã tồn tại hoặc lỗi DB)
            // Vì Service trả về boolean nên ta thông báo chung
            request.setAttribute("mess", "Đăng ký thất bại! Tên đăng nhập đã tồn tại hoặc lỗi hệ thống.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}