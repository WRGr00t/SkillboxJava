import java.util.ArrayList;

public class Bank implements Runnable {
    ArrayList<Account> accounts;

    public Bank(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void run() {
        int numTrans = (int) (50f + Math.random() * 150f);
        for (int i = 1; i < numTrans; i++) {
            int accNo1 = (int) Math.round(Math.random() * accounts.size());
            int accNo2 = (int) Math.round(Math.random() * accounts.size());
            Account account1 = accounts.get(accNo1);
            Account account2 = accounts.get(accNo2);

        }
    }
}
