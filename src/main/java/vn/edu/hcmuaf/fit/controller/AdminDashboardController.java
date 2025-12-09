package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.dao.AccountDAO;
import vn.edu.hcmuaf.fit.dao.AppointmentDAO;
import vn.edu.hcmuaf.fit.dao.OrderDAO;
import vn.edu.hcmuaf.fit.model.Appointment;
import vn.edu.hcmuaf.fit.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminDashboardController", value = "/admin/overview")
public class AdminDashboardController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch stats
        int totalProducts = ProductService.getInstance().countTotalProducts();
        int totalOrders = OrderDAO.getInstance().countTotalOrders();
        int totalAccounts = new AccountDAO().countTotalAccounts();
        int appointmentsToday = AppointmentDAO.getInstance().countAppointmentsToday();

        // Pass to view
        request.setAttribute("totalProducts", totalProducts);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalAccounts", totalAccounts);
        request.setAttribute("appointmentsToday", appointmentsToday);

        // Optional: Fetch recent lists for dashboard tables if needed (e.g., recent
        // orders)
        // List<Order> recentOrders = OrderDAO.getInstance().getRecentOrders(5);
        // request.setAttribute("recentOrders", recentOrders);

        request.getRequestDispatcher("/Admin/overview.jsp").forward(request, response);
    }
}
