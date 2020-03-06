import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class ReaderLinks {
    public static ConcurrentSkipListSet<String> uniqueURL;
    public static String site;

    public ReaderLinks(ConcurrentSkipListSet uniqueURL, String site) {
        this.uniqueURL = uniqueURL;
        this.site = site;
    }

    public void get_links(String url) {
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
                boolean add = false;
                if (this_url.contains(site) && !this_url.contains(".pdf")) {
                    add = uniqueURL.add(this_url);
                }
                if (add && this_url.contains(site)) {
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
            ex.printStackTrace();
        }
    }

    public static ConcurrentSkipListSet<String> getUniqueURL() {
        return uniqueURL;
    }

    public static void saveToFile(Path path, Set<String> strings) throws IOException {
        FileWriter file = null;
        try {
            file = new FileWriter(path.toString());
            for (String string : strings) {
                file.write(string + "\n");
            }
            file.flush();
            System.out.println("File " + path + " saved successfully");
        } catch (IOException e) {
            System.err.println("Some problems with file " + e.getMessage());
        } finally {
            file.close();
        }

    }
}
