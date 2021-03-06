import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class readAllLinks {

    public static ConcurrentSkipListSet<String> uniqueURL = new ConcurrentSkipListSet<>();
    public static String my_site;

    public static void main(String[] args) {

        readAllLinks obj = new readAllLinks();
        my_site = "https://skillbox.ru/";
        obj.get_links(my_site);
    }

    private void get_links(String url) {
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
            Elements links = doc.select("a");

            if (links.isEmpty()) {
                return;
            }
            links.stream()
                    .parallel()
                    .unordered()
                    .map((link) -> link.absUrl("href")).forEach((this_url) -> {
                boolean add = uniqueURL.add(this_url);
                if (add && this_url.contains(my_site)) {
                    System.out.println(this_url + " " + Thread.currentThread().getName() + " size = " + uniqueURL.size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get_links(this_url);
                }
            });

        } catch (IOException ex) {
        }
    }
}