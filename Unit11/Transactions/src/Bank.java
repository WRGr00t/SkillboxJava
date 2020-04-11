import Exceptions.AccountNotExistsException;
import Exceptions.BlockedAccountException;
import Exceptions.InsufficientFundsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    public Bank(int bankSize) {
        for (int i = 0; i < bankSize; i++) {
            AtomicLong sum = new AtomicLong(1_000);// * Math.random()));
            Account account = new Account(sum, String.valueOf(i));
            accounts.put(String.valueOf(i), account);
        }
        System.out.printf("Сгенерирован банк с размером %d%n", accounts.size());
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
    /*public boolean transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException, AccountNotExistsException {
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
                            if (fromAccount.getMoney() >= amount) {
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
                            if (fromAccount.getMoney() >= amount) {
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
    }*/
    private void doTransfer(final Account fromAcct, final Account toAcct, final long amount) throws InsufficientFundsException {
        if (fromAcct.getMoney().longValue() < amount) {
            throw new InsufficientFundsException(fromAcct.getAccNumber());
        } else {
            fromAcct.deductMoney(amount);
            fromAcct.addOperation(toAcct, -amount);
            toAcct.addMoney(amount);
            toAcct.addOperation(fromAcct, amount);
        }
    }

    public boolean transferMoney(final Account fromAcct, final Account toAcct, final long amount) {
        boolean isDone = false;
        int fromId = Integer.parseInt(fromAcct.getAccNumber());
        int toId = Integer.parseInt(toAcct.getAccNumber());
        if (fromAcct.equals(toAcct)) {
            return false;
        }
        Object lock1 = fromId < toId ? fromAcct : toAcct;
        Object lock2 = fromId < toId ? toAcct : fromId;
        try {
            synchronized (lock1) {
                synchronized (lock2) {
                    if (fromAcct.isBlocked() || (toAcct.isBlocked())) {
                        throw new BlockedAccountException();
                    } else {
                        doTransfer(fromAcct, toAcct, amount);
                        isDone = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDone;
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public AtomicLong getBalance(String accountNum) throws AccountNotExistsException {
        Account account = getAccount(accountNum);
        return account.getMoney();
    }

    public long getAllMoney() {
        return accounts.values().stream()
                .mapToLong(acc -> acc.getMoney().get())
                .sum();
    }

    public Account getAccount(String accNumber) throws AccountNotExistsException {
        return accounts.values().stream()
                .filter(a -> a.getAccNumber().equals(accNumber))
                .findAny()
                .orElseThrow(() -> new AccountNotExistsException(accNumber));
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
}
