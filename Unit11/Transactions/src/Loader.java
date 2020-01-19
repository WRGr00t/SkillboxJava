public class Loader {
    public static void main(String[] args) throws InterruptedException {
        int bankSize = 10000;
        Bank bank = new Bank(bankSize);
        System.out.println(bank.getAccount("1").getMoney());
        System.out.println(bank.getBalance("10"));
        bank.transfer("1", "10", 100);
        System.out.println(bank.getBalance("1"));
        System.out.println(bank.getBalance("10"));
    }
}
