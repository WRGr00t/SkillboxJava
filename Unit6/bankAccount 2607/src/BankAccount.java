public class BankAccount {
    private double amount;
    private int hashID;

    private static final int MIN_ID = 1000;
    private static final int TO_MAX_ID = 1000;

    public BankAccount() {
        this(0.0);
    }

    public BankAccount(double sum) {
        if (sum >= 0.0) {
            this.amount = sum;
            hashID = MIN_ID + (int) Math.round(Math.random() * TO_MAX_ID);
        }
    }

    public double getAmount() {
        return amount;
    }

    /*public static void setAmount(double amount) {
        BankAccount.amount = amount;
    }*/

    public void giveOut(double sum) {
        double currentSum = getAmount();
        if (sum > 0) {
            if (sum < currentSum) {
                // метод, разрешающий оператору съем денег
                // метод, проверяющий ответ от оператора
                amount = currentSum - sum;
                System.out.println("Произведено снятие на сумму - " + sum);
            } else {
                System.out.println("Недостаточно средств для операции");
            }
        } else {
            System.out.println("Ошибка");
        }
    }

    public void putOn(double sum) {
        double currentSum = getAmount();
        if (sum > 0) {
            //метод, проверяющий ответ оператора
            amount = currentSum + sum;
            System.out.println("Внесена сумма в размере - " + sum);
        }
    }

    public int getHashID() {
        return hashID;
    }
}
