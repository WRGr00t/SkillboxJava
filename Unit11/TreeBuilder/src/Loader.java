import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;

public class Loader {
    public static void main(String[] args) throws MalformedURLException {
        String path = "https://skillbox.ru/";
        Path pathForWriteFile = Paths.get("src/map.txt");
        Link rootLink = new Link(path, Link.getSublink(path), 0);
        TreeBuilder treeBuilder = new TreeBuilder(rootLink);
        TreeSet<String> links = new ForkJoinPool().invoke(treeBuilder);
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
    }

}
