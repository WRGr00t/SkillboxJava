public class Loader
{
    public static void main(String[] args) {
        System.out.println("Целые:");
        System.out.println("Byte " + Byte.MIN_VALUE + " ~ +" + Byte.MAX_VALUE);
        System.out.println("Integer " + Integer.MIN_VALUE + " ~ +" + Integer.MAX_VALUE);
        System.out.println("Short " + Short.MIN_VALUE + " ~ +" + Short.MAX_VALUE);
        System.out.println("Long " + Long.MIN_VALUE + " ~ +" + Long.MAX_VALUE);

        System.out.println("С точкой:");
        System.out.println("Float " + -Float.MAX_VALUE + " ~ +" + Float.MAX_VALUE);
        System.out.println("Double " + -Double.MAX_VALUE + " ~ +" + Double.MAX_VALUE);
    }
}
