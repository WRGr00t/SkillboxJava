import core.Account;
import core.Bank;
import core.MyThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Loader {
    public static void main(String[] args) throws InterruptedException {
        final int BANK_SIZE = 10;
        Bank bank = new Bank(BANK_SIZE);
        final int THREADS_COUNT = 100;
        long oldSumBank = bank.getAllMoney();

        List<Thread> threads = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            int numTransaction = (int) (50f + Math.random() * 450f);

            for (int i = 0; i < numTransaction; i++) {
                Account account1 = bank.getAccount(String.valueOf((int) Math.round(Math.random() * BANK_SIZE)));
                Account account2 = bank.getAccount(String.valueOf((int) Math.round(Math.random() * BANK_SIZE)));
                long amountMoney = Math.round(Math.random() * 1_000);
                try {
                    if ((account1.getAccNumber() != null) && (account2.getAccNumber() != null)) {
                        bank.transfer(account1.getAccNumber(), account2.getAccNumber(), amountMoney);
                        System.out.printf("Поток - %s Перевод с %s на %s в размере %s%n",
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
        };
        for (int i = 0; i < THREADS_COUNT; i++) {
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
            MyThread myThread = new MyThread(bank);
            executor.execute(myThread);
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
