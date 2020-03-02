import java.util.Collection;

public interface SiteNode {
    Collection<String> getChildren();
    Link getNode(String string);
}
