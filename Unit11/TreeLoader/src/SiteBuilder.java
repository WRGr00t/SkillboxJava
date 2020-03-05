import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class SiteBuilder extends RecursiveTask<Set<Link>> {
    private String url;
    private int level;

    public SiteBuilder(String url, int level) {
        this.url = url;
        this.level = level;
    }

    @Override
    protected Set<Link> compute() {
        Link siteNode = Link.getLinkFromURL(url, level);
        if (SiteTree.alreadyParsedLinks.add(siteNode)){
            System.out.println(siteNode.getLink() + " " + Thread.currentThread().getName());
            List<SiteBuilder> subtask = new LinkedList<>();
            for (String childUrl : siteNode.getChildren()) {
                int childLevel = siteNode.getLevel() + 1;
                //Link childLink = new Link(childUrl, Link.getChildrenFromString(childUrl), childLevel);
                SiteBuilder task = new SiteBuilder(childUrl, childLevel);
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
