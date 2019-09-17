public class SoleProprietor extends Client {
    private final double COMMISSION_OUT = 0.0;

    @Override
    public void giveOut(double sum) {
        double commission = COMMISSION_OUT;
        double amount = getBankAccount();
        if (sum > 0) {
            if (sum < getBankAccount()) {
                // метод, разрешающий оператору съем денег
                // метод, проверяющий ответ от оператора
                amount = amount - sum * (1 + commission);
                setBankAccount(amount);
                System.out.printf("Произведено снятие на сумму - %.2f c комиссией - %.2f%n", sum, sum * commission);
            } else {
                System.out.println("Недостаточно средств для операции");
            }
        } else {
            System.out.println("Ошибка");
        }
    }

    @Override
    public void putOn(double sum) {
        double commission;
        if (sum > 0) {
            if (sum < 1000.0) {
                commission = 0.01;
            } else {
                commission = 0.005;
            }
            double amount = getBankAccount();
            //метод, проверяющий ответ оператора
            amount = amount + (sum - sum * commission);
            setBankAccount(amount);
            System.out.printf("Внесена сумма в размере - %.2f c комиссией - %.2f%n", (sum - (sum * commission)), sum * commission);
        }
    }

    @Override
    public void printAmount() {
        System.out.printf("Текущий счет - %.2f%n", getBankAccount());
    }
}
