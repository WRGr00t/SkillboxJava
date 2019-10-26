import java.util.ArrayList;

public class Line {
    private String number;
    private String name;
    transient private ArrayList<Station> stations;

    public Line(String name, String number, ArrayList<Station> stations) {
        this.name = name;
        this.number = number;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "Line{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", stations=" + stations +
                '}';
    }
}
