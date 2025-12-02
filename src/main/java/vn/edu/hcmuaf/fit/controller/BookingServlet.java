package vn.edu.hcmuaf.fit.controller;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.model.Appointment;
import vn.edu.hcmuaf.fit.model.Cart;
import vn.edu.hcmuaf.fit.service.AppointmentService;
import vn.edu.hcmuaf.fit.service.CartService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static vn.edu.hcmuaf.fit.model.Appointment.AppointmentType;

@WebServlet(name = "BookingServlet", value = "/booking")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Load cart from database (same as HomeServlet)
        HttpSession session = request.getSession();
        int dummyCustomerId = 1;
        Cart cart = CartService.getInstance().getCart(dummyCustomerId);
        session.setAttribute("cart", cart);

        // Forward to booking page
        request.getRequestDispatcher("/Booking/booking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> jsonResponse = new HashMap<>();

        try {
            // Get form parameters
            String serviceType = request.getParameter("serviceType");
            String appointmentDate = request.getParameter("appointmentDate"); // yyyy-MM-dd
            String appointmentTime = request.getParameter("appointmentTime"); // HH:mm
            String careType = request.getParameter("careType"); // "AtHome" or "AtClinic"
            String address = request.getParameter("address");
            String notes = request.getParameter("notes");

            // Validate required fields
            if (serviceType == null || serviceType.trim().isEmpty() ||
                    appointmentDate == null || appointmentDate.trim().isEmpty() ||
                    appointmentTime == null || appointmentTime.trim().isEmpty() ||
                    careType == null || careType.trim().isEmpty()) {

                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Vui lòng điền đầy đủ thông tin bắt buộc");
                response.getWriter().write(new Gson().toJson(jsonResponse));
                return;
            }

            // Parse date and time
            LocalDate date = LocalDate.parse(appointmentDate);
            LocalTime time = LocalTime.parse(appointmentTime);
            LocalDateTime appointmentDateTime = LocalDateTime.of(date, time);

            // Validate datetime is in the future
            if (appointmentDateTime.isBefore(LocalDateTime.now())) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Thời gian hẹn phải là thời điểm trong tương lai");
                response.getWriter().write(new Gson().toJson(jsonResponse));
                return;
            }

            // Parse appointment type
            AppointmentType appointmentType = AppointmentType.fromString(careType);
            if (appointmentType == null) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Loại hình chăm sóc không hợp lệ");
                response.getWriter().write(new Gson().toJson(jsonResponse));
                return;
            }

            // Validate address for AtHome appointments
            if (appointmentType == AppointmentType.AT_HOME &&
                    (address == null || address.trim().isEmpty())) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Vui lòng nhập địa chỉ cho dịch vụ tại nhà");
                response.getWriter().write(new Gson().toJson(jsonResponse));
                return;
            }

            // Get customer ID from session (dummy for now)
            int dummyCustomerId = 1;

            // Build admin note with service type and additional notes
            StringBuilder adminNote = new StringBuilder();
            adminNote.append("Loại dịch vụ: ").append(serviceType);
            if (notes != null && !notes.trim().isEmpty()) {
                adminNote.append("\nGhi chú: ").append(notes);
            }

            // Create appointment object
            Appointment appointment = new Appointment();
            appointment.setCustomerID(dummyCustomerId);
            appointment.setAppointmentDateTime(appointmentDateTime);
            appointment.setAppointmentType(appointmentType);
            appointment.setAddress(address);
            appointment.setAdminNote(adminNote.toString());

            // Save to database
            int appointmentId = AppointmentService.getInstance().bookAppointment(appointment);

            if (appointmentId > 0) {
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Đặt lịch thành công! Chúng tôi sẽ liên hệ với bạn sớm nhất.");
                jsonResponse.put("appointmentId", appointmentId);
            } else {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Có lỗi xảy ra khi đặt lịch. Vui lòng thử lại.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
}
