// ตัวอย่าง Decorator สำหรับเพิ่มการตรวจสอบเวลาการเข้าระบบ
public class TimeEncryptedCard extends CardDecorator {
    public TimeEncryptedCard(UserCard decoratedCard) {
        super(decoratedCard);
    }
    
    @Override
    public void accessRoom() {
        System.out.println("ตรวจสอบการเข้ารหัสแบบเวลาของบัตร...");
        super.accessRoom();
    }
}
