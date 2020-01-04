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
        int sizeOfPart = files.length;
        if (cpuCount > 1){
            sizeOfPart = files.length / cpuCount;
        } else {
            System.out.println("Нет доступных процессоров(");
        }

        System.out.println("Копируем " + files.length + " файлов кусками по " + sizeOfPart);
        System.out.println("Доступно " + cpuCount + " процессоров");

        if (files.length > cpuCount) {
            for (int i = 0; i < cpuCount; i++) {
                int positionOnArray = (i * sizeOfPart);
                sizeOfPart = files.length / cpuCount;
                File[] newFiles = new File[sizeOfPart];
                System.out.println("Поток №" + i);
                runThread(positionOnArray, files, newFiles, dstFolder, start, i);
            }
        } else {
            File[] newFiles = new File[files.length];
            runThread(0, files, newFiles, dstFolder, start, 0);
        }
        int remains = files.length % cpuCount;
        if ( remains > 0){
            File[] newFiles = new File[remains];
            System.out.println("Дополнительный поток");
            runThread(files.length - remains, files, newFiles, dstFolder, start, 1000);
        }
    }

    private static void runThread(int positionOnArray, File[] files, File[] newFiles, String dstFolder, long start, int i) {
        System.arraycopy(files, positionOnArray, newFiles, 0, newFiles.length);
        ImageResizer resizer = new ImageResizer(newFiles, newWidth, dstFolder, start, i);
        new Thread(resizer).start();
    }
}
