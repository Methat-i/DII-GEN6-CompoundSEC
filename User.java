import java.util.Date;
import java.util.Scanner;

public class User extends UserCard {
    public User(String username, int hours, int roomNumber, String accessLevel) {
        super(username, hours, roomNumber, accessLevel);
    }

    @Override
    public void accessRoom() {
        System.out.println(username + " เข้าห้อง " + roomNumber + " เวลา " + new Date());
        AuditLog.getInstance().logAccess(username, roomNumber, "เข้าห้อง");
    }

    public void useElevator() {
        System.out.println(username + " ใช้ลิฟต์ไปยังชั้น " + Utils.getFloorFromRoom(roomNumber) + " เวลา " + new Date());
        AuditLog.getInstance().logAccess(username, roomNumber, "ใช้ลิฟต์");
    }

    public void login(Scanner scanner) {
        System.out.print("กรอกชื่อผู้ใช้: ");
        String inputUsername = scanner.nextLine();

        System.out.print("กรอกรหัสบัตร: ");
        String inputPassword = scanner.nextLine();

        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            System.out.println("เข้าสู่ระบบสำเร็จ");
            AuditLog.getInstance().logAccess(username, 0, "เข้าสู่ระบบ");
        } else {
            System.out.println("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
        }
    }
}
