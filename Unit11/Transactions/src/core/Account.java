package core;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private AtomicLong money;
    private String accNumber;
    private boolean isBlocked;
    private Lock lock;

    public Account(AtomicLong money, String accNumber, boolean isBlocked) {
        this.money = money;
        this.accNumber = accNumber;
        this.isBlocked = isBlocked;
        lock = new ReentrantLock();
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

    public Lock getLock() {
        return lock;
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
