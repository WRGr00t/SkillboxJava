import java.util.TreeSet;

public interface SiteNode {
    SiteNode getNode(String string);
    TreeSet<String> getChildren(String string);
}
