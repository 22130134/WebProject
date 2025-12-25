package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.dao.AppointmentDAO;
import vn.edu.hcmuaf.fit.model.Appointment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminAppointmentController", value = "/admin/appointments")
public class AdminAppointmentController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("q");
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        String dateFrom = request.getParameter("dateFrom");
        String dateTo = request.getParameter("dateTo");

        // Null checks for filters to keep them effective only when set
        if ("".equals(type))
            type = null;
        if ("".equals(status))
            status = null;

        AppointmentDAO dao = AppointmentDAO.getInstance();
        List<Appointment> list = dao.filter(keyword, type, status, dateFrom, dateTo);




        // Stats
        int todayTotal = dao.countAppointmentsToday();
        int todayNew = dao.countNewAppointmentsToday();
        int confirmedTotal = dao.countConfirmedAppointments();

        request.setAttribute("listA", list);
        request.setAttribute("todayTotal", todayTotal);
        request.setAttribute("todayNew", todayNew);
        request.setAttribute("confirmedTotal", confirmedTotal);
        request.setAttribute("msgKeyword", keyword);
        request.setAttribute("msgType", type);
        request.setAttribute("msgStatus", status);
        request.setAttribute("msgDateFrom", dateFrom);
        request.setAttribute("msgDateTo", dateTo);

        request.getRequestDispatcher("/Admin/calendar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (idStr != null && action != null) {
            try {
                int id = Integer.parseInt(idStr);
                AppointmentDAO dao = AppointmentDAO.getInstance();

                if ("updateStatus".equals(action)) {
                    String statusStr = request.getParameter("status");
                    Appointment.AppointmentStatus status = Appointment.AppointmentStatus.fromString(statusStr);
                    if (status != null) {
                        dao.updateStatus(id, status);
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("appointments");
    }
}
