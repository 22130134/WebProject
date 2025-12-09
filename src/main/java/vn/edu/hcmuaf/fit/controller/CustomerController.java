package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.CustomerService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CustomerController", value = "/update-profile")
public class CustomerController extends HttpServlet {

    // doGet: Dùng để chuyển hướng đến trang Thông tin cá nhân (profile.jsp)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");

        // Kiểm tra đăng nhập
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Chuyển hướng đến trang JSP hiển thị profile (Bạn sẽ tạo file này sau)
        // Giả sử đường dẫn là User/profile.jsp
        request.getRequestDispatcher("/Customer/Profile/profile.jsp").forward(request, response);
    }

    // doPost: Xử lý logic cập nhật thông tin khi bấm nút "Lưu"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1. Xử lý tiếng Việt
        request.setCharacterEncoding("UTF-8");

        // 2. Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");

        if (user == null) {
            response.sendRedirect("/login");
            return;
        }

        // 3. Lấy dữ liệu từ Form
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Thêm logic VALIDATION (kiểm tra dữ liệu)
        if (fullName == null || fullName.trim().isEmpty() ||
                phone == null || phone.trim().isEmpty() ||
                address == null || address.trim().isEmpty()) {

            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            request.getRequestDispatcher("/Customer/Profile/profile.jsp").forward(request, response);
            return; // Dừng xử lý, không gọi Service
        }

        // Kiểm tra định dạng số điện thoại
        if (!phone.matches("\\d{10,11}")) {
            request.setAttribute("error", "Số điện thoại không hợp lệ (phải là 10-11 số)!");
            request.getRequestDispatcher("/Customer/Profile/profile.jsp").forward(request, response);
            return;
        }

        // 4. Gọi Service để update vào Database
        boolean isUpdated = CustomerService.getInstance().updateCustomerInfo(
                user.getAccountID(),
                fullName,
                phone,
                address
        );

        if (isUpdated) {
            // ⚠️ QUAN TRỌNG: Cập nhật lại thông tin trong Session
            // Nếu không làm bước này, người dùng phải đăng xuất ra vào lại mới thấy thay đổi
            Customer newInfo = CustomerService.getInstance().getCustomerByAccountId(user.getAccountID());
            session.setAttribute("customer", newInfo);

            request.setAttribute("message", "Cập nhật thông tin thành công!");
        } else {
            request.setAttribute("error", "Cập nhật thất bại. Vui lòng thử lại!");
        }

        // 5. Trả về trang profile để hiển thị kết quả
        request.getRequestDispatcher("Customer/Profile/profile.jsp").forward(request, response);
    }
}