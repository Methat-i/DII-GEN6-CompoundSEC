


import java.util.*;
// Singleton Pattern สำหรับบันทึก Audit Log
// เพื่อให้แน่ใจว่ามี instance เดียวที่ใช้บันทึกเหตุการณ์ในระบบ
public class AuditLog {
    private static volatile AuditLog uniqueInstance;
    private List<String> logs = new ArrayList<>();
   
    private AuditLog() { }
   
    public static AuditLog getInstance() {
        if (uniqueInstance == null) {
            synchronized (AuditLog.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new AuditLog();
                }
            }
        }
        return uniqueInstance;
    }
   
    public void logEvent(String event) {
        logs.add(event);
        System.out.println("Log event: " + event);
    }
   
    public void logAccess(String username, int floorOrRoom, String action) {
        String log = "User: " + username + ", Action: " + action + ", Floor/Room: " + floorOrRoom;
        logs.add(log);
        System.out.println("Log access: " + log);
    }
   
    // แสดงประวัติทั้งหมด
    public void printAllLogs() {
        System.out.println("=== ประวัติทั้งหมด ===");
        for (String log : logs) {
            System.out.println(log);
        }
    }
   
    // แสดงประวัติโดยค้นหาจากรหัสบัตรหรือชื่อ
    public void printLog(String key) {
        System.out.println("=== ประวัติสำหรับ " + key + " ===");
        for (String log : logs) {
            if (log.contains(key)) {
                System.out.println(log);
            }
        }
    }
}


