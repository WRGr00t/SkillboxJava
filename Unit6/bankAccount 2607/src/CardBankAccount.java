public class CardBankAccount extends BankAccount {
    private static final double COMMISSION = 0.01;

    public CardBankAccount(double sum) {
        super(sum);
    }

    public CardBankAccount() {
        this(0.0);
    }

    @Override
    public void giveOut(double sum) {
        super.giveOut(sum * (1 + COMMISSION));
        // метод, проверяющий ответ от оператора
        // если метод подтвердил снятие...{
        System.out.println("В том числе комиссия - " + sum * COMMISSION);
        //}
    }
}
