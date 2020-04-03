package Exceptions;

public class AccountNotExistsException extends Exception {

    private String accountId;

    public AccountNotExistsException(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("Счет №%s не найден.\n", accountId);
    }

    @Override
    public String getMessage() {
        return "Счет №" + accountId + " не найден.\n";
    }
}
