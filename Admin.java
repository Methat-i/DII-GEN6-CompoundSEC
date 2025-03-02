import java.util.*;
// คลาส Admin สำหรับจัดการบัตรและประวัติการใช้งาน
// ในที่นี้ Admin จะเป็นผู้สร้างบัตรและเก็บบัตรไว้ในระบบ (cardDatabase)
public class Admin extends UserCard implements UserManagement, RoomManagement {
    // ใช้ static Map เพื่อให้ข้อมูลบัตรที่สร้างโดย Admin สามารถเข้าถึงได้จากทุกที่
    public static Map<String, UserCard> cardDatabase = new HashMap<>();
    
    // Map สำหรับเก็บสถานะของห้อง (key: room number, value: 1 = เต็ม, 0 = ว่าง)
    private Map<Integer, Integer> roomStatus = new HashMap<>();
    
    public Admin(String username, String cardId, String expiryDate, int roomNumber) {
        super(username, cardId, expiryDate, roomNumber);
    }
    
    @Override
    public boolean verifyLogin(String username, String password) {
        // ตรวจสอบ Admin ด้วยรหัส "Admin012345"
        return password.equals("Admin012345");
    }
    
    @Override
    public boolean verifyRoomAccess(String cardId, int roomNumber) {
        // Admin มีสิทธิ์เข้าห้องทุกห้อง
        return true;
    }
    
    @Override
    public void accessRoom() {
        System.out.println("Admin เข้าห้องหมายเลข " + roomNumber);
        AuditLog.getInstance().logAccess(username, roomNumber, "Admin access");
    }
    
    // 0: ฟังก์ชันสำหรับสร้างบัตรใหม่โดย Admin
    public void createCard(Scanner scanner) {
        System.out.println("=== สร้างบัตรใหม่ ===");
        System.out.print("พิมพ์ชื่อ: ");
        String newName = scanner.nextLine();
        System.out.print("พิมพ์รหัสบัตร: ");
        String newCardId = scanner.nextLine();
        System.out.print("กำหนดวันหมดอายุ (yyyy-mm-dd): ");
        String newExpiry = scanner.nextLine();
        System.out.print("พิมพ์หมายเลขห้อง (เช่น 305): ");
        int newRoom = Integer.parseInt(scanner.nextLine());
        // สร้างบัตรใหม่ (ในที่นี้ใช้คลาส User เป็นตัวแทน)
        UserCard newCard = new User(newName, newCardId, newExpiry, newRoom);
        cardDatabase.put(newCardId, newCard);
        AuditLog.getInstance().logEvent("Create card: " + newCardId);
        System.out.println("สร้างบัตรเรียบร้อยแล้ว");
    }
    
    // 1: ฟังก์ชันสำหรับแก้ไขหรือ ลบบัตรโดย Admin
    public void modifyOrDeleteCard(Scanner scanner) {
        System.out.println("=== แก้ไข/ลบบัตร ===");
        if(cardDatabase.isEmpty()){
            System.out.println("ยังไม่มีบัตรในระบบ");
            return;
        }
        // แสดงรายชื่อบัตรทั้งหมด
        for (UserCard card : cardDatabase.values()) {
            System.out.println("ชื่อ: " + card.username + " | รหัสบัตร: " + card.cardId +
                    " | วันหมดอายุ: " + card.expiryDate + " | ห้อง: " + card.roomNumber);
        }
        System.out.println("พิมพ์ 'แก้ไข' เพื่อแก้ไขบัตร หรือ 'ลบ' เพื่อทำการลบบัตร");
        String action = scanner.nextLine();
        System.out.print("พิมพ์รหัสบัตรที่ต้องการ: ");
        String targetCardId = scanner.nextLine();
        
        if(!cardDatabase.containsKey(targetCardId)){
            System.out.println("ไม่พบรหัสบัตรในระบบ");
            return;
        }
        
        if(action.equalsIgnoreCase("แก้ไข")){
            UserCard card = cardDatabase.get(targetCardId);
            System.out.print("พิมพ์ชื่อใหม่: ");
            card.username = scanner.nextLine();
            System.out.print("กำหนดวันหมดอายุใหม่ (yyyy-mm-dd): ");
            card.expiryDate = scanner.nextLine();
            System.out.print("พิมพ์หมายเลขห้องใหม่: ");
            card.roomNumber = Integer.parseInt(scanner.nextLine());
            AuditLog.getInstance().logEvent("Modify card: " + targetCardId);
            System.out.println("แก้ไขบัตรเรียบร้อยแล้ว");
        } else if(action.equalsIgnoreCase("ลบ")){
            cardDatabase.remove(targetCardId);
            AuditLog.getInstance().logEvent("Delete card: " + targetCardId);
            System.out.println("ลบบัตรเรียบร้อยแล้ว");
        } else {
            System.out.println("คำสั่งไม่ถูกต้อง");
        }
    }
    
    // 2: ฟังก์ชันสำหรับแสดงประวัติการใช้งาน
    public void showHistory(Scanner scanner) {
        System.out.println("=== แสดงประวัติการใช้งาน ===");
        System.out.println("พิมพ์ '1' เพื่อแสดงประวัติทั้งหมด");
        System.out.println("พิมพ์ '2' เพื่อแสดงประวัติด้วยรหัสบัตรหรือชื่อ");
        String subChoice = scanner.nextLine();
        if(subChoice.equals("1")){
            AuditLog.getInstance().printAllLogs();
        } else if(subChoice.equals("2")){
            System.out.print("พิมพ์รหัสบัตรหรือชื่อ: ");
            String searchKey = scanner.nextLine();
            AuditLog.getInstance().printLog(searchKey);
        } else {
            System.out.println("คำสั่งไม่ถูกต้อง");
        }
    }
    
    // ฟังก์ชันสำหรับแสดงสถานะห้อง (เต็ม = 1, ว่าง = 0)
    public void showRoomStatus() {
        System.out.println("=== สถานะห้อง ===");
        for (Map.Entry<Integer, Integer> entry : roomStatus.entrySet()) {
            System.out.println("ห้อง " + entry.getKey() + ": " + (entry.getValue() == 1 ? "เต็ม" : "ว่าง"));
        }
    }
    
    // ฟังก์ชันสำหรับปรับปรุงสถานะห้อง
    public void updateRoomStatus(Scanner scanner) {
        System.out.print("พิมพ์หมายเลขห้องที่ต้องการอัปเดต: ");
        int room = Integer.parseInt(scanner.nextLine());
        System.out.print("พิมพ์สถานะ (1 = เต็ม, 0 = ว่าง): ");
        int status = Integer.parseInt(scanner.nextLine());
        roomStatus.put(room, status);
        AuditLog.getInstance().logEvent("Update room " + room + " status to " + status);
        System.out.println("อัปเดตสถานะห้องเรียบร้อยแล้ว");
    }
}