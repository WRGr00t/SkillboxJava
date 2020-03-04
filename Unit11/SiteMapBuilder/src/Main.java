import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        String url = "https://skillbox.ru/";
        Path pathForWriteFile = Paths.get("src/map.txt");
        Link rootLink = Link.getRootLink(url);
        ForkJoinPool pool = new ForkJoinPool();
        Set<Link> links = pool.invoke(new SiteBuilder(rootLink));
        SiteBuilder.saveToFile(pathForWriteFile, links);
        pool.shutdown();
    }

}
