public class Main
{
    public static void main(String[] args)
    {
        Container container = new Container();
        container.count += 7843;
        System.out.println(sumDigits (546077));

    }

    public static int sumDigits(int number)
    {
        String string = Integer.toString(number);
        int result = 0;
        char character ='0';
        for (int i = 0; i<string.length(); i++){
            character = string.charAt(i);
            result = result + Character.digit(character, 10);
        }
        return result;
    }
}
