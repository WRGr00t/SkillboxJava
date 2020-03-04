import java.util.concurrent.ConcurrentSkipListSet;

public class SiteTree {
    public static final ConcurrentSkipListSet<Link> alreadyParsedLinks = new ConcurrentSkipListSet<>();

    public static ConcurrentSkipListSet<Link> getAlreadyParsedLinks() {
        return alreadyParsedLinks;
    }
}
