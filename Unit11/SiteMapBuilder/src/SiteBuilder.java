import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class SiteBuilder extends RecursiveTask<Set<Link>> {
    private Link siteNode;

    public SiteBuilder(Link siteNode) {
        this.siteNode = siteNode;
    }

    Set<Link> tree = new TreeSet<>();

    @Override
    protected Set<Link> compute() {
        if (!tree.contains(siteNode)){
            System.out.println(siteNode.getLink() + " " + Thread.currentThread().getName());
            try {
                tree.add(siteNode);
            } catch (Exception e){
                System.out.println("Error " + e.getMessage());
            }
            List<SiteBuilder> subtask = new LinkedList<>();
            for (String child : siteNode.getChildren()) {
                Link childLink = new Link(child, Link.getChildrenFromString(child), siteNode.getLevel() + 1);
                SiteBuilder task = new SiteBuilder(childLink);
                task.fork();
                subtask.add(task);
            }
            for (SiteBuilder task : subtask){
                tree.addAll(task.join());
            }
        } else {
            System.out.println("This link is visited!");
        }
        return tree;
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
