import java.util.*;
// คลาส Admin สำหรับจัดการบัตรและประวัติการใช้งาน
// ในที่นี้ Admin จะเป็นผู้สร้างบัตรและเก็บบัตรไว้ในระบบ (cardDatabase)
public class Admin extends UserCard implements UserManagement, RoomManagement {
    // ใช้ static Map เพื่อให้ข้อมูลบัตรที่สร้างโดย Admin สามารถเข้าถึงได้จากทุกที่
    public static Map<String, UserCard> cardDatabase = new HashMap<>();
   
    // Map สำหรับเก็บสถานะของห้อง (key: room number, value: true = เต็ม, false = ว่าง)
    private Map<Integer, Boolean> roomStatus = new HashMap<>();
   
    public Admin(String username, String cardId, int cardNumber, int expiryHours, int roomNumber, String accessLevel) {
        super(username, cardId, cardNumber, expiryHours, roomNumber, accessLevel);
        initializeRooms();
    }
   
    private void initializeRooms() {
        // ตั้งค่าเริ่มต้นให้ทุกห้องว่าง
        for(int i = 201; i <= 210; i++) roomStatus.put(i, false); // Normal floor
        for(int i = 301; i <= 310; i++) roomStatus.put(i, false); // Medium floor
        for(int i = 401; i <= 410; i++) roomStatus.put(i, false); // High floor
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
   
    // 0: ฟังก์ชันสร้างบัตรใหม่
    public void createCard(Scanner scanner) {
        System.out.println("=== สร้างบัตรใหม่ ===");
        System.out.print("พิมพ์ชื่อ: ");
        String newName = scanner.nextLine();
        
        // สร้างหมายเลขบัตร 4 หลักแบบสุ่ม
        int newCardNumber = new Random().nextInt(9000) + 1000;
        
        System.out.print("กำหนดรหัสบัตร: ");
        String newCardId = scanner.nextLine();
        
        System.out.print("กำหนดจำนวนชั่วโมงที่ใช้ได้: ");
        int newExpiryHours = Integer.parseInt(scanner.nextLine());

        System.out.println("เลือกระดับสิทธิ์:");
        System.out.println("1: Normal Level (ห้อง 201-210)");
        System.out.println("2: Medium Level (ห้อง 301-310)");
        System.out.println("3: High Level (ห้อง 401-410)");
        int levelChoice = Integer.parseInt(scanner.nextLine());
        String accessLevel = "";
        List<Integer> availableRooms = new ArrayList<>();

        switch(levelChoice) {
            case 1:
                accessLevel = "Normal";
                for(int i = 201; i <= 210; i++) {
                    if(!roomStatus.get(i)) availableRooms.add(i);
                }
                break;
            case 2:
                accessLevel = "Medium";
                for(int i = 301; i <= 310; i++) {
                    if(!roomStatus.get(i)) availableRooms.add(i);
                }
                break;
            case 3:
                accessLevel = "High";
                for(int i = 401; i <= 410; i++) {
                    if(!roomStatus.get(i)) availableRooms.add(i);
                }
                break;
        }

        if(availableRooms.isEmpty()) {
            System.out.println("ไม่มีห้องว่างในระดับที่เลือก");
            return;
        }

        System.out.println("ห้องที่ว่าง: " + availableRooms);
        System.out.print("เลือกหมายเลขห้อง: ");
        int newRoom = Integer.parseInt(scanner.nextLine());

        if(!availableRooms.contains(newRoom)) {
            System.out.println("หมายเลขห้องไม่ถูกต้องหรือห้องไม่ว่าง");
            return;
        }

        UserCard newCard = new User(newName, newCardId, newCardNumber, newExpiryHours, newRoom, accessLevel);
        cardDatabase.put(newCardId, newCard);
        roomStatus.put(newRoom, true); // อัปเดตสถานะห้องเป็นเต็ม

        AuditLog.getInstance().logEvent(String.format(
            "Admin สร้างบัตร: หมายเลข %d, ชื่อ %s, ห้อง %d, ระดับ %s, เวลา %d ชม.",
            newCardNumber, newName, newRoom, accessLevel, newExpiryHours
        ));
        
        System.out.println("สร้างบัตรเรียบร้อยแล้ว");
        System.out.println("หมายเลขบัตรของคุณคือ: " + newCardNumber);
    }
   
    // 1: ฟังก์ชันแก้ไข/ลบบัตร
    public void modifyOrDeleteCard(Scanner scanner) {
        System.out.println("=== แก้ไข/ลบบัตร ===");
        if(cardDatabase.isEmpty()) {
            System.out.println("ยังไม่มีบัตรในระบบ");
            return;
        }

        // แสดงรายการบัตรทั้งหมด
        for (UserCard card : cardDatabase.values()) {
            System.out.printf("หมายเลขบัตร: %d | ชื่อ: %s | ห้อง: %d | เวลาคงเหลือ: %d ชม.\n",
                card.cardNumber, card.username, card.roomNumber, card.expiryHours);
        }

        System.out.println("พิมพ์ 'แก้ไข' เพื่อแก้ไขบัตร หรือ 'ลบ' เพื่อทำการลบบัตร");
        String action = scanner.nextLine();
        System.out.print("พิมพ์หมายเลขบัตรที่ต้องการ: ");
        int targetCardNumber = Integer.parseInt(scanner.nextLine());

        // หา cardId จากหมายเลขบัตร
        String targetCardId = null;
        for(Map.Entry<String, UserCard> entry : cardDatabase.entrySet()) {
            if(entry.getValue().cardNumber == targetCardNumber) {
                targetCardId = entry.getKey();
                break;
            }
        }

        if(targetCardId == null) {
            System.out.println("ไม่พบหมายเลขบัตรในระบบ");
            return;
        }

        if(action.equalsIgnoreCase("แก้ไข")) {
            UserCard card = cardDatabase.get(targetCardId);
            roomStatus.put(card.roomNumber, false); // คืนสถานะห้องเดิมเป็นว่าง

            System.out.print("พิมพ์หมายเลขห้องใหม่ (ตามสิทธิ์เดิม): ");
            int newRoom = Integer.parseInt(scanner.nextLine());
            System.out.print("กำหนดจำนวนชั่วโมงใหม่: ");
            int newHours = Integer.parseInt(scanner.nextLine());

            card.roomNumber = newRoom;
            card.expiryHours = newHours;
            roomStatus.put(newRoom, true); // อัปเดตสถานะห้องใหม่เป็นเต็ม

            AuditLog.getInstance().logEvent(String.format(
                "Admin แก้ไขบัตรหมายเลข %d: ห้องใหม่ %d, เวลาใหม่ %d ชม.",
                card.cardNumber, newRoom, newHours
            ));
            
            System.out.println("แก้ไขบัตรเรียบร้อยแล้ว");
        } else if(action.equalsIgnoreCase("ลบ")) {
            UserCard card = cardDatabase.get(targetCardId);
            roomStatus.put(card.roomNumber, false); // คืนสถานะห้องเป็นว่าง
            cardDatabase.remove(targetCardId);
            
            AuditLog.getInstance().logEvent("Admin ลบบัตรหมายเลข " + card.cardNumber);
            System.out.println("ลบบัตรเรียบร้อยแล้ว");
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
        for (Map.Entry<Integer, Boolean> entry : roomStatus.entrySet()) {
            System.out.println("ห้อง " + entry.getKey() + ": " + (entry.getValue() ? "เต็ม" : "ว่าง"));
        }
    }
   
    // ฟังก์ชันสำหรับปรับปรุงสถานะห้อง
    public void updateRoomStatus(Scanner scanner) {
        System.out.println("");
        System.out.print("พิมพ์หมายเลขห้องที่ต้องการอัปเดต: ");
        int room = Integer.parseInt(scanner.nextLine());
        System.out.print("พิมพ์สถานะ (1 = เต็ม, 0 = ว่าง): ");
        int status = Integer.parseInt(scanner.nextLine());
        roomStatus.put(room, status == 1);
        System.out.println("");
        AuditLog.getInstance().logEvent("Update room " + room + " status to " + status);
        System.out.println("");
        System.out.println("อัปเดตสถานะห้องเรียบร้อยแล้ว");
    }
}


