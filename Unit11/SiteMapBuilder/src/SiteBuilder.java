import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class SiteBuilder extends RecursiveTask<Set<String>> {
    private String siteURL;

    public SiteBuilder(String siteURL) {
        this.siteURL = siteURL;
    }

    @Override
    protected Set<String> compute() {
        if (SiteTree.alreadyParsedLinks.add(siteURL)){
            //System.out.println(siteURL + " " + Thread.currentThread().getName());
            List<SiteBuilder> subtask = new LinkedList<>();
            int level = (int) siteURL.codePoints().filter(ch -> ch == '/').count() - 3;
            Link siteNode = Link.getLink(siteURL, level);
            for (String child : siteNode.getChildren()) {
                Link childLink;
                if (child.equals(Main.url)){
                    childLink = new Link(child, Link.getChildrenFromString(child), 0);
                } else {
                    childLink = new Link(child, Link.getChildrenFromString(child), siteNode.getLevel() + 1);
                }
                SiteTree.siteMapTree.add(childLink);
                SiteBuilder task = new SiteBuilder(child);
                task.fork();
                subtask.add(task);
            }
            for (SiteBuilder task : subtask){
                SiteTree.alreadyParsedLinks.addAll(task.join());
            }
        } else {
            System.out.println("This link is visited!");
        }
        return SiteTree.alreadyParsedLinks;
    }

    public static void saveToFile(Path path, Set<Link> strings){
        try {
            FileWriter file = new FileWriter(path.toString());
            for (Link string : strings){
                file.write("\t".repeat(string.getLevel()) + string.getLink() + "\n");
            }
            file.flush();
            file.close();
            System.out.println("File " + path + " saved successfully");
        } catch (IOException e) {
            System.err.println("Some problems with file " + e.getMessage());
        }
    }
}
