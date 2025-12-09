package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.dao.UserDAO;
import vn.edu.hcmuaf.fit.model.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private UserService() {
    }

    public User checkLogin(String username, String password) {
        return new UserDAO().checkLogin(username, hashPassword(password));
    }

    public boolean checkUserExist(String username) {
        return new UserDAO().checkUserExist(username);
    }

    public boolean checkEmailExist(String email) {
        return new UserDAO().checkEmailExist(email);
    }

    public void register(String username, String password, String email) {
        new UserDAO().register(username, hashPassword(password), email);
    }

    // HUNG (ChangePassword)
    public boolean changePassword(int accountID, String oldPassword, String newPassword) {
        UserDAO dao = new UserDAO();

        // Bước 1: Lấy mật khẩu hiện tại (đã mã hóa) trong Database
        String currentHashInDB = dao.getPasswordById(accountID);

        // Bước 2: Mã hóa mật khẩu cũ mà người dùng nhập vào
        String oldPasswordInputHash = hashPassword(oldPassword);

        // Bước 3: So sánh
        // Nếu không tìm thấy user hoặc mật khẩu cũ nhập vào không khớp với DB
        if (currentHashInDB == null || !currentHashInDB.equals(oldPasswordInputHash)) {
            return false; // Đổi mật khẩu thất bại do sai mật khẩu cũ
        }

        // Bước 4: Nếu đúng, mã hóa mật khẩu MỚI
        String newPasswordHash = hashPassword(newPassword);

        // Bước 5: Gọi DAO để cập nhật
        return dao.changePassword(accountID, newPasswordHash);
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
