package vn.edu.hcmuaf.fit.webapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.model.UserDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String phone = req.getParameter("phone");
        String pass = req.getParameter("password");

        User u = new UserDAO().login(phone, pass);

        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            resp.sendRedirect("User/user_home.jsp");
        } else {
            resp.getWriter().println("Sai tài khoản hoặc mật khẩu!");
        }
    }
}

