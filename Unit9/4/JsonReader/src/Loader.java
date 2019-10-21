import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.ls.LSOutput;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Loader {
    private static String dataFile = "src/map.json";
    private static StationIndex stationIndex;

    public static void main(String[] args) {
        createStationIndex();
        for (HashMap.Entry<String, Line> entry : stationIndex.getNumber2line().entrySet()) {
            System.out.println(entry.getValue().getName() + " имеет " + entry.getValue().getStations().size() + " станций");
        }
        //System.out.println(entry.getKey() + " - " + stationIndex.number2line.entrySet().size() + " станций");
    }

    private static String getJsonFile()
    {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            lines.forEach(line -> builder.append(line));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

    private static void createStationIndex() //создаем метро из файла
    {
        stationIndex = new StationIndex();
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonData = (JSONObject) parser.parse(getJsonFile());

            JSONArray linesArray = (JSONArray) jsonData.get("lines");
            parseLines(linesArray);

            JSONObject stationsObject = (JSONObject) jsonData.get("stations");
            parseStations(stationsObject);

            JSONArray connectionsArray = (JSONArray) jsonData.get("connections");
            parseConnections(connectionsArray);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void parseStations(JSONObject stationsObject)
    {
        stationsObject.keySet().forEach(lineNumberObject ->
        {
            String lineNumber = (String) lineNumberObject;
            Line line = stationIndex.getLine((String) lineNumberObject);
            JSONArray stationsArray = (JSONArray) stationsObject.get(lineNumberObject);
            stationsArray.forEach(stationObject ->
            {
                Station station = new Station((String) stationObject, line);
                stationIndex.addStation(station);
                line.addStation(station);
            });
        });
    }

    private static void parseConnections(JSONArray connectionsArray)
    {
        connectionsArray.forEach(connectionObject ->
        {
            JSONArray connection = (JSONArray) connectionObject;
            List<Station> connectionStations = new ArrayList<>();
            connection.forEach(item ->
            {
                JSONObject itemObject = (JSONObject) item;
                String lineNumber = ((String) itemObject.get("line"));
                String stationName = (String) itemObject.get("station");

                Station station = stationIndex.getStation(stationName, lineNumber);
                if(station == null)
                {
                    throw new IllegalArgumentException("core.Station " +
                            stationName + " on line " + lineNumber + " not found");
                }
                connectionStations.add(station);
            });
            stationIndex.addConnection(connectionStations);
        });
    }

    private static void parseLines(JSONArray linesArray)
    {
        linesArray.forEach(lineObject -> {
            JSONObject lineJsonObject = (JSONObject) lineObject;
            Line line = new Line(
                    ((String) lineJsonObject.get("number")),
                    (String) lineJsonObject.get("name")
            );
            stationIndex.addLine(line);
        });
    }
}
