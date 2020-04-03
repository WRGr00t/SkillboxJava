import Exceptions.AccountNotExistsException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws AccountNotExistsException {
        final int BANK_SIZE = 10;
        Bank bank = new Bank(BANK_SIZE);
        final int THREADS_COUNT = 100;
        long oldSumBank = bank.getAllMoney();

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT);
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < THREADS_COUNT; i++) {
            Task task = new Task(bank);
            tasks.add(task);
        }
        for (Task task: tasks) {
            executor.submit(task);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        Account account = bank.getAccount("1");
        for (String string : account.getHistory()){
            System.out.println(string);
        }
    }
}
