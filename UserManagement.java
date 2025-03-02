// Interface สำหรับการจัดการข้อมูลผู้ใช้ (User Management)
// ใช้สำหรับกำหนดรูปแบบ (structure) ที่คลาสอื่นต้องทำตาม

// Interface สำหรับจัดการผู้ใช้
public interface UserManagement {
    boolean verifyLogin(String username, String password);
}
