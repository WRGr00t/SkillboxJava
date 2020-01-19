import java.util.HashMap;
import java.util.Random;

public class Bank {
    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    public Bank(HashMap<String, Account> accounts){
        this.accounts = accounts;
        System.out.println("Создан банк на " + accounts.size() + " счетов.");
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            System.out.println("ID =  " + entry.getKey() + " значение - #" + entry.getValue().getAccNumber() + " c " + entry.getValue().getMoney());
        }
    }

    public Bank(int accountAmount) {
        initBank(accountAmount);
        System.out.println("Создан банк на " + accounts.size() + " счетов.");
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            System.out.println("ID =  " + entry.getKey() + " значение - #" + entry.getValue().getAccNumber() + " c " + entry.getValue().getMoney());
        }
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
    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Account fromAccount = getAccount(fromAccountNum);
        Account toAccount = getAccount(toAccountNum);
        if (!(fromAccount.isBlocked() || toAccount.isBlocked())) {
            if (fromAccount.getMoney() >= amount) {
                fromAccount.deductMoney(amount);
                toAccount.addMoney(amount);
            } else {
                System.out.println("Недостаточно средств");
            }
            if (amount > 50000) {
                if (isFraud(fromAccountNum, toAccountNum, amount)) {
                    setBlocked(fromAccountNum);
                    setBlocked(toAccountNum);
                }
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        Account account = getAccount(accountNum);
        return account.getMoney();
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

    public void setBlocked(String accountNum) {
        getAccount(accountNum).setBlocked(true);
    }

    public void initBank(int bankSize) {
        for (int i = 0; i < bankSize; i++) {
            Account account = new Account((long) (100000 * Math.random()), Integer.toString(i));
            accounts.put(account.getAccNumber(), account);
        }
    }
    public long getAllMoney(){
        long result = 0;
        for (HashMap.Entry<String, Account> entry : accounts.entrySet()) {
            result =+ entry.getValue().getMoney();
        }
        return result;
    }
}
