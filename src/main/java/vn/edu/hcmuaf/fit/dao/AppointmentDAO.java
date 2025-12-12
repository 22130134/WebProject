package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.db.DBConnect;
import vn.edu.hcmuaf.fit.model.Appointment;
import vn.edu.hcmuaf.fit.model.Appointment.AppointmentStatus;
import vn.edu.hcmuaf.fit.model.Appointment.AppointmentType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private static AppointmentDAO instance;

    private AppointmentDAO() {
    }

    public static AppointmentDAO getInstance() {
        if (instance == null) {
            instance = new AppointmentDAO();
        }
        return instance;
    }

    /**
     * Create a new appointment in the database
     * 
     * @param appointment Appointment object to insert
     * @return generated AppointmentID, or -1 if failed
     */
    public int createAppointment(Appointment appointment) {
        Connection conn = DBConnect.get();
        System.out.println("DEBUG: Connection = " + conn);
        if (conn == null) {
            System.out.println("ERROR: Connection is null!");
            return -1;
        }

        String sql = "INSERT INTO appointments (CustomerID, ProductID, AppointmentDateTime, AppointmentType, Address, Status, AdminNote) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, appointment.getCustomerID());
            System.out.println("DEBUG: CustomerID = " + appointment.getCustomerID());

            if (appointment.getProductID() != null) {
                ps.setInt(2, appointment.getProductID());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            System.out.println("DEBUG: ProductID = " + appointment.getProductID());

            ps.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDateTime()));
            System.out.println("DEBUG: DateTime = " + appointment.getAppointmentDateTime());

            ps.setString(4, appointment.getAppointmentType().getValue());
            System.out.println("DEBUG: Type = " + appointment.getAppointmentType().getValue());

            ps.setString(5, appointment.getAddress());
            System.out.println("DEBUG: Address = " + appointment.getAddress());

            ps.setString(6, appointment.getStatus().getValue());
            System.out.println("DEBUG: Status = " + appointment.getStatus().getValue());

            ps.setString(7, appointment.getAdminNote());
            System.out.println("DEBUG: AdminNote = " + appointment.getAdminNote());

            System.out.println("DEBUG: Executing SQL: " + sql);
            int affectedRows = ps.executeUpdate();
            System.out.println("DEBUG: Affected rows = " + affectedRows);

            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("DEBUG: Generated ID = " + id);
                    return id;
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR: SQLException occurred!");
            System.out.println("ERROR Message: " + e.getMessage());
            System.out.println("ERROR SQLState: " + e.getSQLState());
            System.out.println("ERROR ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get appointment by ID
     */
    public Appointment getAppointmentById(int appointmentID) {
        Connection conn = DBConnect.get();
        if (conn == null)
            return null;

        String sql = "SELECT * FROM appointments WHERE AppointmentID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractAppointmentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get all appointments for a customer
     */
    public List<Appointment> getAppointmentsByCustomer(int customerID) {
        List<Appointment> appointments = new ArrayList<>();
        Connection conn = DBConnect.get();
        if (conn == null)
            return appointments;

        String sql = "SELECT * FROM appointments WHERE CustomerID = ? ORDER BY AppointmentDateTime DESC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Update appointment status
     */
    public boolean updateStatus(int appointmentID, AppointmentStatus status) {
        Connection conn = DBConnect.get();
        if (conn == null)
            return false;

        String sql = "UPDATE appointments SET Status = ? WHERE AppointmentID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status.getValue());
            ps.setInt(2, appointmentID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update full appointment details
     */
    public boolean updateAppointment(Appointment appointment) {
        Connection conn = DBConnect.get();
        if (conn == null)
            return false;

        String sql = "UPDATE appointments SET AppointmentDateTime = ?, AppointmentType = ?, " +
                "Address = ?, Status = ?, AdminNote = ? WHERE AppointmentID = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, Timestamp.valueOf(appointment.getAppointmentDateTime()));
            ps.setString(2, appointment.getAppointmentType().getValue());
            ps.setString(3, appointment.getAddress());
            ps.setString(4, appointment.getStatus().getValue());
            ps.setString(5, appointment.getAdminNote());
            ps.setInt(6, appointment.getAppointmentID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Helper method to extract Appointment object from ResultSet
     */
    private Appointment extractAppointmentFromResultSet(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentID(rs.getInt("AppointmentID"));
        appointment.setCustomerID(rs.getInt("CustomerID"));

        int productID = rs.getInt("ProductID");
        if (!rs.wasNull()) {
            appointment.setProductID(productID);
        }

        Timestamp timestamp = rs.getTimestamp("AppointmentDateTime");
        appointment.setAppointmentDateTime(timestamp.toLocalDateTime());

        appointment.setAppointmentType(AppointmentType.fromString(rs.getString("AppointmentType")));
        appointment.setAddress(rs.getString("Address"));
        appointment.setStatus(AppointmentStatus.fromString(rs.getString("Status")));
        appointment.setAdminNote(rs.getString("AdminNote"));

        return appointment;
    }

    //HUNG
    /**
     * Kiểm tra lịch hẹn có thuộc về Khách hàng đang đăng nhập không (Bảo mật)
     * @param appointmentID ID lịch hẹn cần hủy
     * @param customerID ID khách hàng đang đăng nhập
     * @return true nếu khách hàng là chủ sở hữu lịch hẹn
     */
    public boolean isAppointmentOwnedByCustomer(int appointmentID, int customerID) {
        String query = "SELECT COUNT(*) FROM appointments WHERE AppointmentID = ? AND CustomerID = ?";
        try (Connection conn = DBConnect.get();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointmentID);
            ps.setInt(2, customerID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
