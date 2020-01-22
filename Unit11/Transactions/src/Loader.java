import java.util.ArrayList;
import java.util.List;

public class Loader {
    public static void main(String[] args) throws InterruptedException {
        int bankSize = 100000;
        Bank bank = new Bank(bankSize);
        int threadCount = 3;
        long oldSumBank = bank.getAllMoney();
        System.out.println("Контрольная сумма денег в банке = " + oldSumBank);

        System.out.println(bank.getAccount("1").getMoney());
        System.out.println(bank.getBalance("10"));
        boolean isDone = bank.transfer("1", "10", 100);
        if (isDone) {
            System.out.println("Перевод проведен успешно");
        }
        System.out.println(bank.getBalance("1"));
        System.out.println(bank.getBalance("10"));
        List<Thread> threads = new ArrayList<>();

        //============ тестовые манипуляции со счетами ===========

        Runnable task = () -> {
            long oldSummFrom = 0;
            long oldSummTo = 0;
            long newSummFrom = 0;
            long newSummTo = 0;
            boolean successfully = false;
            for (int i = 40; i < 60; i++) {
                Account account1 = bank.getAccount(Integer.toString(i));
                oldSummFrom = account1.getMoney().longValue();
                System.out.println("Источник - " + account1.getAccNumber() + " с суммой = " + oldSummFrom + " " + Thread.currentThread().getName());
                Account account2 = bank.getAccount(Integer.toString(bankSize - i - 1));
                oldSummTo = account2.getMoney().longValue();
                System.out.println("Получатель - " + account2.getAccNumber() + " с суммой = " + oldSummTo + " " + Thread.currentThread().getName());
                long amountMoney = i * 1000;
                try {
                    successfully = bank.transfer(account1.getAccNumber(), account2.getAccNumber(), amountMoney);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newSummFrom = account1.getMoney().longValue();
                newSummTo = account2.getMoney().longValue();
                if (successfully) {
                    System.out.println("Источник - " + account1.getAccNumber() + ", было снято " + amountMoney + " остаток  = " + account1.getMoney() + " " + Thread.currentThread().getName());
                    System.out.println("Получатель - " + account2.getAccNumber() + " с новой суммой = " + account2.getMoney() + " " + Thread.currentThread().getName());
                    System.out.println("Контрольные цифры - " + oldSummFrom + " - " + amountMoney + " = " + newSummFrom + " " + Thread.currentThread().getName());
                    System.out.println("Контрольные цифры - " + oldSummTo + " + " + amountMoney + " = " + newSummTo + " " + Thread.currentThread().getName());
                } else {
                    System.out.println("Перевод не проведен, состояние счетов:" + " " + Thread.currentThread().getName());
                    System.out.println("Источник - " + account1.getAccNumber() + ", остаток  = " + account1.getMoney() + " " + Thread.currentThread().getName());
                    System.out.println("Получатель - " + account2.getAccNumber() + ", остаток = " + account2.getMoney() + " " + Thread.currentThread().getName());
                }
            }
        };
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long newSumBank = bank.getAllMoney();
        System.out.println("Контрольная сумма денег в банке после манипуляций = " + newSumBank + " " + Thread.currentThread().getName());
        System.out.println("Разница = " + (oldSumBank - newSumBank) + " " + Thread.currentThread().getName());
        System.out.println("Счетов заблокировано - " + bank.getAllBlockAcc().size() + " " + Thread.currentThread().getName());
    }
}
