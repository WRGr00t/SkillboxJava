import java.io.File;

public class Main {
    private static int newWidth = 300;

    public static void main(String[] args) {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        String srcFolder = "d:/src";
        String dstFolder = "d:/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();
        System.out.println(files.length);
        int sizeOfPart = files.length / cpuCount;
        System.out.println(sizeOfPart);
        System.out.println(cpuCount);
        for (int i = 0; i <= cpuCount; i++) {
            int positionOnArray = (i * sizeOfPart);
            if (i == cpuCount && !(files.length % cpuCount == 0)) {
                sizeOfPart = files.length % cpuCount;
            }
            File[] newFiles = new File[sizeOfPart];
            runThread(positionOnArray, files, newFiles, dstFolder, start);
            System.out.println(positionOnArray);
        }
    }

    private static void runThread(int positionOnArray, File[] files, File[] newFiles, String dstFolder, long start) {
        System.arraycopy(files, positionOnArray, newFiles, 0, newFiles.length);
        ImageResizer resizer = new ImageResizer(newFiles, newWidth, dstFolder, start);
        new Thread(resizer).start();
    }
}
