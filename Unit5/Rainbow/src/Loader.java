public class Loader {
    public static void main(String[] args) {
        String array[]={"Red", "Orange", "Yellow", "Green", "Cyan", "Blue", "Violet"};
        for (int i = 0; i < array.length; i++)
        {
            System.out.println(array[array.length - 1 - i]);
        }
        for (int i = array.length - 1; i >= 0; i--)
        {
            System.out.println(array[i]);
        }
    }
}
