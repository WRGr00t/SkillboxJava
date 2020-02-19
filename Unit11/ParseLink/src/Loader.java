import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashSet;

public class Loader {

    public static void main(String[] args) {
        final String link = "https://skillbox.ru";
        String path = "tree.txt";
        HashSet<String> linkSet;
        linkSet = startParse(link);
        if (linkSet.size() != 0){
            writeFromSet(linkSet, path);
        }
        linkSet = readFromFile(path);
    }

    private static HashSet<String> readFromFile(String path){
        HashSet<String> set = new HashSet<>();
        try {
            File file = new File("tree.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            set.add(line);
            while (line != null) {
                line = reader.readLine();
                set.add(line);
            }
            for (String string : set){
                System.out.println( string);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    private static HashSet<String> startParse(String startLink){
        HashSet<String> set;
        set = getSublink(startLink);
        set = filterSet(set, startLink);
        return set;
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
                    String s = link.concat(str);
                    if (!s.equals(link)){
                        result.add(s);
                    }
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
