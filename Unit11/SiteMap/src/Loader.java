import com.sun.source.tree.Tree;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.*;

public class Loader {
    private static int count = 0;

    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        String path = "https://skillbox.ru/";
        Link link = new Link(new URL(path));
        TreeSet<String> links = link.getSublink(path);
        TreeSet<String> resultSet = new TreeSet<>();

        for (String string : links) {
            TreeSet<String> linkDownLevel = Link.getSublink(string);
            System.out.println("\t" + string);
            resultSet.add(string);
            Link downLink = new Link(new URL(string), link);
            //System.out.println(downLink.getLevel());
        }
        for (String string : resultSet){
            System.out.println(string);
        }
        System.out.println("Размер итогового набора - " + resultSet.size());
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> {
            count++;
            System.out.println("Running...task1 - count : " + count);
        };

        // init Delay = 5, repeat the task every 1 second
        ScheduledFuture<?> scheduledFuture = ses.scheduleAtFixedRate(task1, 5, 1, TimeUnit.SECONDS);

        while (true) {
            System.out.println("count :" + count);
            Thread.sleep(1000);
            if (count == 5) {
                System.out.println("Count is 5, cancel the scheduledFuture!");
                scheduledFuture.cancel(true);
                ses.shutdown();
                break;
            }
        }
    }
}
