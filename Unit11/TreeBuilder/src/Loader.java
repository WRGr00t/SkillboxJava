import java.net.MalformedURLException;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;

public class Loader {
    public static void main(String[] args) throws MalformedURLException {
        String path = "https://skillbox.ru/";
        Link rootLink = new Link(path, Link.getSublink(path), 0);
        TreeBuilder treeBuilder = new TreeBuilder(rootLink);
        ConcurrentSkipListSet<String> links = new ForkJoinPool().invoke(treeBuilder);
        /*for (String string : links){
            ConcurrentSkipListSet<String> subLinks = new ForkJoinPool().invoke(new TreeBuilder(new Link(string, Link.getSublink(string), 1)));
            for (String s : subLinks){
                links.add(s);
            }
        }*/
        //ConcurrentSkipListSet<String> links = rootLink.getSublink(path);
        //TreeSet<String> resultSet = new TreeSet<>();
        treeBuilder.buildBuild(rootLink, 0);
        System.out.println(links.size());
    }

}
