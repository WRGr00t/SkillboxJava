import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

public class ReaderLinks {
    public static ConcurrentSkipListSet<String> uniqueURL;
    public static String site;

    public ReaderLinks(ConcurrentSkipListSet<String> uniqueURL, String site) {
        ReaderLinks.uniqueURL = uniqueURL;
        ReaderLinks.site = site;
    }

    public void getLinks(String url) {
        try {
            Document doc = Jsoup.connect(url).maxBodySize(0).userAgent("Mozilla").get();
            Elements links = doc.select("a");

            if (links.isEmpty()) {
                return;
            }
            links.stream()
                    .parallel()
                    .unordered()
                    .map((link) -> link.absUrl("href")).forEach((thisUrl) -> {
                if (thisUrl.contains("?")) {
                    thisUrl = thisUrl.substring(0, thisUrl.indexOf("?") - 1);
                }
                if (thisUrl.startsWith(site) && !thisUrl.contains(".pdf")) {
                    if (uniqueURL.add(thisUrl)) {
                        //System.out.println(thisUrl + " " + Thread.currentThread().getName() + " size = " + uniqueURL.size());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getLinks(thisUrl);
                    }
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ConcurrentSkipListSet<String> getUniqueURL() {
        return uniqueURL;
    }

    public static void saveToFile(Path path, Set<String> strings) throws IOException {
        Files.write(path, strings);
       /* FileWriter file = null;
        try {
            file = new FileWriter(path.toString());
            for (String string : strings) {
                file.write(string + "\n");
            }
            file.flush();
            System.out.println("File " + path + " saved successfully");
        } catch (IOException e) {
            System.err.println("Some problems with file " + e.getMessage());
        } finally {
            file.close();
        }*/
    }

    public static TreeSet<String> loadFromFile(Path path) throws IOException {

        TreeSet<String> resultSet;
        resultSet = Files.lines(path).collect(Collectors.toCollection(TreeSet::new));
        /*try {
            File file = new File(path);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                resultSet.add(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return resultSet;
    }

    public static TreeSet<String> getSetForTree(ConcurrentSkipListSet<String> uniqueURL) {
        TreeSet<String> resultSet = new TreeSet<>();
        for (String string : uniqueURL) {
            resultSet.addAll(uniqueURL);
            int level = (int) string.codePoints().filter(ch -> ch == '/').count();
            resultSet.add("\t".repeat(level - 2) + string);
        }
        return resultSet;
    }

    public static TreeSet<Link> getLinks(TreeSet<String> uniqueURL) {
        TreeSet<Link> resultSet = new TreeSet<>();
        for (String string : uniqueURL) {
            int level = (int) string.codePoints().filter(ch -> ch == '/').count() - 2;
            Link link = new Link(string, level);
            resultSet.add(link);
        }
        return resultSet;
    }

    public static String getMapFromLinks(TreeSet<Link> uniqueLink) {
        String result = "";
        for (Link link : uniqueLink) {
            result = "\t".repeat(link.getLevel()) + link.getUrl();
        }
        return result;
    }


}
