import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Loader {
    public static void main(String[] args) {
        Document doc;
        Elements links = new Elements();
        try {
            doc = Jsoup.connect("https://skillbox.ru/").maxBodySize(0).get();
            links = doc.select("a");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Element category : links) {
            System.out.println(category.select("a").attr("href"));
        }
        System.out.println(links.size());
    }
}
