import java.io.File;
import java.util.*;

public class Main {
    public static int newWidth = 300;

    public static void main(String[] args) {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        String srcFolder = "d:/src";
        String dstFolder = "d:/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();
        List<File> filesList = Arrays.asList(files);
        int partitionSize = filesList.size() / cpuCount;
        List<List<File>> partitions = new LinkedList<>();
        for (int i = 0; i < filesList.size(); i += partitionSize) {
            partitions.add(filesList.subList(i,
                    Math.min(i + partitionSize, filesList.size())));
        }
        for (int i = 0; i < partitions.size(); i++) {
            System.out.println(partitions.get(i).size());
            File[] newFiles = partitions.get(i).toArray(new File[partitions.get(i).size()]);
            ImageResizer resizer = new ImageResizer(newFiles, newWidth, dstFolder, start);
            new Thread(resizer).start();
        }
    }
}
