import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account
{
    private long money;
    private String accNumber;
    private boolean isBlocked;
    private Lock lock;
    private ArrayList<String> history;

    public Account(long money, String accNumber, boolean isBlocked) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlocked = isBlocked;
        lock = new ReentrantLock();
        history = new ArrayList<>();
    }

    public Account(long money, String accNumber) {
        this(money, accNumber, false);
    }

    public Account() {
    }

    public long getMoney() {
        return money;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public Lock getLock() {
        return lock;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public synchronized void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public synchronized void addMoney(long money) {
        this.money += money;
    }

    public synchronized void deductMoney(long money) {
        this.money -= money;
    }

    public synchronized void addOperition(Account account, long amount){
        String string = String.format("# %d account=%s sum=%d remains=%d", history.size(), account.accNumber, amount, getMoney());
        history.add(string);
    }

}
