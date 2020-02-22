import com.sun.source.tree.Tree;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Loader {
    public static void main(String[] args) throws MalformedURLException {
        String path = "https://skillbox.ru/";
        Link link = new Link(new URL(path));
        TreeSet<String> links = link.getSublink(path);
        TreeSet<String> resultSet = new TreeSet<>();

        for (String string : links) {
            System.out.println(string);
            TreeSet<String> linkDownLevel= Link.getSublink(string);
            TreeSet<String> downLinks = new TreeSet<>();
            //System.out.println("\t" + downlink);
            downLinks.addAll(linkDownLevel);
            Link downLink = new Link(new URL(string), link);
            //System.out.println(downLink.getLevel());
            resultSet = downLinks;
        }
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        TreeSet<String> finalResultSet = resultSet;
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName());
            for (String string : finalResultSet){
                System.out.println(string);
            }
            return Thread.currentThread().getName();
        };
        scheduledExecutorService.schedule(task, 500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.shutdown();
    }
}
