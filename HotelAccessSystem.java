import java.util.Scanner;

public class HotelAccessSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();

        while (true) {
            System.out.println("เลือก: User (U/u) | Admin (A/a) | ออก (Q/q)");
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("Q")) break;

            if (choice.equalsIgnoreCase("A")) {
                admin.createCard(scanner);
            } else if (choice.equalsIgnoreCase("U")) {
                System.out.println("เข้าสู่ระบบ User");
                System.out.print("หมายเลขบัตร: ");
                String cardNumber = scanner.nextLine();
                UserCard user = Admin.cardDatabase.get(cardNumber);

                if (user != null) {
                    ((User) user).login(scanner);
                    ((User) user).useElevator();
                    user.accessRoom();
                }
            }
        }
        scanner.close();
    }
}
