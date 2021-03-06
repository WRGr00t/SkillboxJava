import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.TreeSet;

public class Link {
    private String link;
    private TreeSet<String> subLinks;
    private int level;

    public Link(String link, TreeSet<String> subLinks, int level) {
        this.link = link;
        this.subLinks = subLinks;
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public TreeSet<String> getSubLinks() {
        return subLinks;
    }

    public void setSubLinks(TreeSet<String> subLinks) {
        this.subLinks = subLinks;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public static TreeSet<String> getSublink(String link) {
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

    public String buildMap() {
        System.out.println("build...");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\t".repeat(this.level))
                .append(this)
                .append("\n");
        for (String url : this.subLinks) {
            System.out.println(url);
            stringBuffer.append(new Link(url, getSublink(url), this.getLevel() + 1).buildMap());
        }
        return stringBuffer.toString();
    }

    @Override
    public String toString() {
        return "\t".repeat(level) + link.toString();
    }
}
