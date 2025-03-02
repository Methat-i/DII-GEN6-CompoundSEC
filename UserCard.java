import java.util.Random;

// Abstract Class สำหรับเก็บข้อมูลบัตรผู้ใช้
public abstract class UserCard {
    protected String username;
    protected String cardNumber;
    protected String password;
    protected int hours;  // เปลี่ยนจากวันเป็นชั่วโมง
    protected int roomNumber;
    protected String accessLevel;

    public UserCard(String username, int hours, int roomNumber, String accessLevel) {
        this.username = username;
        this.cardNumber = generateCardNumber();
        this.password = this.cardNumber; // ใช้หมายเลขบัตรเป็นรหัสผ่าน
        this.hours = hours;
        this.roomNumber = roomNumber;
        this.accessLevel = accessLevel;
    }

    public UserCard(String username2, String cardId, String expiryDate, int roomNumber2) {
        //TODO Auto-generated constructor stub
    }

    private String generateCardNumber() {
        Random rand = new Random();
        return String.format("%04d", rand.nextInt(10000));
    }

    public abstract void accessRoom();

    public int calculatePrice() {
        switch (accessLevel) {
            case "Normal": return hours * 5;
            case "Medium": return hours * 10;
            case "High": return hours * 15;
            default: return 0;
        }
    }
}

