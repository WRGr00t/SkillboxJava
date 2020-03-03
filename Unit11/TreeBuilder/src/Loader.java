import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;

public class Loader {
    public static void main(String[] args) {
        String path = "https://lenta.ru/";
        Path pathForWriteFile = Paths.get("src/map.txt");
        TreeSet<String> linkSet = Link.getSublink(path);
        Link rootLink = new Link(path, linkSet, 0);
        ForkJoinPool pool = new ForkJoinPool();
        TreeBuilder treeBuilder = new TreeBuilder(rootLink);
        Set<TreeBuilder> treeBuilders = null;
        for (String string : linkSet){
            TreeBuilder tb = new TreeBuilder(new Link(string, Link.getSublink(string), 1));
            System.out.println(string);
            if (tb != null){
                treeBuilders.add(tb);
                pool.invoke(tb);
            } else {
                System.out.println(string);
            }
        }


        TreeSet<String> links = pool.invoke(treeBuilder);
        /*for (String string : links){
            TreeSet<String> subLinks = new ForkJoinPool().invoke(new TreeBuilder(new Link(string, Link.getSublink(string), 1)));
            for (String s : subLinks){
                links.add(s);
            }
        }*/
        //TreeSet<String> links = rootLink.getSublink(path);
        //TreeSet<String> resultSet = new TreeSet<>();
        //treeBuilder.buildBuild(rootLink, 0);
        System.out.println(links.size());
        TreeBuilder.saveToFile(pathForWriteFile, links);
        pool.shutdown();
    }

}
