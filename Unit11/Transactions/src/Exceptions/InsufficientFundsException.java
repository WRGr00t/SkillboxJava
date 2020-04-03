package Exceptions;

public class InsufficientFundsException extends Exception {

    private String accountId;

    public InsufficientFundsException(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public void printStackTrace() {
        System.out.printf("Недостаточно средств на счете №%s%n", accountId);
    }

    @Override
    public String getMessage() {
        return "Недостаточно средств на счете №" + accountId;
    }
}
