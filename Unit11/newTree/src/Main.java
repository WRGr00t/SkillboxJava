import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static final String url = "https://skillbox.ru/";
    public static void main(String[] args) throws IOException {
        Path pathForWriteFile = Paths.get("src/map.txt");
        ForkJoinPool pool = new ForkJoinPool();
        TreeNode root = new TreeNode(url);

        Set<TreeNode> links = pool.invoke(new TreeBuilder(root));
        Set<String> resultSet = prepareStrings(links);
        Files.write(pathForWriteFile, resultSet);
        pool.shutdown();
    }

    public static Set<String> prepareStrings(Set<TreeNode> links) {
        Set<String> resultSet = null;
        for (TreeNode link : links){
            String s = link.getLink();
            s = "\t".repeat(link.getLevel()) + s;
            resultSet.add(s);
        }
        return resultSet;
    }
}


