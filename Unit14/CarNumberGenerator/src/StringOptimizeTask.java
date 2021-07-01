import java.io.FileOutputStream;
import java.util.ArrayList;

public class StringOptimizeTask {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ArrayList<Integer> timeToRec = new ArrayList<>();
        FileOutputStream writer = new FileOutputStream("res/numbers.txt");

        char letters[] = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        for (int number = 1; number < 1000; number++) {
            int regionCode = 199;
            for (char firstLetter : letters) {
                for (char secondLetter : letters) {
                    for (char thirdLetter : letters) {
                        StringBuilder builder = new StringBuilder();
                        builder.append(firstLetter)
                                .append(padNumber(number, 3))
                                .append(secondLetter)
                                .append(thirdLetter)
                                .append(padNumber(regionCode, 2))
                                .append('\n');
                        long startRec = System.currentTimeMillis();
                        writer.write(builder.toString().getBytes());
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
        StringBuilder numberStr = new StringBuilder(Integer.toString(number));
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr.insert(0, '0');
        }
        return numberStr.toString();
    }
}
