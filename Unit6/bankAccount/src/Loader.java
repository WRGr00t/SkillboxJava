import java.time.LocalDate;

public class Loader
{
    public static void main(String[] args)
    {
        System.out.println("============ операции по депозиту ============");
        DepositBankAccount myAccount = new DepositBankAccount(100.0, LocalDate.of(2010, 11, 01));
        System.out.println(myAccount.getAmount());
        myAccount.setAmount(200.0);
        System.out.println(myAccount.getAmount());
        myAccount.giveOut(1050.0);
        System.out.println(myAccount.getAmount());
        myAccount.putOn(1000.0);
        System.out.println(myAccount.getAmount());
        myAccount.giveOut(1050.0);
        System.out.println(myAccount.getAmount());

        System.out.println("============ операции по карте ============");
        CardBankAccount myCard = new CardBankAccount(100.0);
        System.out.println(myCard.getAmount());
        myCard.setAmount(200.0);
        System.out.println(myCard.getAmount());
        myCard.giveOut(1050.0);
        System.out.println(myCard.getAmount());
        myCard.putOn(1000.0);
        System.out.println(myCard.getAmount());
        myCard.giveOut(1050.0);
        System.out.println(myCard.getAmount());

        System.out.println("============ операции по второй карте ============");
        CardBankAccount wifeCard = new CardBankAccount(1000.0);
        wifeCard.giveOut(500.0);
        System.out.println(wifeCard.getAmount());
        wifeCard.putOn(500.0);
        wifeCard.giveOut(100.0);
        System.out.println(wifeCard.getAmount());
    }
}
/*
Создайте класс, который будет представлять собой расчётный счёт в банке.
 На этот расчётный счёт деньги можно положить, с него их можно снять, и ещё можно посмотреть,
 сколько денег на счёте. Создайте два класса-наследника - депозитарный расчётный счёт,
 с которого нельзя снимать деньги в течение месяца после последнего внесения,
 и карточный счёт, при снятии денег с которого будет взиматься комиссия 1%. */