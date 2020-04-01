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
            Account account1 = bank.getAccount(String.valueOf((int) Math.round(Math.random() * bank.getAccounts().size())));
            Account account2 = bank.getAccount(String.valueOf((int) Math.round(Math.random() * bank.getAccounts().size())));
            long amountMoney = Math.round(Math.random() * 1_000);
            try {
                if ((account1.getAccNumber() != null) && (account2.getAccNumber() != null)) {
                    bank.transfer(account1.getAccNumber(), account2.getAccNumber(), amountMoney);
                    System.out.printf("Поток - %s Перевод с %s на %s в размере %s from tasks %n",
                            Thread.currentThread().getName(),
                            account1.getAccNumber(),
                            account2.getAccNumber(),
                            amountMoney);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("i = " + i + "/"+ numTransaction + " Thread - " + Thread.currentThread().getName());
        }
    }
}
