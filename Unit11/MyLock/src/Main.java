import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static int bankSize = 10;
    private static HashSet<Account> accounts = new HashSet<>();

    public static void main(String[] args) {
        for (int i = 0; i < bankSize; i++) {
            AtomicLong sum = new AtomicLong(1_000);// * Math.random()));
            Account account = new Account(sum, String.valueOf(i));
            accounts.add(account);
        }

        final int THREADS_COUNT = 100;
        long oldSumBank = accounts.stream()
                .mapToLong(acc -> acc.getMoney().get())
                .sum();

        long amountMoney = Math.round(Math.random() * 1_000);

        try {
            if ((account1 != null) && (account2 != null)) {
                if (bank.transferMoney(account1, account2, amountMoney)) {
                    System.out.printf("Поток - %s Перевод с %s на %s в размере %s%n",
                            Thread.currentThread().getName(),
                            account1.getAccNumber(),
                            account2.getAccNumber(),
                            amountMoney);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("i = " + i + "/" + numTransaction + " Thread - " + Thread.currentThread().getName());

    }
}

