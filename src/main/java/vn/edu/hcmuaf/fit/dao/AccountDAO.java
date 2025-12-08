package vn.edu.hcmuaf.fit.dao;

import vn.edu.hcmuaf.fit.db.DBConnect;
import vn.edu.hcmuaf.fit.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Account> getAll() {
        List<Account> list = new ArrayList<>();
        String query = "select * from accounts";
        try {
            conn = DBConnect.get();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Account> filter(String keyword, String role, String status) {
        List<Account> list = new ArrayList<>();
        StringBuilder query = new StringBuilder("select * from accounts where 1=1");

        if (keyword != null && !keyword.isEmpty()) {
            query.append(" and (Username like ? or Email like ?)");
        }
        if (role != null && !role.isEmpty()) {
            query.append(" and Role = ?");
        }
        if (status != null && !status.isEmpty()) {
            query.append(" and Status = ?");
        }

        try {
            conn = DBConnect.get();
            ps = conn.prepareStatement(query.toString());

            int index = 1;
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
                ps.setString(index++, "%" + keyword + "%");
            }
            if (role != null && !role.isEmpty()) {
                ps.setString(index++, role);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(index++, status);
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Account(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
