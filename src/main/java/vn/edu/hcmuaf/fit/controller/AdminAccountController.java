package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.dao.AccountDAO;
import vn.edu.hcmuaf.fit.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminAccountController", value = "/admin/accounts")
public class AdminAccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String q = request.getParameter("q");
        String role = request.getParameter("role");
        String status = request.getParameter("status");

        AccountDAO dao = new AccountDAO();
        List<Account> list;

        if ((q != null && !q.isEmpty()) || (role != null && !role.isEmpty()) || (status != null && !status.isEmpty())) {
            list = dao.filter(q, role, status);
        } else {
            list = dao.getAll();
        }

        request.setAttribute("listA", list);
        request.setAttribute("msgName", q);
        request.setAttribute("msgRole", role);
        request.setAttribute("msgStatus", status);

        request.getRequestDispatcher("/Admin/accounts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
