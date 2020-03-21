import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class TreeBuilder extends RecursiveTask<Set<TreeNode>> {
    private TreeNode linkURL;

    public TreeBuilder(TreeNode linkURL) {
        this.linkURL = linkURL;
    }

    @Override
    protected Set<TreeNode> compute() {
        if (SiteTree.siteMapTree.add(linkURL)){
            System.out.println(linkURL.getLink() + " in thread " + Thread.currentThread().getName());
            List<TreeBuilder> subtask = new LinkedList<>();
            for (String child : linkURL.getChildren(linkURL.getLink())){
                TreeBuilder task = new TreeBuilder(new TreeNode(child));
                subtask.add(task);
                task.fork();
            }
            for (TreeBuilder task : subtask) {
                SiteTree.siteMapTree.addAll(task.join());
            }
        } else {
            System.out.println("This link is visited!");
        }
        System.out.println("Спарсилось - " + SiteTree.getSiteMapTree().size());
        return SiteTree.siteMapTree;
    }
}
