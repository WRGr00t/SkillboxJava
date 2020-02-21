import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

public class Loader {
    public static void main(String[] args) throws MalformedURLException {
        String path = "https://skillbox.ru/";
        Link link = new Link(new URL(path));
        HashSet<String> links = link.getSublink(path);
        for (String string : links) {
            System.out.println(string);
            HashSet<String> linkDownLevel= Link.getSublink(string);
            HashSet<URL> downLinks = new HashSet<>();
            for (String downlink : linkDownLevel){
                System.out.println("\t" + downlink);
                downLinks.add(new URL(downlink));
            }
            Link downLink = new Link(new URL(string), link);
        }
    }
}
