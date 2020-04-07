import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Account
{
    private AtomicLong money;
    private String accNumber;
    private boolean isBlocked;
    private ConcurrentLinkedQueue<String> history;
    private MyLock lock;

    public Account(AtomicLong money, String accNumber, boolean isBlocked) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlocked = isBlocked;
        history = new ConcurrentLinkedQueue<>();
        this.lock = new MyLock();
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

    public ConcurrentLinkedQueue<String> getHistory() {
        return history;
    }

    public void setLock(MyLock lock) {
        this.lock = lock;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void addMoney(long amount) {
        this.money.addAndGet(amount);
    }

    public void deductMoney(long amount) {
        this.money.addAndGet(-amount);
    }

    public void addOperation(Account account, long amount){
        String string = String.format("# %d account=%s sum=%d remains=%d", history.size(), account.accNumber, amount, getMoney().longValue());
        history.add(string);
    }

}
