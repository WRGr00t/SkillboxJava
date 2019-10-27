import java.util.ArrayList;
import java.util.TreeMap;

public class Metro {
    private TreeMap<String, ArrayList<Station>> stations;
    private ArrayList<ArrayList<Station>> connections;
    private ArrayList<Line> lines;

    public Metro(TreeMap<String, ArrayList<Station>> stations, ArrayList<ArrayList<Station>> connections, ArrayList<Line> lines) {
        this.stations = stations;
        this.connections = connections;
        this.lines = lines;
    }
}
