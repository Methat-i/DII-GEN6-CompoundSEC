import java.util.*;

public class AuditLog {
    private static volatile AuditLog uniqueInstance;
    private List<String> logs = new ArrayList<>();

    private AuditLog() {}

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

    public void logAccess(String username, int location, String action) {
        logs.add(username + " " + action + " ที่ " + location + " เวลา " + new Date());
    }

    public void printAllLogs() {
        for (String log : logs) {
            System.out.println(log);
        }
    }
}
