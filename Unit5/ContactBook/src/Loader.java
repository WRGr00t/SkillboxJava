import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class Loader
{
    public static void main(String[] args) throws IOException {
        TreeMap<String,String> contactList = new TreeMap<>();
        boolean exitOn = false;
        for (;;){
            if (exitOn) break;
            System.out.println("Please type name or number:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command = reader.readLine().trim();
            String numberForCheck = command.replaceAll("[^0-9]", "");
            if (numberForCheck.equals("")){ //если введены только буквы
                switch (command)
                {
                    case "LIST":
                    {
                        if (contactList.size()==0) {System.out.println("Contact list is empty");}
                        else for (String name : contactList.keySet())
                        {
                            System.out.println(name+" - "+contactList.get(name));
                        }
                        break;
                    }
                    case "EXIT":
                    {
                        exitOn = true;
                        break;
                    }
                    default:{
                        if (contactList.containsKey(command))//если введеная строка совпадают с именем в списке
                        {
                            System.out.println(command+" - "+contactList.get(command));
                        } else //новая запись по имени
                            {
                            System.out.println("It's new contact, please type number:");
                            String stringForCheck = reader.readLine().replaceAll("[^0-9]", "");
                            if (!stringForCheck.equals(""))
                                {
                                contactList.put(command,stringForCheck);
                                System.out.println("New record is added");
                                }
                            }
                        break;
                    }
                }

            } else //если введены цифры
                {
                    if (contactList.containsValue(command)) //вывод совпадения
                    {
                        for (String name : contactList.keySet())
                        {
                            if (command.equals(contactList.get(name))) System.out.println(name+" - "+contactList.get(name));
                        }
                    } else //введен новый номер
                        {
                            System.out.println("It's a new number, please type name:");
                            String stringForCheck = reader.readLine().replaceAll("[^A-Z,a-z,А-Я,а-я]", "");
                            if (!stringForCheck.equals(""))
                            {
                                contactList.put(stringForCheck,command);
                                System.out.println("New record is added");
                            }
                        }
                }
        }
    }
}
