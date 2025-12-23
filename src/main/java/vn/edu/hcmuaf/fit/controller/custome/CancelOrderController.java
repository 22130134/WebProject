package vn.edu.hcmuaf.fit.controller.custome;

import vn.edu.hcmuaf.fit.dao.OrderDAO;
import vn.edu.hcmuaf.fit.model.Customer;
import vn.edu.hcmuaf.fit.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CancelOrderController", value = "/cancel-order")
public class CancelOrderController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        Customer customer = (Customer) session.getAttribute("customer");
        String contextPath = request.getContextPath();
        String targetUrl = contextPath + "/purchase-history"; // Trang đích là lịch sử mua hàng

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
            int orderId = Integer.parseInt(idParam);
            int customerID = customer.getCustomerID();

            // Lấy Instance của OrderDAO (Giả định bạn dùng Singleton giống AppointmentDAO)
            OrderDAO orderDAO = OrderDAO.getInstance();

            // 2. BẢO MẬT: Kiểm tra đơn hàng này có phải của khách hàng đang đăng nhập không
            // (Bạn cần thêm hàm này vào OrderDAO, xem hướng dẫn bên dưới)
            if (!orderDAO.isOrderOwnedByCustomer(orderId, customerID)) {
                session.setAttribute("toastError", "Không tìm thấy đơn hàng hoặc bạn không có quyền hủy.");
            } else {

                // 3. THỰC HIỆN HỦY ĐƠN HÀNG
                // Kiểm tra trạng thái hiện tại (chỉ cho hủy nếu đang Pending)
                // Hàm cancelOrder đã viết ở bước trước
                boolean success = orderDAO.cancelOrder(orderId);

                if (success) {
                    session.setAttribute("toastSuccess", "Đơn hàng #" + orderId + " đã được hủy thành công.");
                    // Chuyển hướng đến Tab Đã hủy
                    targetUrl += "?tab=Cancelled";
                } else {
                    session.setAttribute("toastError", "Không thể hủy đơn hàng #" + orderId + " (Có thể đơn đã được xử lý).");
                }
            }

        } catch (NumberFormatException e) {
            session.setAttribute("toastError", "Mã đơn hàng không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("toastError", "Đã xảy ra lỗi hệ thống.");
        }

        // 4. Chuyển hướng cuối cùng
        response.sendRedirect(targetUrl);
    }
}