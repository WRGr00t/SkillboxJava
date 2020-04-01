package Test;

import core.Account;
import core.Bank;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BankTest {
    private Account account1, account2, account3;
    HashMap<String, Account> accountHashMap = new HashMap<>();
    Bank bank;

    public BankTest() {
    }

    @Before
    public void setUp() throws Exception {
        AtomicLong sum1 = new AtomicLong(0);
        account1 = new Account(sum1, "1");
        accountHashMap.put("1", account1);

        AtomicLong sum2 = new AtomicLong(100_000);
        account2 = new Account(sum2, "2");
        accountHashMap.put("2", account2);

        AtomicLong sum3 = new AtomicLong(30_000);
        account3 = new Account(sum3, "3");
        accountHashMap.put("3", account3);

        bank = new Bank(accountHashMap);
    }

    @Test
    public void transfer_little_money() throws InterruptedException {
        boolean actual;
        boolean expected;
        actual = bank.transfer("1", "2", 10_000);
        expected = account1.getMoney().longValue() > 10_000;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void transfer_enough_money() throws InterruptedException {
        boolean actual;
        boolean expected;
        actual = bank.transfer("2", "1", 10_000);
        expected = account2.getMoney().longValue() > 10_000;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void doTransfer_little_money() {

        String oldSum1 = account1.getMoney().toString();
        String oldSum2 = account2.getMoney().toString();
        bank.doTransfer(account1, account2, 10_000);

        String actualAmount1 = account1.getMoney().toString();
        String actualAmount2 = account2.getMoney().toString();
        String amount1 = String.valueOf(Integer.parseInt(oldSum1) - Integer.parseInt(actualAmount1));
        String amount2 = String.valueOf(Integer.parseInt(oldSum2) - Integer.parseInt(actualAmount2));
        String [] actual = new String[] {actualAmount1, actualAmount2, amount1, amount2};

        String [] expected = new String[] {"0", "100000", "0", "0"};

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void doTransfer_enough_money() {

        String oldSum1 = account1.getMoney().toString();
        String oldSum2 = account2.getMoney().toString();
        bank.doTransfer(account2, account1, 10_000);

        String actualAmount1 = account1.getMoney().toString();
        String actualAmount2 = account2.getMoney().toString();
        String amount1 = String.valueOf(Integer.parseInt(oldSum1) - Integer.parseInt(actualAmount1));
        String amount2 = String.valueOf(Integer.parseInt(oldSum2) - Integer.parseInt(actualAmount2));
        String [] actual = new String[] {actualAmount1, actualAmount2, amount1, amount2};

        String [] expected = new String[] {"10000", "90000", "-10000", "10000"};

        Assert.assertArrayEquals(expected, actual);
    }
}