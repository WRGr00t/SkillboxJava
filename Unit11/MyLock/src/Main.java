import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static int bankSize = 10;
    private static ArrayList<Account> accounts = new ArrayList<>();

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

        int to = (int) Math.round(Math.random() * accounts.size());
        int from = (int) Math.round(Math.random() * accounts.size());
        Account toAcc = accounts.get(to);
        Account fromAcc = accounts.get(from);

        long amountMoney = Math.round(Math.random() * 1_000);
        boolean isValid = (toAcc != null) && (fromAcc != null) && (toAcc.equals(fromAcc) && (fromAcc.getMoney().longValue() >= amountMoney));

        try {
            if (isValid) {
                System.out.printf("Поток - %s Перевод с %s на %s в размере %s%n",
                        Thread.currentThread().getName(),
                        fromAcc.getAccNumber(),
                        toAcc.getAccNumber(),
                        amountMoney);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("i = " + i + "/" + numTransaction + " Thread - " + Thread.currentThread().getName());

    }
}

