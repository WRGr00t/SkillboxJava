import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Loader {
    private static ArrayList<Station> stations = new ArrayList<>();
    private static TreeMap<String, String> lines = new TreeMap<>();
    private static TreeMap<Station, ArrayList<Station>> connections = new TreeMap<>();
    private static final int INDEX_MOSCOW_METRO_LIST = 3;
    private static final int INDEX_MOSCOW_MONORAIL_LIST = 4;
    private static final int INDEX_MOSCOW_CENTRAL_RING_LIST = 5;
    private static final int INDEX_OF_COLS_WITH_NUMBER = 0;
    private static final int INDEX_OF_COLS_WITH_NAME = 1;
    private static final int INDEX_OF_COLS_CONNECTIONS = 3;

    public static void main(String[] args) {
        String url = "https://ru.wikipedia.org/wiki/" +
                "%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D1%81%D1%82%D0%B0%D0%BD%D1" +
                "%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%" +
                "BE%D0%B3%D0%BE_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%" +
                "D1%82%D0%B5%D0%BD%D0%B0";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).maxBodySize(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseStation(doc, INDEX_MOSCOW_METRO_LIST);
        parseStation(doc, INDEX_MOSCOW_MONORAIL_LIST);
        parseStation(doc, INDEX_MOSCOW_CENTRAL_RING_LIST);
        parseLines();
        //System.out.println(craftMap());
        getConnections(doc);
        printConnections();
    }

    private static void parseStation(Document doc, int INDEX_OF_STATIONS_LIST) {
        int INDEX_OF_COLS_WITH_NUMBER = 0;
        int INDEX_OF_COLS_WITH_NAME = 1;

        Element table = doc.select("table").get(INDEX_OF_STATIONS_LIST);
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            if (i == 1 &&
                    ((INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_MONORAIL_LIST) ||
                            (INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_CENTRAL_RING_LIST))) {
                continue;
            }
            Element row = rows.get(i);
            Elements cols = row.select("td");
            String name = cols.get(INDEX_OF_COLS_WITH_NAME).select("a").attr("title");
            if (name.contains("(")) {
                name = name.substring(0, name.indexOf('(')).trim();
            }
            String lineNumber = cols.get(INDEX_OF_COLS_WITH_NUMBER).select("span").first().text();
            String lineName = cols.get(INDEX_OF_COLS_WITH_NUMBER).select("a").attr("title");
            stations.add(new Station(name, lineNumber, lineName));
        }
    }

    private static StringBuffer craftMap() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{\n")
                .append("\t\"stations\" : {\n");
        for (HashMap.Entry<String, String> entry : lines.entrySet()) {
            stringBuffer.append("\t\t\"" + entry.getKey() + "\" : [\n");
            for (Station station : stations) {
                if (station.getLineNumber().equals(entry.getKey())) {
                    stringBuffer.append("\t\t\t\"" + station.getName() + "\"\n");
                }
            }
            stringBuffer.append("\t\t],\n");
        }
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length() - 1)
                .append("\t},\n")
                .append("\t\"connections\": [");
        return stringBuffer;
    }

    private static void parseLines() {
        for (Station station : stations) {
            String lineName = station.getLineName();
            String lineNumber = station.getLineNumber();
            lines.put(lineNumber, lineName);
        }
    }

    private static void getConnections(Document doc) {
        ArrayList<Station> connects = new ArrayList<>();
        for (int INDEX_OF_STATIONS_LIST = INDEX_MOSCOW_METRO_LIST;
             INDEX_OF_STATIONS_LIST < INDEX_MOSCOW_CENTRAL_RING_LIST + 1; INDEX_OF_STATIONS_LIST++) {
            Element table = doc.select("table").get(INDEX_OF_STATIONS_LIST);
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                if (i == 1 && (INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_MONORAIL_LIST) ||
                        (INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_CENTRAL_RING_LIST)) {
                    continue;
                }
                Element row = rows.get(i);
                Elements cols = row.select("td");
                String name = cols.get(INDEX_OF_COLS_WITH_NAME).select("a").attr("title");
                String lineNumber = cols.get(INDEX_OF_COLS_WITH_NUMBER).select("span").first().text();
                String connect = cols.get(INDEX_OF_COLS_CONNECTIONS).text();
                String[] numberConnectLine = connect.split(" ");
                Elements elements = cols.get(INDEX_OF_COLS_CONNECTIONS).select("span");
                ArrayList<Station> connectStation = new ArrayList<>();
                ArrayList<String> descriptions = new ArrayList<>();
                for (int j = 1; j < elements.size(); j++){
                    String conStation = elements.get(j).attr("title");
                    if (!conStation.isEmpty()){
                        descriptions.add(conStation);
                    }
                }
                for (int j = 0; j < descriptions.size(); j++) {
                    if (!descriptions.get(j).isEmpty()){
                        connectStation.add(findStation(descriptions.get(j), numberConnectLine[j]));
                    }
                }
                Station station = findStation(name, lineNumber);
                if (station.getName().equals("Белорусская")){
                    System.out.println(station);
                    System.out.println(connectStation);
                }
                if (station != null){
                    connections.put(station, connectStation);
                }
            }
        }
    }

    private static Station findStation (String string, String lineNumber){
        for (Station station : stations){
            if (string.contains(station.getName()) && station.getLineNumber().equals(lineNumber)){
                return station;
            }
        }
        return null;
    }

    private static void printConnections(){
        /*for (HashMap.Entry<Station, ArrayList<Station>> entry : connections.entrySet()) {
            if (!entry.getValue().isEmpty()){
                System.out.println("Станция - " + entry.getKey() + " с переходами: " + entry.getValue());
            }
        }*/
        for (Station key : connections.keySet()) {
            System.out.println("Станция = " + key + ", переходы = " +  connections.get(key));
        }
    }
}
