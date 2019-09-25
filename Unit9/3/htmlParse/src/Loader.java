import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Loader {
    public static void main(String[] args) {
        Document doc;
        String title = "";
        try {
            doc = Jsoup.connect("https://lenta.ru/").get();
            title = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Jsoup Can read HTML page from URL, title : " + title);
    }
}
