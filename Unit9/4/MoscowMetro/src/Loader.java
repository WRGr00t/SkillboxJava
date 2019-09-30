
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;

public class Loader {
    public static void main(String[] args) throws MalformedURLException {
        String url = "https://ru.wikipedia.org/wiki/" +
                "%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1" +
                "%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%" +
                "BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%" +
                "D1%82%D0%B5%D0%BD%D0%B0";
        Document doc;
        Elements links = new Elements();
        try {
            doc = Jsoup.connect(url).get();
            Jsoup.connect(url).maxBodySize(0);
            links = doc.select("tr");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Element category : links) {
            System.out.println(category.select("a[title]").text());
                //System.out.println(links);
        }
    }
}
