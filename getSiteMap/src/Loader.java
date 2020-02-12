import java.net.MalformedURLException;

public class Loader {
    public static void main(String[] args) throws MalformedURLException {
        Link link = new Link("https://lenta.ru/");
        System.out.println(link.buildMap());
    }
}
