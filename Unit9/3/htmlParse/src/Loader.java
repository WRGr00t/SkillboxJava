import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;

public class Loader {
    private static Path target = Paths.get("src\\folderForImg");

    public static void main(String[] args) throws IOException {
        Document doc;
        Elements links = new Elements();
        try {
            doc = Jsoup.connect("https://lenta.ru/").get();
            links = doc.select("img");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Element category : links) {
            download(category.select("img").attr("abs:src"),target.toString());
        }
    }
    private static void download(String sourceURL, String targetDirectory) throws IOException
    {
        try {
            URL url = new URL(sourceURL);
            String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1);
            File target = new File(targetDirectory + File.separator + fileName);
            Path targetPath = target.toPath();
            System.out.println("Скачан файл " + targetPath.getFileName());
            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
