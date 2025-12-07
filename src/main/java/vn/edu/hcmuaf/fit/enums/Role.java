package vn.edu.hcmuaf.fit.enums;

public enum Role {

    CUSTOMER("Customer"),
    ADMIN("Admin");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Hàm chuyển từ String (DB) sang Enum (Java)
    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.value.equalsIgnoreCase(value)) {
                return role;
            }
        }
        return CUSTOMER; // Mặc định nếu không tìm thấy
    }
}