
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class Loader {
    public static void main(String[] args) {
        String url = "https://ru.wikipedia.org/wiki/" +
                "%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1" +
                "%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%" +
                "BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%" +
                "D1%82%D0%B5%D0%BD%D0%B0";
        Document doc = null;
        ArrayList<Station> stations = new ArrayList<>();
        int INDEX_OF_STATIONS_LIST = 3;
        int INDEX_OF_COLS_WITH_NAME = 1;
        int INDEX_OF_COLS_CONNECTIONS = 3;
        try {
            doc = Jsoup.connect(url).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = doc.select("table").get(INDEX_OF_STATIONS_LIST);
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            String name = cols.get(INDEX_OF_COLS_WITH_NAME).text();
            String lineName = cols.get(0).select("a").attr("title");
            String lineNumber = cols.get(0).text().substring(0,2);
            stations.add(new Station(name, lineNumber, lineName));
            String connect = cols.get(INDEX_OF_COLS_CONNECTIONS).text();
            if (!connect.equals("")){
                System.out.println(cols.get(INDEX_OF_COLS_WITH_NAME).text() + " переход на " + cols.get(INDEX_OF_COLS_CONNECTIONS).text());
            }
        }
        for (Station station : stations) {
            System.out.println(station);
        }
    }
}
