import java.util.*;
// คลาส User สำหรับผู้ใช้ทั่วไป
// ผู้ใช้จะเข้าสู่ระบบโดยใช้ข้อมูลการ์ดที่ Admin สร้างไว้
class User extends UserCard implements UserManagement, RoomManagement {
    public User(String username, String cardId, int cardNumber, int expiryHours, int roomNumber, String accessLevel) {
        super(username, cardId, cardNumber, expiryHours, roomNumber, accessLevel);
    }
   
    @Override
    public boolean verifyLogin(String username, String password) {
        return this.username.equals(username) && this.cardId.equals(password);
    }
   
    @Override
    public boolean verifyRoomAccess(String caradId, int roomNumber) {
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
        String destinationFloor;
        int userFloor = Utils.getFloorFromRoom(this.roomNumber);
        int destinationFloorInt = 0; // กำหนดค่าเริ่มต้น
        
        while (true) {
            System.out.print("กรุณาระบุชั้นที่ต้องการขึ้น (เช่น Normal, Medium, High): ");
            destinationFloor = scanner.nextLine();
            if (destinationFloor.equals("Normal") || destinationFloor.equals("Medium") || destinationFloor.equals("High")) {
                System.out.println("User " + username + " ใช้ลิฟต์ไปชั้น " + destinationFloor);

                if (destinationFloor.equals("Normal")) {
                    destinationFloorInt = 1;
                } else if (destinationFloor.equals("Medium")) {
                    destinationFloorInt = 2;
                } else if (destinationFloor.equals("High")) {
                    destinationFloorInt = 3;
                }

                AuditLog.getInstance().logAccess(username, destinationFloorInt, "Elevator access");
                break;
            } else {
                System.out.println("ไม่สามารถขึ้นลิฟต์ไปชั้น " + destinationFloor + " ได้ (ห้องของคุณอยู่ชั้น " + userFloor + ") กรุณาลองใหม่อีกครั้ง");
            }
        }
    }
   
    // สำหรับการเข้าสู่ระบบ เราจะให้วนลูปจนกว่าผู้ใช้จะป้อนรหัสบัตรถูกต้อง
    public void login(Scanner scanner) {
        while (true) {
            System.out.println("พิมพ์ชื่อผู้ใช้: ");
            System.out.print(">>");
            System.out.println("");
            String inputUsername = scanner.nextLine();
            System.out.println("พิมพ์รหัสบัตร: ");
            System.out.print(">>");
            String cardInput = scanner.nextLine();
            System.out.println("");
            if (this.verifyLogin(inputUsername, cardInput)) {
                System.out.println("User " + username + " เข้าสู่ระบบเรียบร้อยแล้ว");
                System.out.println("");
                AuditLog.getInstance().logAccess(username, cardNumber, "User login");
                System.out.println("");
                break;
            } else {
                System.out.println("");
                System.out.println("ชื่อผู้ใช้หรือรหัสบัตรไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง");
                System.out.println("");
            }
        }
    }
}
