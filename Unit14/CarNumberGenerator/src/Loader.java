import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Loader {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int THREADS_COUNT = Runtime.getRuntime().availableProcessors();
        char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
        byte[][] byteLetter = convertToBytes(letters);
        int numberCount = 1000;
        int partitionSize = numberCount / THREADS_COUNT;
        int regionCode = 5;

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT);
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < THREADS_COUNT; i++) {
            Task task;
            if (i < THREADS_COUNT - 1) {
                task = new Task(byteLetter, i * partitionSize + 1, partitionSize, regionCode);
            } else {
                task = new Task(byteLetter, i * partitionSize + 1, numberCount - i * partitionSize - 1, regionCode);
            }

            tasks.add(task);
        }
        for (Task task : tasks) {
            executor.submit(task);
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((System.currentTimeMillis() - start) + " ms");

    }

    private static byte[][] convertToBytes(char[] chars) {
        byte[][] data = new byte[chars.length][];
        for (int i = 0; i < chars.length; i++) {
            String string = String.valueOf(chars[i]);
            data[i] = string.getBytes(StandardCharsets.UTF_8);
        }
        return data;
    }

}
