import java.util.Objects;

public class Station implements Comparable<Station> {
    private String name;
    private String lineNumber;
    private String lineName;

    public Station(String name, String lineNumber, String lineName) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.lineName = lineName;
    }

    public String getName() {
        return name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getLineName() {
        return lineName;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", lineNumber='" + lineNumber + '\'' +
                ", lineName='" + lineName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return name.equals(station.getName()) &&
                lineNumber.equals(station.getLineNumber()) &&
                lineName.equals(station.getLineName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lineNumber, lineName);
    }

    @Override
    public int compareTo(Station s) {
        if (name.compareTo(s.getName()) == 0){
            return lineNumber.compareTo(s.getLineNumber());
        }
        return name.compareTo(s.getName());
    }
}
