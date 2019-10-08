import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;

public class Loader {
    private static Path target = Paths.get("src", "folderForImg");

    public static void main(String[] args) {
        Document doc;
        Elements links = new Elements();
        try {
            doc = Jsoup.connect("https://lenta.ru/").maxBodySize(0).get();
            links = doc.select("img");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Element category : links) {
            download(category.select("img").attr("abs:src"), target);
        }
    }

    private static void download(String sourceURL, Path targetDirectory) {
        try {
            URL url = new URL(sourceURL);
            String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1);
            Path targetPath = Paths.get(String.valueOf(targetDirectory), fileName);
            System.out.println("Скачан файл " + targetPath.getFileName());
            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
