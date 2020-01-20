import java.util.concurrent.atomic.AtomicInteger;

public class Loader {
    public static void main(String[] args) throws InterruptedException {
        int bankSize = 10000;
        Bank bank = new Bank(bankSize);

        System.out.println("Контрольная сумма денег в банке = " + bank.getAllMoney());

        System.out.println(bank.getAccount("1").getMoney());
        System.out.println(bank.getBalance("10"));
        bank.transfer("1", "10", 100);
        System.out.println(bank.getBalance("1"));
        System.out.println(bank.getBalance("10"));

        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                Account account1 = bank.getAccount(Integer.toString(i));
                System.out.println("Источник - " + account1.getAccNumber() + " с суммой = " + account1.getMoney());
                Account account2 = bank.getAccount(Integer.toString(bankSize - i - 1));
                System.out.println("Получатель - " + account2.getAccNumber() + " с суммой = " + account2.getMoney());
                try {
                    bank.transfer(account1.getAccNumber(), account2.getAccNumber(), i*100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Источник - " + account1.getAccNumber() + ", было снято " + i * 100 + " остаток  = " + account1.getMoney());
                System.out.println("Получатель - " + account2.getAccNumber() + " с новой суммой = " + account2.getMoney());
            }
        };
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(task);
            thread.start();
        }
        System.out.println("Контрольная сумма денег в банке после манипуляций = " + bank.getAllMoney());
    }
}
