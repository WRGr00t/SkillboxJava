import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerStorage
{
    private HashMap<String, Customer> storage;

    public CustomerStorage()
    {
        storage = new HashMap<>();
    }

    public void addCustomer(String data)
    {

        String[] components = data.split("\\s+");
        try {
            String name = components[0] + " " + components[1];
            if (isValidEmail(components[2])){
                if (isValidNumber(components[3])){
                    storage.put(name, new Customer(name, components[3], components[2]));
                } else {
                    throw new NumberException("Input wrong number", components[3]);
                }
            } else {
                throw new MailException("Input wrong email", components[2]);
            }

        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Incorrect data entered!");
        } catch (MailException e) {
            e.printStackTrace();
        } catch (NumberException e) {
            e.printStackTrace();
        }

    }

    public void listCustomers()
    {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name)
    {
        storage.remove(name);
    }

    public int getCount()
    {
        return storage.size();
    }

    public final static boolean isValidEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public final static boolean isValidNumber(String number) {
        String ePattern = "\\(\\d{3}\\)\\d{3}-?\\d{4}"; // формат телефона (123)456-7890 или (123)4567890
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(number);
        return m.matches();
    }
}