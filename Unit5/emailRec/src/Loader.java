import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashSet<String> note = new HashSet<>();
        for (; ; ) {
            System.out.println("Введите e-mail");
            String command = scanner.nextLine().trim().toLowerCase();
            if (command.equals("list")) {
                for (String string : note) {
                    System.out.println(string);
                }
            } else {
                String email = command.trim().toLowerCase();
                if (isValidEmail(email)) { //если введен mail
                    boolean addRec = note.add(email);
                    if (addRec) { //если запись добавилась
                        System.out.println("Новая запись добавлена");
                    } else { //если есть запись - выводим на экран
                        System.out.println(email + " уже внесена в базу");
                    }
                } else { //если ввели нечто не соответствующее адресу почты
                    System.out.println("Некорректная запись e-mail");
                }
            }
        }
    }

    public final static boolean isValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
