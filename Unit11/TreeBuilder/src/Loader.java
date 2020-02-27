import java.net.MalformedURLException;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;

public class Loader {
    public static void main(String[] args) throws MalformedURLException {
        String path = "https://skillbox.ru/";
        Link rootLink = new Link(path, Link.getSublink(path), 0);
        //new ForkJoinPool().invoke(new TreeBuilder(path));
        ///ConcurrentSkipListSet<String> links = rootLink.getSublink(path);
        //TreeSet<String> resultSet = new TreeSet<>();
        System.out.println(rootLink.buildMap());
    }

}
