import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Locale;

public class Loader {
    private static final Locale LOCALE = new Locale("ru", "RU");
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy - EEE", LOCALE);
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalDate birthDay = LocalDate.of(1979, Month.NOVEMBER, 25);
        int age = today.getYear() - birthDay.getYear();
        if (today.isBefore(birthDay.plusYears(age)))
        {
            age--;
        }
        for (int i = 0; i <= age; i++)
        {
            System.out.println(i + " - " + birthDay.plusYears(i).format(formatter));
        }
    }
}