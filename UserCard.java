// Abstract Class สำหรับเก็บข้อมูลบัตรผู้ใช้ (UserCard)
// ใช้เป็น template ให้คลาสลูก implement เมธอด accessRoom()
abstract class UserCard {
    protected String username;       // ชื่อผู้ใช้
    protected String cardId;         // รหัสบัตร
    protected int cardNumber;        // หมายเลขบัตร (4 หลัก)
    protected int expiryHours;       // จำนวนชั่วโมงที่ใช้ได้
    protected int roomNumber;        // หมายเลขห้องที่ได้รับอนุญาต
    protected String accessLevel;    // Normal, Medium, High

    public UserCard(String username, String cardId, int cardNumber, int expiryHours, int roomNumber, String accessLevel) {
        this.username = username;
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.expiryHours = expiryHours;
        this.roomNumber = roomNumber;
        this.accessLevel = accessLevel;
    }
   
    // Abstract method สำหรับการเข้าห้อง
    public abstract void accessRoom();
}
