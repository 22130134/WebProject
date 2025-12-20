package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.dao.ProductDAO;
import vn.edu.hcmuaf.fit.model.Appointment;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.Product;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.AppointmentService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AppointmentHistoryController", value = "/appointment-history")
public class AppointmentHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        Customer customer = (Customer) session.getAttribute("customer");

        // 1. Kiểm tra đăng nhập
        if (user == null || customer == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 2. Lấy danh sách lịch hẹn
        AppointmentService service = AppointmentService.getInstance();
        List<Appointment> appointmentList = service.getCustomerAppointments(customer.getCustomerID());

        // 3. Lấy thông tin Tên sản phẩm (Vì Appointment chỉ có ProductID)
        // Map<ProductID, ProductName>
        Map<Integer, String> productNames = new HashMap<>();
        ProductDAO productDAO = ProductDAO.getInstance(); // Giả định bạn đã có ProductDAO từ các bước trước

        for (Appointment app : appointmentList) {
            if (app.getProductID() != null && app.getProductID() > 0) {
                if (!productNames.containsKey(app.getProductID())) {
                    Product p = productDAO.getProductById(app.getProductID());
                    if (p != null) {
                        productNames.put(app.getProductID(), p.getName());
                    } else {
                        productNames.put(app.getProductID(), "Sản phẩm không tồn tại");
                    }
                }
            }
        }

        // 4. Gửi dữ liệu sang JSP
        request.setAttribute("appointments", appointmentList);
        request.setAttribute("productNames", productNames);

        // Chuyển hướng đến file JSP (Bạn cần tạo file này ở bước 3)
        request.getRequestDispatcher("/Customer/Appointment/appointment_history.jsp").forward(request, response);
    }
}