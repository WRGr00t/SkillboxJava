import Exceptions.AccountNotExistsException;

public class Task implements Runnable {
    Bank bank;

    public Task(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    @Override
    public void run() {
        int numTransaction = (int) (50f + Math.random() * 450f);

        for (int i = 0; i < numTransaction; i++) {
            Account account1 = null;
            Account account2 = null;
            String accNo1 = String.valueOf((int) Math.round(Math.random() * bank.getAccounts().size()));
            String accNo2 = String.valueOf((int) Math.round(Math.random() * bank.getAccounts().size()));
            try {
                account1 = bank.getAccount(accNo1);
                account2 = bank.getAccount(accNo2);
            } catch (AccountNotExistsException e) {
                e.printStackTrace();
            }
            if (account1 == null) {
                try {
                    throw new AccountNotExistsException(accNo1);
                } catch (AccountNotExistsException e) {
                    e.printStackTrace();
                }
            }
            if (account2 == null) {
                try {
                    throw new AccountNotExistsException(accNo2);
                } catch (AccountNotExistsException e) {
                    e.printStackTrace();
                }
            }

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
}
