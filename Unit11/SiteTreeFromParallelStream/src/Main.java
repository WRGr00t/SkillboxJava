import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://skillbox.ru/";
        Path pathForWriteFile = Paths.get("src/map.txt");
        ConcurrentSkipListSet<String> uniqueURL = new ConcurrentSkipListSet<>();
        ReaderLinks reader = new ReaderLinks(uniqueURL, url);
        reader.getLinks(url);
        ReaderLinks.saveToFile(pathForWriteFile, ReaderLinks.getSetForTree(ReaderLinks.getUniqueURL()));
        TreeSet<String> treeSet, resultSet = new TreeSet<>();
        treeSet = ReaderLinks.loadFromFile(pathForWriteFile);
        TreeSet<Link> links = ReaderLinks.getLinks(treeSet);
        for (Link link : links){
            System.out.println("\t".repeat(link.getLevel() - 1) + link.getUrl());
        }
        /*for (String s : treeSet){
            try {
                if (s.contains("?")){
                    s = s.substring(0, s.indexOf("?") - 1);
                }
            } catch (Exception e){
                e.getMessage();
                System.out.println("Ошибка на строке - " + s);
            }
            int level = (int) s.codePoints().filter(ch -> ch == '/').count();
            s = "\t".repeat(level - 2) + s;
            resultSet.add(s);
        }
        for (String string : resultSet){
            System.out.println(string);
        }*/
    }
}
