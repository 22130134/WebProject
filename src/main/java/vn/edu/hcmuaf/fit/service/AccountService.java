package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.dao.AccountDAO;
import vn.edu.hcmuaf.fit.model.Account;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AccountService {
    AccountDAO dao = new AccountDAO();

    // Thêm hàm hỗ trợ mã hóa SHA-256
    private String hashPassword(String password) {
        try {
            // Sử dụng thuật toán SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Chuyển mật khẩu sang byte và băm
            byte[] hashedBytes = md.digest(password.getBytes());

            // Chuyển mảng byte sang chuỗi String (dùng Base64 cho gọn và chuẩn)
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Cập nhật logic ĐĂNG NHẬP (Login)
    public Account login(String username, String password) {
        Account account = dao.findByUsername(username);

        if (account != null) {
            // Bước A: Mã hóa mật khẩu người dùng vừa nhập bằng hàm SHA ở trên
            String inputHash = hashPassword(password);

            // Bước B: So sánh chuỗi vừa mã hóa với chuỗi đang lưu trong Database
            // (Dùng equals vì SHA luôn ra kết quả giống nhau với cùng 1 input)
            if (inputHash != null && inputHash.equals(account.getPassword())) {
                return account; // Đăng nhập thành công
            }
        }
        return null; // Sai user hoặc sai pass
    }

    // 4. Cập nhật logic ĐĂNG KÝ (Register)
    public boolean register(String username, String password, String email) {
        if (dao.checkUsernameExist(username)) {
            return false;
        }

        // Bước A: Mã hóa mật khẩu bằng SHA-256
        String hashedPassword = hashPassword(password);

        // Bước B: Lưu chuỗi đã mã hóa vào DB
        return dao.register(username, hashedPassword, email);
    }
}