package Exceptions;

public class BlockedAccountException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("Счет заблокирован. Перевод средств невозможен.");
    }

    @Override
    public String getMessage() {
        return "Cчет заблокирован. Перевод средств невозможен.";
    }
}
