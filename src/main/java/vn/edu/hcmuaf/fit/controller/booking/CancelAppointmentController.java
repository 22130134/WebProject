package vn.edu.hcmuaf.fit.controller.booking;

import vn.edu.hcmuaf.fit.dao.AppointmentDAO;
import vn.edu.hcmuaf.fit.model.Appointment.AppointmentStatus;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.User;
import vn.edu.hcmuaf.fit.service.AppointmentService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CancelAppointmentController", value = "/cancel-appointment")
public class CancelAppointmentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        Customer customer = (Customer) session.getAttribute("customer");
        String contextPath = request.getContextPath();
        String targetUrl = contextPath + "/appointment-history";

        // 1. Kiểm tra đăng nhập
        if (user == null || customer == null) {
            response.sendRedirect(contextPath + "/login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(targetUrl);
            return;
        }

        try {
            int appointmentID = Integer.parseInt(idParam);
            AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
            int customerID = customer.getCustomerID();

            // 2. BẢO MẬT: Kiểm tra quyền sở hữu lịch hẹn
            if (!appointmentDAO.isAppointmentOwnedByCustomer(appointmentID, customerID)) {
                session.setAttribute("toastError", "Không tìm thấy lịch hẹn hoặc bạn không có quyền hủy.");
            } else {
                
                // 3. THỰC HIỆN HỦY LỊCH HẸN
                // Lý tưởng: Chỉ cho phép hủy nếu trạng thái là 'New' (Chờ xác nhận)
                // Hàm updateStatus trong DAO đã nhận AppointmentStatus.CANCELLED
                
                boolean success = AppointmentService.getInstance()
                        .updateAppointmentStatus(appointmentID, AppointmentStatus.CANCELLED);
                
                if (success) {
                    session.setAttribute("toastSuccess", "Lịch hẹn #" + appointmentID + " đã được hủy thành công.");
                    // Chuyển hướng đến Tab Đã hủy
                    targetUrl += "?tab=Cancelled"; 
                } else {
                    session.setAttribute("toastError", "Không thể hủy lịch hẹn #" + appointmentID + ".");
                }
            }

        } catch (NumberFormatException e) {
            session.setAttribute("toastError", "Mã lịch hẹn không hợp lệ.");
        } 
        
        // 4. Chuyển hướng cuối cùng
        response.sendRedirect(targetUrl);
    }
}