
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Loader {
    private static ArrayList<Station> stations = new ArrayList<>();
    private static TreeMap<String, String> lines = new TreeMap<>();
    private static HashMap<Station, Station[]> connections = new HashMap<>();
    private static final int INDEX_MOSCOW_METRO_LIST = 3;
    private static final int INDEX_MOSCOW_MONORAIL_LIST = 4;
    private static final int INDEX_MOSCOW_CENTRAL_RING_LIST = 5;

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
        parseMetro(doc, INDEX_MOSCOW_METRO_LIST);
        parseMetro(doc, INDEX_MOSCOW_MONORAIL_LIST);
        parseMetro(doc, INDEX_MOSCOW_CENTRAL_RING_LIST);
        //printStationWithConnection();
        //printStation("Каховская линия");
        //printLine();
        //printStation();
        parseLines();
        //printLines();
        parseConnections();
        StringBuffer stringBuffer = craftMap();
        System.out.println(stringBuffer);
    }

    private static void printStationWithConnection(){
        for (Station station : stations) {
            System.out.println(station);
            if (!station.getTransit().isEmpty()){
                System.out.println(station.ConnectionToString());
            }
        }
    }

    private static void printLine(){
        for (Station station : stations) {
            System.out.println(station.getLineName() + " #" + station.getLineNumber());
        }
    }

    private static void printStation(String lineName){
        for (Station station : stations) {
            if (station.getLineName().equals(lineName)){
                System.out.println(station);
            }
        }
    }

    private static void printStation(){
        for (Station station : stations) {
            System.out.println(station + " " + station.getTransit());
        }
    }

    private static void parseMetro(Document doc, int INDEX_OF_STATIONS_LIST){
        int INDEX_OF_COLS_WITH_NUMBER = 0;
        int INDEX_OF_COLS_WITH_NAME = 1;
        int INDEX_OF_COLS_CONNECTIONS = 3;

        Element table = doc.select("table").get(INDEX_OF_STATIONS_LIST);
        Elements rows = table.select("tr");
        for (int i = 1; i < rows.size(); i++) {
            if(i == 1 && (INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_MONORAIL_LIST) ||
                    (INDEX_OF_STATIONS_LIST == INDEX_MOSCOW_CENTRAL_RING_LIST)){
                continue;
            }
            Element row = rows.get(i);
            Elements cols = row.select("td");
            String name = cols.get(INDEX_OF_COLS_WITH_NAME).select("a").attr("title");
            if (name.contains("(")) {
                name = name.substring(0, name.indexOf('(')).trim();
            }
            String lineName = cols.get(INDEX_OF_COLS_WITH_NUMBER).select("a").attr("title");
            String lineNumber = cols.get(INDEX_OF_COLS_WITH_NUMBER).select("span").first().text();
            String connect = cols.get(INDEX_OF_COLS_CONNECTIONS).text();
            Elements elements = cols.get(INDEX_OF_COLS_CONNECTIONS).select("span");

            String[] numberConnectLine = connect.split(" ");
            ArrayList<String> connects = new ArrayList<>();
            if (numberConnectLine.length > 0) {
                for (int j = 1; j < elements.size(); j++){
                    String conStation = elements.get(j).attr("title");
                    if (!conStation.equals("")){
                        connects.add(conStation);
                        //System.out.println(conStation);
                    }
                }
                connect = cols.get(INDEX_OF_COLS_CONNECTIONS).select("span").attr("title") +
                        " (" + cols.get(INDEX_OF_COLS_CONNECTIONS).text() + ")";
                //System.out.println(connect);
            }
            stations.add(new Station(name, lineNumber, lineName, connect, numberConnectLine, connects));
            //System.out.println("Добавлена станция " + name);
        }
    }

    private static void FileWriter(String data) {
        File file = new File("/src/map.json");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseConnections(){
        for (Station station: stations){
            ArrayList<String> strings = station.getConnectLines();
            for (Station station1: stations){
                if (!station.equals(station1)){
                    for (String s : strings){
                        if (s.contains(station1.getName())){
                            //System.out.println(station + " <=> " + station1);
                            //System.out.println(station1.getConnectLines());
                        }
                    }
                }
            }
        }
    }

    private static void parseLines(){
        for (Station station : stations){
            String lineName = station.getLineName();
            String lineNumber = station.getLineNumber();
            lines.put(lineNumber, lineName);
        }
    }

    private static void printLines(){
        for (HashMap.Entry<String, String> entry : lines.entrySet()) {
            System.out.println("Линия №" + entry.getKey() + " - " + entry.getValue());
        }
    }

    private static StringBuffer craftMap(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{\n")
                .append("\t\"stations\" : {\n");
        for (HashMap.Entry<String, String> entry : lines.entrySet()) {
            stringBuffer.append("\t\t\"" + entry.getKey() + "\" : [\n");
            for (Station station : stations){
                if (station.getLineNumber().equals(entry.getKey())){
                    stringBuffer.append("\t\t\t\"" + station.getName() + "\"\n");
                }
            }
            stringBuffer.append("\t\t],\n");
        }
        stringBuffer.delete(stringBuffer.length()-2, stringBuffer.length()-1)
                .append("\t},\n")
                .append("\t\"connections\": [");
        return stringBuffer;
    }
}
