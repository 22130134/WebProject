package vn.edu.hcmuaf.fit.service;

import vn.edu.hcmuaf.fit.dao.CustomerDAO;
import vn.edu.hcmuaf.fit.model.Customer;

public class CustomerService {
    private static CustomerService instance;

    // Singleton Pattern
    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    private CustomerService() {
    }

    /**
     * Xử lý nghiệp vụ cập nhật thông tin
     */
    public boolean updateCustomerInfo(int accountId, String fullName, String phone, String address) {
        // (Optional) Tại đây bạn có thể Validate dữ liệu nếu cần
        // Ví dụ: if (phone.length() < 10) return false;

        // Gọi DAO để thực hiện update
        return new CustomerDAO().updateCustomerInfo(accountId, fullName, phone, address);
    }

    /**
     * Lấy thông tin customer
     */
    public Customer getCustomerByAccountId(int accountId) {
        return new CustomerDAO().getCustomerByAccountId(accountId);
    }
}