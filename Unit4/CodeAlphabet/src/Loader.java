public class Loader {

    public static void main(String[] args) {
        String alphabet = "АБВГДЕЖЗИКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯабвгдежзиклмнопрстуфхцчшщьыъэюя";
        for (int i = 0; i < alphabet.length(); i++) {
            char symbol = alphabet.charAt(i);
            System.out.printf("'%c': %4d %n", symbol, (int)symbol);
        }
        String latinAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 65; i < 123; i++) {
            if (latinAlphabet.indexOf((char) i) >= 0) {
                System.out.println((char) i + " имеет код " + i);
            }
        }
    }
}
