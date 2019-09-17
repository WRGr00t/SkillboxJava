import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DepositBankAccount extends BankAccount {
    private static LocalDate dayLastPutOn;
    private static final Locale LOCALE = new Locale("ru", "RU");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy - EEE", LOCALE);


    public DepositBankAccount(double sum, LocalDate date) {
        super(sum);
        dayLastPutOn = date;
    }

    public DepositBankAccount() {
        this(0.0, LocalDate.now());
    }

    public DepositBankAccount(LocalDate date) {
        this(0.0, date);
    }


    public static LocalDate getDayLastPutOn() {
        return dayLastPutOn;
    }

    private static void setDayLastPutOn(LocalDate dayLastPutOn) {
        DepositBankAccount.dayLastPutOn = dayLastPutOn;
    }

    @Override
    public void giveOut(double sum) {
        if (LocalDate.now().isAfter(getDayLastPutOn().plusMonths(1))) {
            super.giveOut(sum);
        } else {
            System.out.println("Снятие денег не произведено, операция недоступна до "
                    + dayLastPutOn.plusMonths(1).format(formatter));
        }
    }

    @Override
    public void putOn(double sum) {
        super.putOn(sum);
        setDayLastPutOn(LocalDate.now());
    }

}
