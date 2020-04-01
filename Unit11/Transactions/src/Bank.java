import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    //===== конструкторы =========
    public Bank(HashMap<String, Account> accounts) {
        this.accounts = accounts;

        System.out.printf("Создан банк с размером %d%n на основе Map", accounts.size());
    }

    public Bank(int bankSize) {
        for (int i = 0; i < bankSize; i++) {
            AtomicLong atomicLong = new AtomicLong(1_000);// * Math.random()));
            Account account = new Account(atomicLong, String.valueOf(i));
            accounts.put(String.valueOf(i), account);
        }
        System.out.printf("Сгенерирован банк с размером %d%n", accounts.size());
    }

    //====== методы банка ==========
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
        if (fromAccount.isBlocked() || toAccount.isBlocked() || fromAccount.equals(toAccount)) {
            return false;
        }

        if (fromAccountNum.compareTo(toAccountNum) > 0) {
            if (fromAccount.getLock().tryLock(100, TimeUnit.SECONDS)) {
                if (toAccount.getLock().tryLock(100, TimeUnit.SECONDS)) {
                    try {
                        String isDoneResult = "Платеж не проведен";
                        if (!(fromAccount.isBlocked() || toAccount.isBlocked())) {
                            if (fromAccount.getMoney().longValue() >= amount) {
                                fromAccount.deductMoney(amount);
                                toAccount.addMoney(amount);
                                isDoneResult = "Платеж проведен";
                            } else {
                                isDoneResult = "Недостаточно средств";
                            }
                        }
                        isDone = isDoneResult.equals("Платеж проведен");
                        System.out.println(isDoneResult);
                        Thread.sleep(10);
                    } finally {
                        fromAccount.getLock().unlock();
                        toAccount.getLock().unlock();
                    }
                } else {
                    fromAccount.getLock().unlock();
                    isDone = false;
                }
            }
        } else {
            if (toAccount.getLock().tryLock(100, TimeUnit.SECONDS)) {
                if (fromAccount.getLock().tryLock(100, TimeUnit.SECONDS)) {
                    try {
                        String isDoneResult = "Платеж не проведен";
                        if (!(fromAccount.isBlocked() || toAccount.isBlocked())) {
                            if (fromAccount.getMoney().longValue() >= amount) {
                                fromAccount.deductMoney(amount);
                                toAccount.addMoney(amount);
                                isDoneResult = "Платеж проведен";
                            } else {
                                isDoneResult = "Недостаточно средств";
                            }
                        }
                        isDone = isDoneResult.equals("Платеж проведен");
                        System.out.println(isDoneResult);
                        Thread.sleep(10);
                    } finally {
                        fromAccount.getLock().unlock();
                        toAccount.getLock().unlock();
                    }
                } else {
                    fromAccount.getLock().unlock();
                    isDone = false;
                }
            }

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

    public long getAllMoney() {
        long result = 0;
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            result += entry.getValue().getMoney().longValue();
        }
        return result;
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

    public HashMap<String, Account> getAccounts() {
        return accounts;
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

    public synchronized void setBlocked(String accountNum) {
        getAccount(accountNum).setBlocked(true);
    }
}
