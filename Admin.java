import java.util.*;

public class Admin {
    public static Map<String, UserCard> cardDatabase = new HashMap<>();
    private List<String> adminLogs = new ArrayList<>();

    public void createCard(Scanner scanner) {
        System.out.print("กรอกชื่อผู้ใช้: ");
        String username = scanner.nextLine();
        
        System.out.print("กรอกจำนวนชั่วโมง: ");
        int hours = scanner.nextInt();
        
        System.out.print("เลือกสิทธิ์ (Normal/Medium/High): ");
        String accessLevel = scanner.next();
        
        System.out.print("เลือกหมายเลขห้อง: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        UserCard newCard = new User(username, hours, roomNumber, accessLevel);
        cardDatabase.put(newCard.cardNumber, newCard);

        System.out.println("สร้างบัตรสำเร็จ! หมายเลขบัตร: " + newCard.cardNumber + " รหัสบัตร: " + newCard.password);
        adminLogs.add("Admin สร้างบัตร " + newCard.cardNumber + " เวลา " + new Date());
    }

    public void modifyOrDeleteCard(Scanner scanner) {
        System.out.print("กรอกหมายเลขบัตร: ");
        String cardNumber = scanner.nextLine();

        if (!cardDatabase.containsKey(cardNumber)) {
            System.out.println("ไม่พบบัตร");
            return;
        }

        System.out.println("1: แก้ไขห้อง");
        System.out.println("2: แก้ไขจำนวนชั่วโมง");
        System.out.println("3: ลบบัตร");
        int choice = scanner.nextInt();
        scanner.nextLine();

        UserCard card = cardDatabase.get(cardNumber);
        if (choice == 1) {
            System.out.print("กรอกหมายเลขห้องใหม่: ");
            int newRoom = scanner.nextInt();
            card.roomNumber = newRoom;
            adminLogs.add("Admin แก้ไขห้องของบัตร " + cardNumber + " เป็น " + newRoom + " เวลา " + new Date());
        } else if (choice == 2) {
            System.out.print("กรอกจำนวนชั่วโมงใหม่: ");
            int newHours = scanner.nextInt();
            card.hours = newHours;
            adminLogs.add("Admin แก้ไขเวลาของบัตร " + cardNumber + " เป็น " + newHours + " ชั่วโมง เวลา " + new Date());
        } else if (choice == 3) {
            cardDatabase.remove(cardNumber);
            adminLogs.add("Admin ลบบัตร " + cardNumber + " เวลา " + new Date());
        }
    }

    public void showAdminLogs() {
        for (String log : adminLogs) {
            System.out.println(log);
        }
    }
}
