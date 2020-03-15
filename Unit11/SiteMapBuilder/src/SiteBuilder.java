import java.util.*;
import java.util.concurrent.RecursiveTask;

public class SiteBuilder extends RecursiveTask<Set<String>> {
    private String siteURL;

    public SiteBuilder(String siteURL) {
        this.siteURL = siteURL;
    }

    @Override
    protected Set<String> compute() {
        siteURL = Link.clearLink(siteURL);
        int level = (int) siteURL.codePoints().filter(ch -> ch == '/').count() - 3;
        if (SiteTree.alreadyParsedLinks.add(siteURL)) {
            //System.out.println(siteURL + " " + Thread.currentThread().getName());
            List<SiteBuilder> subtask = new LinkedList<>();
            Link siteNode = Link.getLink(siteURL, level);
            for (String child : siteNode.getChildren()) {
                Link childLink;
                if (child.equals(Main.url)) {
                    childLink = new Link(child, Link.getChildrenFromString(child), 0);
                } else {
                    childLink = new Link(child, Link.getChildrenFromString(child), siteNode.getLevel() + 1);
                }
                SiteTree.siteMapTree.add(childLink);
                SiteBuilder task = new SiteBuilder(child);
                task.fork();
                subtask.add(task);
            }
            for (SiteBuilder task : subtask) {
                SiteTree.alreadyParsedLinks.addAll(task.join());
            }
        } else {
            System.out.println("This link is visited!");
        }
        System.out.println("Спарсилось - " + SiteTree.getAlreadyParsedLinks().size());
        return SiteTree.alreadyParsedLinks;
    }

    public static Set<String> prepareStrings() {
        TreeSet<String> strings = new TreeSet<>();
        Set<String> resultSet = null;
        for (Link link : SiteTree.getSiteMapTree()){
            String s = link.getLink();
            s = "\t".repeat(link.getLevel()) + s;
            resultSet.add(s);
        }
        return resultSet;
    }
}
