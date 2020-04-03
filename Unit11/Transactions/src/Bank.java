import Exceptions.AccountNotExistsException;
import Exceptions.BlockedAccountException;
import Exceptions.InsufficientFundsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
            long sum = 1_000;// * Math.random()));
            Account account = new Account(sum, String.valueOf(i));
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
        if (fromAcct.getMoney() < amount){
            throw new InsufficientFundsException(fromAcct.getAccNumber());
        } else {
            fromAcct.deductMoney(amount);
            fromAcct.addOperition(toAcct, -amount);
            toAcct.addMoney(amount);
            toAcct.addOperition(fromAcct, amount);
        }
    }

    public boolean transferMoney(final Account fromAcct, final Account toAcct, final long amount) {
        boolean isDone = false;
        int fromId = Integer.parseInt(fromAcct.getAccNumber());
        int toId = Integer.parseInt(toAcct.getAccNumber());
        if (fromAcct.equals(toAcct)){
            return false;
        }
        try {
            if (fromId < toId) {
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        if (fromAcct.isBlocked() || (toAcct.isBlocked())){
                            throw new BlockedAccountException();
                        } else {
                            doTransfer(fromAcct, toAcct, amount);
                            isDone = true;
                        }
                    }
                }
            } else {
                synchronized (toAcct) {
                    synchronized (fromAcct) {
                        if (fromAcct.isBlocked() || (toAcct.isBlocked())){
                            throw new BlockedAccountException();
                        } else {
                            doTransfer(fromAcct, toAcct, amount);
                            isDone = true;
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return isDone;
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) throws AccountNotExistsException {
        Account account = getAccount(accountNum);
        return account.getMoney();
    }

    public long getAllMoney() {
        long result = 0;
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            result += entry.getValue().getMoney();
        }
        return result;
    }

    public Account getAccount(String accNumber) throws AccountNotExistsException {
        Account result = new Account();
        boolean isFound = false;
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().getAccNumber().equals(accNumber)) {
                result = entry.getValue();
                isFound = true;
            }
        }
        if (isFound){
            return result;
        } else {
            throw new AccountNotExistsException(accNumber);
        }
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

    public synchronized void setBlocked(String accountNum) throws AccountNotExistsException {
        getAccount(accountNum).setBlocked(true);
    }
}
