package vn.edu.hcmuaf.fit.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.enums.Role;
import vn.edu.hcmuaf.fit.model.Account;

import java.io.IOException;

// Áp dụng filter cho tất cả các request đến thư mục /admin/
@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // Lấy thông tin tài khoản từ Session
        Account account = (Account) session.getAttribute("acc");

        // Kiểm tra Đăng nhập
        if (account == null) {
            // Chưa đăng nhập -> Đá về trang Login
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Kiểm tra Trạng thái (Status)
        // Giả sử quy ước trong DB: 1 = Active, 0 = Inactive/Locked
        if (account.getStatus() != 1) {
            // Nếu tài khoản không Active -> Hủy session ngay lập tức
            session.invalidate();
            // Chuyển hướng về login với thông báo lỗi
            resp.sendRedirect(req.getContextPath() + "/login?error=locked");
            return;
        }

        // Kiểm tra Quyền (Role)
        // So sánh với Enum Role.ADMIN
        if (account.getRole() == Role.ADMIN) {
            // Thỏa mãn tất cả điều kiện -> Cho phép truy cập
            chain.doFilter(request, response);
        } else {
            // Đã đăng nhập nhưng không phải Admin -> Báo lỗi 403 (Cấm truy cập)
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang quản trị.");
        }
    }

}