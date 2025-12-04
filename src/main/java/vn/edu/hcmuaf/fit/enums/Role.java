package vn.edu.hcmuaf.fit.enums;

public enum Role {
    // Định nghĩa các hằng số và giá trị tương ứng trong Database
    CUSTOMER(0),    // 0 tương ứng với User thường
    ADMIN(1);  // 1 tương ứng với Admin
//    MANAGER(2); // 2 thêm vai trò quản lý (nếu cần)

    private final int value;

    // Constructor của Enum (phải là private)
    Role(int value) {
        this.value = value;
    }

    // Getter để lấy giá trị số (dùng khi lưu vào DB)
    public int getValue() {
        return value;
    }

    // Phương thức Static để chuyển từ số (DB) sang Enum (Java)
    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.value == value) {
                return role;
            }
        }
        // Nếu giá trị trong DB không khớp cái nào, trả về null hoặc mặc định là USER
        return CUSTOMER;
    }
}