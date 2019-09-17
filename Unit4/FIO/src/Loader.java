import java.util.Scanner;

public class Loader {
    public static void main(String[] args) {
        //без использования регулярных выражений
        System.out.println("Введите фимилию, имя и отчество через пробел.");
        Scanner fullname = new Scanner(System.in);
        String fio = fullname.nextLine();
        /**String surname = fio.substring(0,fio.indexOf(' '));
        String name = fio.substring(fio.indexOf(' '),fio.lastIndexOf(' '));
        String patronymic  = fio.substring(fio.lastIndexOf(' '),fio.length());
        System.out.println("Фамилия: "+surname);
        System.out.println("Имя: "+name);
        System.out.println("Отчество: "+patronymic);*/

        //с регулярными выражениями
            String result;
            result=fio.replaceAll(" ","\n");
            
            System.out.println(fio);
    }
}
