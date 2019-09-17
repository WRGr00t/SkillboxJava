public class CardBankAccount extends BankAccount
{
    CardBankAccount(double sum)
    {
        super(sum);
    }

    CardBankAccount()
    {
        this(0.0);
    }

    @Override
    public void giveOut(double sum)
    {
        super.giveOut(sum);
        // метод, проверяющий ответ от оператора
        // если метод подтвердил снятие...{
        amount = amount - sum*0.01;
        System.out.println("Комиссия составила - " + sum*0.01);
        //}
    }
}
