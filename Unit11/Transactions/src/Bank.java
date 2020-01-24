import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    public Bank(HashMap<String, Account> accounts) {
        this.accounts = accounts;

        System.out.println("Создан банк на " + accounts.size() + " счетов.");
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            System.out.println("ID =  " + entry.getKey() + " значение - #" + entry.getValue().getAccNumber() + " c " + entry.getValue().getMoney());
        }
    }

    public Bank(int accountAmount) {
        initBank(accountAmount);
        System.out.println("Создан банк на " + accounts.size() + " счетов.");
        /*for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            System.out.println("ID =  " + entry.getKey() + " значение - #" + entry.getValue().getAccNumber() + " c " + entry.getValue().getMoney());
        }*/
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public boolean transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Account fromAccount = getAccount(fromAccountNum);
        Account toAccount = getAccount(toAccountNum);
        boolean isDone = false;
        try {
            if (fromAccount.getLock().tryLock(1, TimeUnit.SECONDS) && toAccount.getLock().tryLock(1, TimeUnit.SECONDS)) {
                isDone = doTransfer(fromAccount, toAccount, amount);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            fromAccount.getLock().unlock();
            toAccount.getLock().unlock();
        }

        if ((amount > 50_000) && isDone) {
            if (isFraud(fromAccountNum, toAccountNum, amount)) {
                setBlocked(fromAccountNum);
                setBlocked(toAccountNum);
            }
        }
        return isDone;
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        Account account = getAccount(accountNum);
        return account.getMoney().longValue();
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public Account getAccount(String accNumber) {
        Account result = new Account();
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().getAccNumber().equals(accNumber)) {
                result = entry.getValue();
            }
        }
        return result;
    }

    public synchronized void setBlocked(String accountNum) {
        getAccount(accountNum).setBlocked(true);
    }

    public void initBank(int bankSize) {
        for (int i = 0; i < bankSize; i++) {
            AtomicLong atomicLong = new AtomicLong((long) (100_000 * Math.random()));

            Account account = new Account(atomicLong, Integer.toString(i));
            accounts.put(account.getAccNumber(), account);
        }
    }

    public long getAllMoney() {
        long result = 0;
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            result += entry.getValue().getMoney().longValue();
        }
        return result;
    }

    public List<Account> getAllBlockAcc() {
        List<Account> resultList = new ArrayList<>();
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().isBlocked()) {
                resultList.add(entry.getValue());
            }
        }
        return resultList;
    }

    public boolean doTransfer(Account fromAccount, Account toAccount, long amount) {
        boolean isDoneResult = false;
        if (!(fromAccount.isBlocked() || toAccount.isBlocked())) {
            if (fromAccount.getMoney().longValue() >= amount) {
                fromAccount.deductMoney(amount);
                toAccount.addMoney(amount);
                isDoneResult = true;
            } else {
                System.out.println("Недостаточно средств");
                isDoneResult = false;
            }
        }
        return isDoneResult;
    }
}
