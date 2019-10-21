import java.util.*;
import java.util.stream.Collectors;

public class StationIndex
{
    HashMap<String, Line> number2line;
    TreeSet<Station> stations;
    TreeMap<Station, TreeSet<Station>> connections;

    public StationIndex()
    {
        number2line = new HashMap<>();
        stations = new TreeSet<>();
        connections = new TreeMap<>();
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public void addLine(Line line)
    {
        number2line.put(line.getNumber(), line);
    }

    public void addConnection(List<Station> stations)
    {
        for(Station station : stations)
        {
            if(!connections.containsKey(station)) {
                connections.put(station, new TreeSet<>());
            }
            TreeSet<Station> connectedStations = connections.get(station);
            connectedStations.addAll(stations.stream()
                    .filter(s -> !s.equals(station)).collect(Collectors.toList()));
        }
    }

    public Line getLine(String number)
    {
        return number2line.get(number);
    }

    public void setNumber2line(HashMap<String, Line> number2line) {
        this.number2line = number2line;
    }

    public void setStations(TreeSet<Station> stations) {
        this.stations = stations;
    }

    public void setConnections(TreeMap<Station, TreeSet<Station>> connections) {
        this.connections = connections;
    }


    public HashMap<String, Line> getNumber2line() {
        return number2line;
    }

    public Station getStation(String name)
    {
        for(Station station : stations)
        {
            if(station.getName().equalsIgnoreCase(name)) {
                return station;
            }
        }
        return null;
    }

    public Station getStation(String name, String lineNumber)
    {
        Station query = new Station(name, getLine(lineNumber));
        Station station = stations.ceiling(query);
        return station.equals(query) ? station : null;
    }

    public Set<Station> getConnectedStations(Station station)
    {
        if(connections.containsKey(station)) {
            return connections.get(station);
        }
        return new TreeSet<>();
    }
}
