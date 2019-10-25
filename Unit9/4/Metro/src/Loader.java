import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Loader {
    private static ArrayList<Station> stations = new ArrayList<>();
    private static ArrayList<Line> lineArrayList = new ArrayList<>();
    private static TreeMap<String, String> lines = new TreeMap<>();
    private static TreeMap<Station, ArrayList<Station>> connections = new TreeMap<>();
    private static ArrayList<ArrayList<Station>> list = new ArrayList<>();
    private static HashSet<ArrayList<Station>> connects = new HashSet<>();
    private static StringBuffer stringBuffer = new StringBuffer();
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

            parseStation(doc, INDEX_MOSCOW_METRO_LIST);
            parseStation(doc, INDEX_MOSCOW_MONORAIL_LIST);
            parseStation(doc, INDEX_MOSCOW_CENTRAL_RING_LIST);
            parseLines();
            getConnections(doc);
            printStationOnMap();
            printConnections();
            printLines();
            Path filePath = Paths.get("src", "map.json");
            Files.writeString(filePath, stringBuffer, StandardCharsets.UTF_16, StandardOpenOption.CREATE);
            getLines();
            saveToJSON();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parseStation(Document doc, int indexOfStationsList) {
        int INDEX_OF_COLS_WITH_NUMBER = 0;
        int INDEX_OF_COLS_WITH_NAME = 1;

        Element table = doc.select("table").get(indexOfStationsList);
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            if (i == 1 &&
                    ((indexOfStationsList == INDEX_MOSCOW_MONORAIL_LIST) ||
                            (indexOfStationsList == INDEX_MOSCOW_CENTRAL_RING_LIST))) {
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

    private static StringBuffer printStationOnMap() {
        stringBuffer.append("{\n")
                .append("\t\"stations\" : {\n");
        for (HashMap.Entry<String, String> entry : lines.entrySet()) {
            stringBuffer.append("\t\t\"" + entry.getKey() + "\" : [\n");
            for (Station station : stations) {
                if (station.getLineNumber().equals(entry.getKey())) {
                    stringBuffer.append("\t\t\t\"" + station.getName() + "\",\n");
                }
            }
            stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length() - 1)
                    .append("\t\t],\n");
        }
        stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length() - 1)
                .append("\t},\n");
        return stringBuffer;
    }

    private static void saveToJSON() throws IOException {
        Gson builder = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Path filePath = Paths.get("src", "mapGson.json");
        builder.toJson(lines, new FileWriter(String.valueOf(filePath)));

        /*for (Station station : stations){
            try {
                //String json = builder.toJson(station);
                builder.toJson(station, new FileWriter(String.valueOf(filePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }

    private static void parseLines() {
        for (Station station : stations) {
            String lineName = station.getLineName();
            String lineNumber = station.getLineNumber();
            lines.put(lineNumber, lineName);
        }
    }

    private static void getLines(){
        for (HashMap.Entry<String, String> entry : lines.entrySet()) {
            String nameLine = "";
            String numberLine = "";
            ArrayList<Station> stationsList = new ArrayList<>();
            for (Station station : stations){
                if (entry.toString().contains(station.getLineNumber())){
                    nameLine = station.getLineName();
                    numberLine = station.getLineNumber();
                    stationsList.add(station);
                }
            }
            Line line = new Line(nameLine, numberLine, stationsList);
            lineArrayList.add(line);
        }
    }

    private static void getConnections(Document doc) {
        for (int INDEX_OF_STATIONS_LIST = INDEX_MOSCOW_METRO_LIST;
             INDEX_OF_STATIONS_LIST < INDEX_MOSCOW_CENTRAL_RING_LIST + 1; INDEX_OF_STATIONS_LIST++) {
            Element table = doc.select("table").get(INDEX_OF_STATIONS_LIST);
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) {
                if ((i == 1) && ((INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_MONORAIL_LIST) ||
                        (INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_CENTRAL_RING_LIST))) {
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
                for (int j = 1; j < elements.size(); j++) {
                    String conStation = elements.get(j).attr("title");
                    if (!conStation.isEmpty()) {
                        descriptions.add(conStation);
                    }
                }
                try {
                    for (int j = 0; j < descriptions.size(); j++) {
                        if (!descriptions.get(j).isEmpty()) {
                            connectStation.add(findStation(descriptions.get(j), numberConnectLine[j]));
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Шелепиха и Хорошево не парсятся, на итог не повлияет из-за избыточности информации");
                }
                Station station = findStation(name, lineNumber);
                if (!station.equals(new Station("Станция", " не", " найдена")) &&
                        !connectStation.contains(new Station("Станция", " не", " найдена")) &&
                        !connectStation.isEmpty()) {
                    connections.put(station, connectStation);
                }
            }
        }
        /*for (Station key : connections.keySet()) {
            System.out.println("Станция " + key + ", переходы = " +  connections.get(key));
        }*/
        for (Station key : connections.keySet()) {
            ArrayList<Station> connect = new ArrayList<>();
            connect.add(key);
            connect.addAll(connections.get(key));
            list.add(connect);
        }
    }

    private static Station findStation(String string, String lineNumber) {
        String crutch = "Рижская линия"; //из-за этой строки в переходах станция Рижская
        for (Station station : stations) {
            if (string.contains(crutch)) {
                string = string.substring(0, (string.length() - crutch.length()));
            }
            if (string.contains(station.getName()) && station.getLineNumber().equals(lineNumber)) {
                return station;
            }
        }
        return new Station("Станция", " не", " найдена");
    }

    private static void printConnections() {
        stringBuffer.append("\t\"connections\": [");
        Comparator<Station> comparator = Comparator.comparing(obj -> obj.getName());
        comparator = comparator.thenComparing(obj -> obj.getLineNumber());
        for (ArrayList<Station> stations : list) {
            stations.sort(comparator);
            connects.add(stations);
        }
        stringBuffer.append("\t\n");
        for (ArrayList<Station> stations : connects) {
            stringBuffer.append("\t[");
            for (Station station : stations) {
                stringBuffer.append("\t\t\n\t\t\t{\n")
                        .append("\t\t\t\t\"line\": \"" + station.getLineNumber() + "\",\n")
                        .append("\t\t\t\t\"station\": \"" + station.getName() + "\"\n")
                        .append("\t\t\t},");
            }
            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
            stringBuffer.append("\n\t\t],\n\t");
        }
        stringBuffer.delete(stringBuffer.length() - 3, stringBuffer.length() - 1);
        stringBuffer.append("\n\t],\n");

    }

    private static void printLines() {
        stringBuffer.append("\t\"lines\" : [");
        for (HashMap.Entry<String, String> entry : lines.entrySet()) {
            stringBuffer.append("\n\t\t{\n\t\t\t\"number\" : \"" + entry.getKey() + "\",\n")
                    .append("\t\t\t\"name\" : \"" + entry.getValue() + "\"\n\t\t},");
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        stringBuffer.append("\n\t]\n}");
    }
}
