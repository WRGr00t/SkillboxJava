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

    }
}

