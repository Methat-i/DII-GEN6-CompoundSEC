// ฟังก์ชันช่วยสำหรับตรวจสอบชั้นของห้องจากหมายเลขห้อง
// เช่น ห้อง 305 จะได้ชั้น 3 เพราะ 305/100 = 3
public class Utils {
    public static int getFloorFromRoom(int roomNumber) {
        return roomNumber / 100;
    }
}
