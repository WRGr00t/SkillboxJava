import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

public class Link {
    private URL url;
    private Link upLink;
    private int level;
    private HashSet<URL> downLinks;

    //===========конструкторы========//
    public Link(URL url, Link upLink) {
        this.url = url;
        this.upLink = upLink;
        this.level = upLink.getLevel() + 1;
        this.downLinks = getSublink(upLink);
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

    public HashSet<URL> getDownLinks() {
        return downLinks;
    }

    public void setDownLinks(HashSet<URL> downLinks) {
        this.downLinks = downLinks;
    }

    //============вспомогательные методы========//
    public static HashSet<String> getSublink(String link) {
        HashSet<String> resultSet = new HashSet<>();
        try {
            Document doc;
            Elements links = new Elements();
            try {
                doc = Jsoup.connect(link).maxBodySize(0).get();
                links = doc.select("a");
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Element category : links) {
                String string = category.absUrl("href").trim();
                if (string.contains(link) && string.length() != link.length()) {
                    resultSet.add(string);
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return resultSet;
    }
}
