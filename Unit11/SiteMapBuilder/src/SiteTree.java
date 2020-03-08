import java.util.concurrent.ConcurrentSkipListSet;

public class SiteTree {
    public static final ConcurrentSkipListSet<String> alreadyParsedLinks = new ConcurrentSkipListSet<>();
    public static final ConcurrentSkipListSet<Link> siteMapTree = new ConcurrentSkipListSet<>();

    public static ConcurrentSkipListSet<Link> getSiteMapTree() {
        return siteMapTree;
    }

    public static ConcurrentSkipListSet<String> getAlreadyParsedLinks() {
        return alreadyParsedLinks;
    }
}
