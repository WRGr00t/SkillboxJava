import core.Account;
import core.Bank;

import java.util.ArrayList;
import java.util.List;

public class Loader {
    public static void main(String[] args) throws InterruptedException {
        final int BANK_SIZE = 3;
        Bank bank = new Bank(BANK_SIZE);
        final int THREADS_COUNT = 100_000;
        long oldSumBank = bank.getAllMoney();

        List<Thread> threads = new ArrayList<>();

        //============ тестовые манипуляции со счетами ===========

        Runnable task = () -> {

            for (int i = 40; i < 60; i++) {
                Account account1 = bank.getAccount(String.valueOf((int)Math.round(Math.random() * BANK_SIZE)));
                /*oldSumFrom = account1.getMoney().longValue();
                System.out.printf("Поток - %s Источник - %s с суммой = %d%n",
                        Thread.currentThread().getName(),
                        account1.getAccNumber(),
                        oldSumFrom);*/
                Account account2 = bank.getAccount(String.valueOf((int)Math.round(Math.random() * BANK_SIZE)));
                /*oldSumTo = account2.getMoney().longValue();
                System.out.printf("Поток - %s Получатель - %s с суммой = %d%n",
                        Thread.currentThread().getName(),
                        account2.getAccNumber(),
                        oldSumTo);*/
                long amountMoney = i * 1000;
                try {
                    if ((account1.getAccNumber() != null) && (account2.getAccNumber() != null)){
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
                /*newSumFrom = account1.getMoney().longValue();
                newSumTo = account2.getMoney().longValue();
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
                            oldSumFrom,
                            amountMoney,
                            newSumFrom);
                    System.out.printf("Поток - %s Контрольные цифры - %d + %d = %d%n",
                            Thread.currentThread().getName(),
                            oldSumTo,
                            amountMoney,
                            newSumTo);
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
                }*/
            }
        };
        for (int i = 0; i < THREADS_COUNT; i++) {
            Thread thread = new Thread(task);
            thread.start();
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.join();
        }
        /*long newSumBank = bank.getAllMoney();
        System.out.printf("Поток - %s Контрольная сумма денег в банке после манипуляций = %d%n",
                Thread.currentThread().getName(),
                newSumBank);
        System.out.printf("Поток - %s Разница = %d%n",
                Thread.currentThread().getName(),
                oldSumBank - newSumBank);
        System.out.printf("Поток - %s Счетов заблокировано - %d%n",
                Thread.currentThread().getName(),
                bank.getAllBlockAcc().size());*/
    }
}
