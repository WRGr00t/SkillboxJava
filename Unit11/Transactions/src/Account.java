import java.util.concurrent.atomic.AtomicLong;

public class Account {
    private AtomicLong money;
    private String accNumber;
    private boolean isBlocked;

    public Account(AtomicLong money, String accNumber, boolean isBlocked) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlocked = isBlocked;
    }

    public Account(AtomicLong money, String accNumber) {
        this(money, accNumber, false);
    }

    public Account() {
    }

    public AtomicLong getMoney() {
        return money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public synchronized void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public synchronized void addMoney(long money) {
        this.money.addAndGet(money);
    }

    public synchronized void deductMoney(long money) {
        this.money.addAndGet(-money);
    }
}
