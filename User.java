
import java.util.*;
// คลาส User สำหรับผู้ใช้ทั่วไป
// ผู้ใช้จะเข้าสู่ระบบโดยใช้ข้อมูลการ์ดที่ Admin สร้างไว้
class User extends UserCard implements UserManagement, RoomManagement {
    public User(String username, String cardId, String expiryDate, int roomNumber) {
        super(username, cardId, expiryDate, roomNumber);
    }
    
    @Override
    public boolean verifyLogin(String username, String password) {
        // ใช้ cardId เป็น password สำหรับตรวจสอบ
        return this.cardId.equals(password);
    }
    
    @Override
    public boolean verifyRoomAccess(String cardId, int roomNumber) {
        return this.cardId.equals(cardId) && this.roomNumber == roomNumber;
    }
    
    // ปรับปรุง method accessRoom ให้วนลูปรับหมายเลขห้องจนกว่าจะถูกต้อง
    @Override
    public void accessRoom() {
        Scanner scanner = new Scanner(System.in);
        int targetRoom;
        while (true) {
            System.out.print("พิมพ์หมายเลขห้องที่ต้องการเข้าจริง ๆ: ");
            targetRoom = Integer.parseInt(scanner.nextLine());
            if (targetRoom == this.roomNumber) {
                int floor = Utils.getFloorFromRoom(roomNumber);
                System.out.println("User " + username + " เข้าห้องหมายเลข " + roomNumber + " (ชั้น " + floor + ")");
                AuditLog.getInstance().logAccess(username, roomNumber, "User access");
                break;  // ถ้าตรงกับบัตร ให้ออกจาก loop
            } else {
                System.out.println("หมายเลขห้องไม่ตรงกับบัตรของคุณ กรุณาลองใหม่อีกครั้ง");
            }
        }
    }
    
    // ปรับปรุงฟังก์ชันการขึ้นลิฟต์ให้วนลูปจนกว่าจะเลือกชั้นที่ถูกต้อง
    public void useElevator(Scanner scanner) {
        int destinationFloor;
        int userFloor = Utils.getFloorFromRoom(this.roomNumber);
        while (true) {
            System.out.print("กรุณาระบุชั้นที่ต้องการขึ้น (เช่น -1, 0, 1, 2, 3, 4): ");
            destinationFloor = Integer.parseInt(scanner.nextLine());
            if(destinationFloor == -1 || destinationFloor == 0 || destinationFloor == userFloor) {
                System.out.println("User " + username + " ใช้ลิฟต์ไปชั้น " + destinationFloor);
                AuditLog.getInstance().logAccess(username, destinationFloor, "Elevator access");
                break;
            } else {
                System.out.println("ไม่สามารถขึ้นลิฟต์ไปชั้น " + destinationFloor + " ได้ (ห้องของคุณอยู่ชั้น " + userFloor + ") กรุณาลองใหม่อีกครั้ง");
            }
        }
    }
    
    // สำหรับการเข้าสู่ระบบ เราจะให้วนลูปจนกว่าผู้ใช้จะป้อนรหัสบัตรถูกต้อง
    public void login(Scanner scanner) {
        while (true) {
            System.out.print("พิมพ์รหัสบัตรเพื่อเข้าสู่ระบบ: ");
            String cardInput = scanner.nextLine();
            if (this.verifyLogin(this.username, cardInput)) {
                System.out.println("User " + username + " เข้าสู่ระบบเรียบร้อยแล้ว");
                break;
            } else {
                System.out.println("รหัสบัตรไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง");
            }
        }
    }
}