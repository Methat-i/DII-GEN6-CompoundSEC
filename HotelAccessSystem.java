import java.util.*;;
// คลาสหลักสำหรับควบคุมระบบ HotelAccessSystem
// ผู้ดูแล (Admin) สร้างบัตรและผู้ใช้ (User) จะเข้าสู่ระบบโดยใช้การ์ดที่ Admin สร้างไว้
public class HotelAccessSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       
        while (true) {
            System.out.print("");
            System.out.println("เลือกการใช้งาน: User (U/u) หรือ Admin (A/a) หรือ Q/q เพื่อออก");
            System.out.print("");
            String choice = scanner.nextLine();
            System.out.print("");
            if (choice.equalsIgnoreCase("Q")) {System.out.print("");
                System.out.println("ออกจากระบบ");
                break;
            } else if (choice.equalsIgnoreCase("A")) {
                // เข้าสู่ระบบ Admin
                System.out.println("กรุณาพิมพ์รหัส Admin:");
                String adminPass = scanner.nextLine();
                System.out.print("");
                Admin admin = new Admin("Admin", "AdminCard", "2099-12-31", 0);
                if (admin.verifyLogin("Admin", adminPass)) {
                    System.out.println("Admin เข้าสู่ระบบเรียบร้อยแล้ว");
                    System.out.print("");
                    // Loop สำหรับเมนูของ Admin
                    while (true) {
                        System.out.println("กรุณาเลือกตัวเลือกของ Admin:");
                        System.out.print("");
                        System.out.println("0: สร้างบัตรใหม่");
                        System.out.print("");
                        System.out.println("1: แก้ไข/ลบบัตร");
                        System.out.print("");
                        System.out.println("2: แสดงประวัติการใช้งาน");
                        System.out.print("");
                        System.out.println("3: แสดงสถานะห้อง");
                        System.out.print("");
                        System.out.println("4: ปรับปรุงสถานะห้อง");
                        System.out.print("");
                        System.out.println("Q: ออกจาก Admin");
                        System.out.print("");
                        String adminOption = scanner.nextLine();
                        System.out.print("");
                        if(adminOption.equalsIgnoreCase("Q")){
                            System.out.print("");
                            System.out.println("ออกจากระบบ Admin");
                            System.out.print("");
                            break;
                        }
                       
                        switch(adminOption) {
                            case "0":
                            System.out.print("");
                                admin.createCard(scanner);
                                break;
                            case "1":
                            System.out.print("");
                                admin.modifyOrDeleteCard(scanner);
                                break;
                            case "2":
                            System.out.print("");
                                admin.showHistory(scanner);
                                break;
                            case "3":
                            System.out.print("");
                                admin.showRoomStatus();
                                break;
                            case "4":
                            System.out.print("");
                                admin.updateRoomStatus(scanner);
                                break;
                            default:
                            System.out.print("");
                                System.out.println("ตัวเลือกไม่ถูกต้อง");
                        }
                    }
                } else {
                    System.out.println("รหัส Admin ผิด");
                }
            } else if (choice.equalsIgnoreCase("U")) {
                // ระบบเข้าสู่ระบบ User โดยให้ผู้ใช้ป้อนรหัสบัตรที่ Admin สร้างไว้
                System.out.print("");
                System.out.println("=== ระบบเข้าสู่ระบบ User ===");
                System.out.print("");
                System.out.print("พิมพ์รหัสบัตรของคุณ: ");
               
                String userCardId = scanner.nextLine();
                System.out.print("");
                // ตรวจสอบว่ามีการ์ดที่สร้างไว้หรือไม่
                if (!Admin.cardDatabase.containsKey(userCardId)) {
                    System.out.println("ไม่พบการ์ด กรุณาติดต่อ Admin เพื่อสร้างการ์ด");
                    continue;
                }
                // ดึงข้อมูลการ์ดของผู้ใช้จาก cardDatabase
                UserCard userCard = Admin.cardDatabase.get(userCardId);
               
                // วนลูปการเข้าสู่ระบบจนกว่าผู้ใช้จะป้อนรหัสบัตรถูกต้อง
                if (userCard instanceof User) {
                    ((User) userCard).login(scanner);
                    // ให้ผู้ใช้ใช้ลิฟต์
                    ((User) userCard).useElevator(scanner);
                    // ใช้ Decorator เพื่อตรวจสอบการเข้ารหัสแบบเวลาของบัตร
                    UserCard encryptedUserCard = new TimeEncryptedCard(userCard);
                    encryptedUserCard.accessRoom();
                } else {
                    System.out.print("");
                    System.out.println("ข้อมูลการ์ดไม่ถูกต้องสำหรับ User");
                }
            } else {
                System.out.print("");
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่");
            }
        }
        scanner.close();
    }
}
