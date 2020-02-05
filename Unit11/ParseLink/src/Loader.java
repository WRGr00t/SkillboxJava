import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class Loader {

    public static void main(String[] args) {
        final String link = "https://lenta.ru";
        String path = "tree.txt";
        HashSet<String> linkList = new HashSet<>();
        linkList = getSublink(link);
        linkList = filterSet(linkList, link);
        for (String string : linkList) {
            HashSet<String> newResultSet;
            getSublink(string);
            newResultSet = filterSet(linkList, string);
            writeFromSet(newResultSet, path);
            //System.out.println(newResultSet.size());
        }
        //System.out.println(linkList.size());
    }

    private static HashSet<String> getSublink(String link) {
        HashSet<String> resultSet = new HashSet<>();
        Document doc;
        Elements links = new Elements();
        try {
            doc = Jsoup.connect(link).maxBodySize(0).get();
            links = doc.select("a");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Element category : links) {
            String string = category.select("a").attr("href");
            if (string != null) {
                resultSet.add(string);
            }
        }
        return resultSet;
    }

    private static HashSet<String> filterSet(HashSet<String> unfilter, String link) {
        HashSet<String> result = new HashSet<>();
        for (String string : unfilter) {
            if (string.length() > 0) {
                int index1 = string.indexOf("https://");
                int index2 = string.indexOf("http://");
                int index3 = string.indexOf("mailto:");
                if (index1 == -1 && index2 == -1 && index3 == -1) {
                    String str = "";
                    if (string.lastIndexOf("/") == string.length() - 1) {
                        str = string.replaceFirst(".$", "");
                    }
                    result.add(link.concat(str));
                }
            }
        }
        return result;
    }

    private static void writeToFile(String text, String path) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void writeFromSet(HashSet<String> textSet, String path) {
        for (String string : textSet) {
            writeToFile(string + "\n", path);
        }
    }
}
