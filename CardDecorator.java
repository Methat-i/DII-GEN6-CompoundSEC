
// Decorator Pattern สำหรับเพิ่มคุณสมบัติการเข้ารหัสแบบเวลาลงในบัตร
// โดยไม่ต้องแก้ไขคลาส UserCard เดิม
public abstract class CardDecorator extends UserCard {
    protected UserCard decoratedCard;
   
    public CardDecorator(UserCard decoratedCard) {
        super(decoratedCard.username, decoratedCard.cardId, decoratedCard.cardNumber, decoratedCard.expiryHours, decoratedCard.roomNumber, decoratedCard.accessLevel);
        this.decoratedCard = decoratedCard;
    }
   
    @Override
    public void accessRoom() {
        decoratedCard.accessRoom();
    }
}



