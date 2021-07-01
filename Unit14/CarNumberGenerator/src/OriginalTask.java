import java.io.FileOutputStream;
import java.util.ArrayList;

public class OriginalTask {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        FileOutputStream writer = new FileOutputStream("res/numbers.txt");
        ArrayList<Integer> timeToRec = new ArrayList<>();
        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        for (int number = 1; number < 1000; number++) {
            int regionCode = 199;
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {
                        String carNumber = firstLetter + padNumber(number, 3) +
                                secondLetter + thirdLetter + padNumber(regionCode, 2);
                        long startRec = System.currentTimeMillis();
                        writer.write(carNumber.getBytes());
                        writer.write('\n');
                        long stopRec = System.currentTimeMillis();
                        timeToRec.add((int) (stopRec - startRec));
                    }
                }
            }
        }
        long startRec = System.currentTimeMillis();
        writer.flush();
        writer.close();
        long stopRec = System.currentTimeMillis();
        timeToRec.add((int) (stopRec - startRec));
        System.out.println((System.currentTimeMillis() - start) + " ms");
        System.out.printf("Из них время на запись - %d ms\n", timeToRec.stream().mapToLong(s -> s).sum());

    }

    private static String padNumber(int number, int numberLength) {
        String numberStr = Integer.toString(number);
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr = '0' + numberStr;
        }
        return numberStr;
    }

}
