import java.util.ArrayList;

public class Station {

    private String name;
    private String lineNumber;
    private String lineName;
    private String transit;
    private String[] numberConnectLines;
    private ArrayList<String> connectLines;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getTransit() {
        return transit;
    }

    public void setTransit(String transit) {
        this.transit = transit;
    }

    public String[] getNumberConnectLines() {
        return numberConnectLines;
    }

    public ArrayList<String> getConnectLines() {
        return connectLines;
    }

    public String ConnectionToString() {
        String result = "";
        ArrayList<String> connects = getConnectLines();
        if (connects.size() > 0) {
            for (String string : connects) {
                result = result + "\n" + string;
            }
        }
        return result;
    }

    Station(String name, String lineNumber, String lineName, String transit, String[] connections, ArrayList<String> connectLines) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.lineName = lineName;
        this.transit = transit;
        this.numberConnectLines = connections;
        this.connectLines = connectLines;
    }

    Station(String name, String lineNumber, String lineName) {
        this(name, lineNumber, lineName, "", new String[0], new ArrayList<String>());
    }

    @Override
    public String toString() {
        return "Станция метро " + name + " (" + lineName + ")";
    }
}
