package vn.edu.hcmuaf.fit.webapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.model.UserDAO;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String phone = req.getParameter("phone");
        String pass = req.getParameter("password");
        String name = req.getParameter("fullname");

        User u = new User();
        u.setPhone(phone);
        u.setPassword(pass);
        u.setFullname(name);

        if (new UserDAO().register(u)) {
            resp.sendRedirect("login.jsp");
        } else {
            resp.getWriter().println("Đăng ký thất bại!");
        }
    }
}
