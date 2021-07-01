import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task implements Runnable {
    byte[][] letters;
    int startRange;
    int partitionSize;
    int regionCode;

    public Task(byte[][] letters, int startRange, int partitionSize, int regionCode) {
        this.letters = letters;
        this.startRange = startRange;
        this.partitionSize = partitionSize;
        this.regionCode = regionCode;
    }

    @Override
    public void run() {
        long startRec = System.currentTimeMillis();
        ByteBuffer numbers = generatedNumbersToStrings();
        long stopRec = System.currentTimeMillis();
        System.out.printf("Время генерации - %d ms\n", stopRec - startRec);
        String path = "res/numbers-" + Thread.currentThread().getName() + ".txt";
        startRec = System.currentTimeMillis();
        writeToFile(path, numbers);
        stopRec = System.currentTimeMillis();
        System.out.printf("Время записи - %d ms\n", stopRec - startRec);
    }

    public static void reuseStringBuilder(final StringBuilder sb) {
        sb.delete(0, sb.length());
    }

    public static String bytesToStringUTFNIO(byte[] bytes) {
        CharBuffer cBuffer = ByteBuffer.wrap(bytes).asCharBuffer();
        return cBuffer.toString();
    }

    public ByteBuffer generatedNumbersToStrings() {
        int capacity = 3_428_352;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        StringBuilder sb = new StringBuilder();
        ArrayList<String> strings = new ArrayList<>();

        for (byte[] let1 : letters) {
            for (byte[] let2 : letters) {
                for (byte[] let3 : letters) {
                    for (int num = startRange; num < startRange + partitionSize; num++) {
                        byteBuffer.put(let1);
                        if (num < 10) {
                            byteBuffer.put("0".getBytes())
                                    .put("0".getBytes())
                                    .put(String.valueOf(num).getBytes());
                        } else if (num < 100) {
                            byteBuffer.put("0".getBytes())
                                    .put(String.valueOf(num).getBytes());
                        } else {
                            byteBuffer.put(String.valueOf(num).getBytes());
                        }
                        byteBuffer.put(let2);
                        byteBuffer.put(let3);
                        if (regionCode < 10) {
                            byteBuffer.put("0".getBytes())
                            .put(String.valueOf(regionCode).getBytes());
                        } else {
                            byteBuffer.put(String.valueOf(regionCode).getBytes());
                        }
                        byteBuffer.put("\n".getBytes());
                    }
                }
            }
        }
        return byteBuffer;
    }

    private static byte[][] convertToBytes(ArrayList<String> strings) {
        byte[][] data = new byte[strings.size()][];
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            data[i] = string.getBytes(StandardCharsets.UTF_8);
        }
        return data;
    }



    public static void writeToFile(String path, ByteBuffer numbers) {

        try {
            FileChannel channel = new FileOutputStream(new File(path), true).getChannel();
            numbers.flip();
            channel.write(numbers);
            channel.force(true);
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static List<byte[]> getList(byte[] str_b, int len) {
        List<byte[]> list = new ArrayList<>();
        int count = (int)Math.ceil((double)str_b.length / len);
        int start_position = 0;
        int end_position = len;
        for(int i = 1; i <= count; i++) {
            if (i == count) {
                list.add(Arrays.copyOfRange(str_b, start_position, str_b.length));
            } else {
                list.add(Arrays.copyOfRange(str_b, start_position, end_position));
            }
            start_position += len;
            end_position = start_position + len;

        }
        return list;
    }
}
