import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveTask;

public class TreeBuilder extends RecursiveTask<TreeSet<String>> implements Comparable<Link>{
    private final Link link;

    public TreeBuilder(Link link) {
        this.link = link;
    }

    @Override
    protected TreeSet<String> compute() {
        TreeSet<String> subLinks = link.getSubLinks();
        List<TreeBuilder> subTasks = new LinkedList<>();

        for (String sublink : subLinks){
            //System.out.println(sublink);
            TreeBuilder task = new TreeBuilder(new Link(sublink, Link.getSublink(sublink), link.getLevel() + 1));
            task.fork();
            System.out.println(Thread.currentThread().getName());
            subTasks.add(task);
        }

        for (TreeBuilder task : subTasks) {
            subLinks.addAll(task.join());
        }
        return subLinks;
    }

    @Override
    public int compareTo(Link l) {
        return link.getLink().compareTo(l.getLink());
    }

    public String buildBuild(Link link, int level) throws MalformedURLException {
        System.out.println("build...");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\t".repeat(level))
                .append(this)
                .append("\n");
        for (String url : link.getSubLinks()){
            System.out.println(url);
            stringBuffer.append(new Link(url, Link.getSublink(url), level + 1).buildMap());
        }
        return stringBuffer.toString();
    }

    @Override
    public String toString() {
        return "\t".repeat(link.getLevel()) + link.toString();
    }

}
