import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.time.temporal.Temporal;
import java.util.HashSet;
import java.util.TreeSet;

public class Link {
    private URL url;
    private Link upLink;
    private int level;
    private TreeSet<String> downLinks;

    //===========конструкторы========//
    public Link(URL url, Link upLink) {
        this.url = url;
        this.upLink = upLink;
        this.level = upLink.getLevel() + 1;
        this.downLinks = getSublink(url.toString());
    }

    public Link(URL rootUrl) {
        this.url = url;
        level = 0;
    }

    //===========геттеры и сеттеры=========//
    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Link getUpLink() {
        return upLink;
    }

    public void setUpLink(Link upLink) {
        this.upLink = upLink;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TreeSet<String> getDownLinks() {
        return downLinks;
    }

    public void setDownLinks(TreeSet<String> downLinks) {
        this.downLinks = downLinks;
    }

    //============вспомогательные методы========//
    public static TreeSet<String> getSublink(String link) {
        TreeSet<String> resultSet = new TreeSet<>();
        try {
            Document doc;
            Elements links = new Elements();
            try {
                doc = Jsoup.connect(link).maxBodySize(0).get();
                links = doc.select("a");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            for (Element category : links) {
                String string = category.absUrl("href").trim();
                if (string.contains(link) && string.length() != link.length()) {
                    resultSet.add(string);
                }
            }
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return resultSet;
    }
}
