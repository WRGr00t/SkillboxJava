import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveTask;

public class TreeBuilder extends RecursiveTask<ConcurrentSkipListSet<String>> {
    private final String link;

    public TreeBuilder(String link) {
        this.link = link;
    }

    @Override
    protected ConcurrentSkipListSet<String> compute() {
        ConcurrentSkipListSet subLinks = Link.getSublink(link);
        List<TreeBuilder> subTasks = new LinkedList<>();

        for (String sublink : Link.getSublink(link)){
            TreeBuilder task = new TreeBuilder(sublink);
            task.fork();
            subLinks.add(task);
        }

        for (TreeBuilder task : subTasks) {
            subLinks.add(task.join());
        }
        return subLinks;
    }
}
