// Interface สำหรับการจัดการการเข้าถึงห้อง (Room Management)
public interface RoomManagement {
    // method สำหรับตรวจสอบสิทธิ์เข้าห้อง (โดยอ้างอิงจากหมายเลขห้องในบัตร)
    boolean verifyRoomAccess(String cardId, int roomNumber);
}
