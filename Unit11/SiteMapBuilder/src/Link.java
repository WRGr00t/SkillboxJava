import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.TreeSet;

public class Link implements SiteNode, Comparable<Link>{
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

    public static Link getLink(String rootURL, int level){
        Set<String> linkSet = Link.getChildrenFromString(rootURL);
        Link rootLink = new Link(rootURL, linkSet, level);
        return rootLink;
    }

    public static Set<String> getChildrenFromString(String str){
        clearLink(str);
        Set<String> resultSet = new TreeSet<>();
        try {
            Document doc;
            Elements links;

            doc = Jsoup.connect(str).maxBodySize(0).get();
            links = doc.select("a");

            for (Element category : links) {
                String string = category.absUrl("href").trim();
                if (string.startsWith(Main.url)){
                    clearLink(string);
                    resultSet.add(string);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultSet;
    }

    public static String clearLinkWithChar(String string, CharSequence c){
        if (string.contains(c)){
            string = string.substring(0, string.indexOf((String) c));
        }
        return string;
    }

    public static String clearLink(String string){
        string = clearLinkWithChar(string, "?");
        string = clearLinkWithChar(string, "#");
        return string;
    }

    @Override
    public Set<String> getChildren() {
        TreeSet<String> resultSet = new TreeSet<>();
        try {
            Document doc;
            Elements links;

            doc = Jsoup.connect(link).maxBodySize(0).get();
            links = doc.select("a");

            for (Element category : links) {
                String string = category.absUrl("href").trim();
                //System.out.println(Thread.currentThread().getName() + ": waiting for: " + string + " in getChildren");
                clearLink(string);
                if (string.startsWith(link.trim()) && !string.contains(".pdf")) {
                    resultSet.add(string);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public Link getNode(String string) {
        return null;
    }


    @Override
    public int compareTo(Link link) {
        return this.getLink().compareTo(link.getLink());
    }
}
