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
        }
    }
}
