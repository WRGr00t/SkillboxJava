import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.TreeSet;

public class Link implements Comparable<Link>{
    private String link;
    private Set<String> subLinks;
    private int level;

    public Link(String link, Set<String> subLinks, int level) {
        this.link = link;
        this.subLinks = subLinks;
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public int getLevel() {
        return level;
    }

    public static Link getRootLink(String rootURL){
        Set<String> linkSet = Link.getChildrenFromString(rootURL);
        Link rootLink = new Link(rootURL, linkSet, 0);
        return rootLink;
    }

    public static Link getLinkFromURL(String url, int level){
        Set<String> linkSet = Link.getChildrenFromString(url);
        Link link = new Link(url, linkSet, level);
        return link;
    }

    public static Set<String> getChildrenFromString(String str){
        Set<String> resultSet = new TreeSet<>();
        try {
            Document doc;
            Elements links;

            doc = Jsoup.connect(str).maxBodySize(0).get();
            links = doc.select("a");

            for (Element category : links) {
                String string = category.absUrl("href").trim();
                Connection.Response response = Jsoup.connect(string).timeout(10 * 1000).execute();
                System.out.println(string + " filter");
                String contentType = response.contentType();
                if (contentType.equals("text/html; charset=utf-8")){
                        resultSet.add(string);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultSet;
    }

    public Set<String> getChildren() {
        TreeSet<String> resultSet = new TreeSet<>();
        try {
            Document doc;
            Elements links;

            doc = Jsoup.connect(link).maxBodySize(0).get();
            links = doc.select("a");

            for (Element category : links) {
                String string = category.absUrl("href").trim();
                if (string.contains(link) && string.length() != link.length()) {
                    resultSet.add(string);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public int compareTo(Link link) {
        return this.getLink().compareTo(link.getLink());
    }
}
