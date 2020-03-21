import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.TreeSet;

public class TreeNode implements Comparable<TreeNode>, SiteNode {
    private String link;
    private Set<String> subLinks;
    private int level;

    public TreeNode(String url){
        System.out.println(url);
        if (!url.isEmpty()){
            getNode(url);
        }
    }

    public TreeNode(String link, Set<String> subLinks, int level) {
        this.link = link;
        this.subLinks = subLinks;
        this.level = level;
    }

    public String getLink() {
        return link;
    }

    public Set<String> getSubLinks() {
        return subLinks;
    }

    public int getLevel() {
        return level;
    }

    public static String clearLink(String string){
        return string.replaceAll("[?#].*", "");
    }

    @Override
    public SiteNode getNode(String string) {
        if (string.startsWith(Main.url)){
            TreeSet<String> children = getChildren(string);
            if (children.isEmpty()){
                System.out.println("empty");
            }
            int level = (int) string.codePoints().filter(ch -> ch == '/').count() - 3;

            return new TreeNode(string, children, level);
        } else {
            return null;
        }
    }

    @Override
    public TreeSet<String> getChildren(String url) {
        url = clearLink(url);
        TreeSet<String> resultSet = new TreeSet<>();
        try {
            Document doc;
            Elements links;
            System.out.println(url + " из начала");
            doc = Jsoup.connect(url).maxBodySize(0).get();
            links = doc.select("a");

            for (Element category : links) {
                String string = category.absUrl("href").trim();
                System.out.println(string);
                string = clearLink(string);
                if (string.startsWith(Main.url.trim()) && !string.contains(".pdf")) {
                    resultSet.add(string);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public int compareTo(TreeNode treeNode) {
        if (!treeNode.getLink().isEmpty()){
            return this.getLink().compareTo(treeNode.getLink());
        } else {
            return 0;
        }
    }
}
