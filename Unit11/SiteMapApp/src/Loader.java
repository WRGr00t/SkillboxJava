import java.net.MalformedURLException;
import java.util.TreeSet;

public class Loader {
    public static void main(String[] args) {
        String site = "https://lenta.ru/";
        Link link = new Link(site);
        TreeSet<Link> allLinks = new TreeSet<>();
        try {
            System.out.println(link.buildMap());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        for (String string : link.getSubLinks()){
            Link subLink = new Link(string, Link.getSublink(string), link.getLevel() + 1);

        }
    }
}
