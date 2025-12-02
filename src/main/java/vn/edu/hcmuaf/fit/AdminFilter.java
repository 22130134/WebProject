package vn.edu.hcmuaf.fit;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.model.Account;

import java.io.IOException;

// Áp dụng filter cho tất cả các request đến /admin/*
@WebFilter(urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // 1. Lấy thông tin tài khoản từ Session
        Account account = (Account) session.getAttribute("acc");

        if (account == null) {
            // Chưa đăng nhập: Chuyển hướng về trang đăng nhập
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (account.getRole() == 1) { // 2. Kiểm tra Role
            // Là Admin: Cho phép truy cập
            chain.doFilter(request, response);
        } else {
            // Không phải Admin (hoặc Role khác 1): Chặn truy cập
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang này.");
        }
    }
}