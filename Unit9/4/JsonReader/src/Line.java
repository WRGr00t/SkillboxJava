import java.util.ArrayList;
import java.util.List;

public class Line implements Comparable<Line>
{
    private String number;
    private String name;

    private List<Station> stations;

    public Line(String number, String name)
    {
        this.number = number;
        this.name = name;
        stations = new ArrayList<>();
    }

    public String getNumber()
    {
        return number;
    }

    public String getName()
    {
        return name;
    }

    public void addStation(Station station)
    {
        stations.add(station);
    }

    public List<Station> getStations()
    {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override
    public int compareTo(Line line) {
        if (name.compareTo(line.getName()) == 0){
            return number.compareTo(line.getNumber());
        }
        return name.compareTo(line.getName());
    }

    @Override
    public boolean equals(Object obj)
    {
        return compareTo((Line) obj) == 0;
    }
}