import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://skillbox.ru/";

        Path pathForWriteFile = Paths.get("src/map.txt");
        /*ForkJoinPool pool = new ForkJoinPool();
        Set<Link> links = pool.invoke(new SiteBuilder(url, 0));
        SiteBuilder.saveToFile(pathForWriteFile, links);
        pool.shutdown();*/

    }
}
