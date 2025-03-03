// Interface สำหรับจัดการผู้ใช้
interface UserManagement {
    boolean verifyLogin(String username, String password);
}


// Interface สำหรับจัดการสิทธิ์เข้าห้อง
interface RoomManagement {
    boolean verifyRoomAccess(String cardId, int roomNumber);
}


// Abstract Class สำหรับเก็บข้อมูลบัตรผู้ใช้ (UserCard)
// ใช้เป็น template ให้คลาสลูก implement เมธอด accessRoom()
abstract class UserCard {
    protected String username;       // ชื่อผู้ใช้
    protected String cardId;         // รหัสบัตร
    protected String expiryDate;     // วันหมดอายุของบัตร
    protected int roomNumber;        // หมายเลขห้องที่ได้รับอนุญาต


    public UserCard(String username, String cardId, String expiryDate, int roomNumber) {
        this.username = username;
        this.cardId = cardId;
        this.expiryDate = expiryDate;
        this.roomNumber = roomNumber;
    }
   
    // Abstract method สำหรับการเข้าห้อง
    public abstract void accessRoom();


   
}
