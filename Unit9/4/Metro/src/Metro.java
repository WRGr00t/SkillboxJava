import java.util.ArrayList;
import java.util.TreeMap;

public class Metro {
    private TreeMap<String, ArrayList<String>> stations;
    private ArrayList<ArrayList<ConnectStation>> connections;
    private ArrayList<Line> lines;

    public Metro(TreeMap<String, ArrayList<String>> stations, ArrayList<ArrayList<ConnectStation>> connections, ArrayList<Line> lines) {
        this.stations = stations;
        this.connections = connections;
        this.lines = lines;
    }
}
