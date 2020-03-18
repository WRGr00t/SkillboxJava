import java.util.Collection;
import java.util.Set;

public interface SiteNode {
    Set<String> getChildren(String url);
    Link getNode(String string);
}
