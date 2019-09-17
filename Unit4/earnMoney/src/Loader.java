public class Loader
{
    public static void main(String[] args)
    {
        String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
        String vasyaString = "Вася заработал";
        String mashaString = "Маша - ";
        String vasyaSumm = text.substring(text.indexOf(vasyaString)+vasyaString.length(),text.indexOf("рублей")).trim();
        System.out.println("Вася заработал "+vasyaSumm+ " рублей");
        String mashaSumm = text.substring(text.indexOf(mashaString)+mashaString.length(),text.lastIndexOf("рублей")).trim();
        System.out.println("Маша заработала "+mashaSumm+ " рублей");
        System.out.println("Вася и Маша вместе заработали: "+(Integer.parseInt(vasyaSumm)+Integer.parseInt(mashaSumm))+ " рублей");
    }
}