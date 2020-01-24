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
                System.out.printf("Поток - %s Источник - %s с суммой = %d%n",
                        Thread.currentThread().getName(),
                        account1.getAccNumber(),
                        oldSummFrom);
                Account account2 = bank.getAccount(Integer.toString(bankSize - i - 1));
                oldSummTo = account2.getMoney().longValue();
                System.out.printf("Поток - %s Получатель - %s с суммой = %d%n",
                        Thread.currentThread().getName(),
                        account2.getAccNumber(),
                        oldSummTo);
                long amountMoney = i * 1000;
                try {
                    successfully = bank.transfer(account1.getAccNumber(), account2.getAccNumber(), amountMoney);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                newSummFrom = account1.getMoney().longValue();
                newSummTo = account2.getMoney().longValue();
                if (successfully) {
                    System.out.printf("Поток - %s Источник - %s, было снято %s остаток = %d%n",
                            Thread.currentThread().getName(),
                            account1.getAccNumber(),
                            amountMoney,
                            account1.getMoney().longValue());
                    System.out.printf("Поток - %s Получатель - %s, с новой суммой = %d%n",
                            Thread.currentThread().getName(),
                            account2.getAccNumber(),
                            account2.getMoney().longValue());
                    System.out.printf("Поток - %s Контрольные цифры - %d - %d = %d%n",
                            Thread.currentThread().getName(),
                            oldSummFrom,
                            amountMoney,
                            newSummFrom);
                    System.out.printf("Поток - %s Контрольные цифры - %d + %d = %d%n",
                            Thread.currentThread().getName(),
                            oldSummTo,
                            amountMoney,
                            newSummTo);
                } else {
                    System.out.printf("Поток - %s Перевод не проведен, состояние счетов:%n",
                            Thread.currentThread().getName());
                    System.out.printf("Поток - %s Источник - %s, остаток  = %s%n",
                            Thread.currentThread().getName(),
                            account1.getAccNumber(),
                            account1.getMoney());
                    System.out.printf("Поток - %s Получатель - %s, остаток = %s%n",
                            Thread.currentThread().getName(),
                            account2.getAccNumber(),
                            account2.getMoney());
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
        System.out.printf("Поток - %s Контрольная сумма денег в банке после манипуляций = %d%n",
                Thread.currentThread().getName(),
                newSumBank);
        System.out.printf("Поток - %s Разница = %d%n",
                Thread.currentThread().getName(),
                oldSumBank - newSumBank);
        System.out.printf("Поток - %s Счетов заблокировано - %d%n",
                Thread.currentThread().getName(),
                bank.getAllBlockAcc().size());
    }
}
