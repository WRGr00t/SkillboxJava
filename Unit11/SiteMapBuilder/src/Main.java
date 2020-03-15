import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static final String url = "https://skillbox.ru/";

    public static void main(String[] args) throws IOException {

        Path pathForWriteFile = Paths.get("src/map.txt");
        //Link rootLink = Link.getRootLink(url);
        ForkJoinPool pool = new ForkJoinPool();
        Set<String> links = pool.invoke(new SiteBuilder(url));
        //links = SiteBuilder.prepareStrings();
        Files.write(pathForWriteFile, links);
        //SiteBuilder.saveToFile(pathForWriteFile, SiteTree.getSiteMapTree());
        pool.shutdown();
    }
}
