import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentSkipListSet;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://skillbox.ru/";
        Path pathForWriteFile = Paths.get("src/map.txt");
        ConcurrentSkipListSet<String> uniqueURL = new ConcurrentSkipListSet<>();
        ReaderLinks reader = new ReaderLinks(uniqueURL, url);
        reader.get_links(url);
        ReaderLinks.saveToFile(pathForWriteFile, ReaderLinks.getSetForTree(ReaderLinks.getUniqueURL()));
    }
}
