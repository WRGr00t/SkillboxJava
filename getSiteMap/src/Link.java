import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

public class Link {
    URL link;
    HashSet<URL> sublinks;
    int level;
    Link upLink;

    public Link() {
    }

    public Link(URL link, Link upLink) throws MalformedURLException {
        this.link = link;
        this.upLink = upLink;
        level = upLink.getLevel() + 1;
        sublinks = getLinkBelowLevel(link.toString());
    }

    public Link(String stringLink) throws MalformedURLException {
        this.link = new URL(stringLink);
        upLink = new Link();
        level = upLink.getLevel() + 1;
        System.out.println(stringLink);
        System.out.println(link.toString());
        sublinks = getLinkBelowLevel(link.toString());
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public HashSet<URL> getSublinks() {
        return sublinks;
    }

    public void setSublinks(HashSet<URL> sublinks) {
        this.sublinks = sublinks;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Link getUpLink() {
        return upLink;
    }

    public void setUpLink(Link upLink) {
        this.upLink = upLink;
    }

    public static HashSet<URL> getLinkBelowLevel(String link) throws MalformedURLException {
        System.out.println("Парсинг страницы " + link);
        HashSet<URL> resultSet = new HashSet<>();
        Document doc;
        Elements links = new Elements();
        try {
            doc = Jsoup.connect(link).maxBodySize(0).get();
            links = doc.select("a");
            System.out.println("Найдено ссылок - " + links.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Element category : links) {
            String absUrl = category.absUrl("href");
            if (!absUrl.isEmpty()) {
                if (absUrl.equals(link)) {
                    continue;
                }
                if (absUrl.contains(link)) {
                    resultSet.add(new URL(absUrl));
                }
            }
        }
        return resultSet;
    }

    public String buildMap() throws MalformedURLException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\t".repeat(this.level))
                .append(this)
                .append("\n");
        for (URL url : sublinks){
            stringBuffer.append(new Link(url.toString()).buildMap());
        }
        return stringBuffer.toString();
    }

    @Override
    public String toString() {
        return "\t".repeat(level) + link.toString();
    }
}




