public class BankAccount
{
    public static double amount;

    BankAccount()
    {
        this(0.0);
    }

    BankAccount(double sum)
    {
        if (sum >= 0.0){
            this.amount = sum;
        }
    }

    public static double getAmount()
    {
        return amount;
    }
    public static void setAmount(double amount)
    {
        BankAccount.amount = amount;
    }
    public void giveOut(double sum)
    {
        double currentSum = getAmount();
        if (sum > 0){
            if (sum < currentSum){
                // метод, разрешающий оператору съем денег
                // метод, проверяющий ответ от оператора
                setAmount(currentSum - sum);
                System.out.println("Произведено снятие на сумму - " + sum);
            } else {
                System.out.println("Недостаточно средств для операции");
            }
        } else {
            System.out.println("Ошибка");
        }
    }
    public void putOn(double sum)
    {
        double currentSum = getAmount();
        if (sum > 0){
            //метод, проверяющий ответ оператора
            setAmount(currentSum + sum);
            System.out.println("Внесена сумма в размере - " + sum);
        }
    }

}
