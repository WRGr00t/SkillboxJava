public class Station {

    public String name;
    public String lineNumber;
    public String lineName;
    public String transit;
    public String[] connections;

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

    public String[] getConnections() {
        return connections;
    }

    public String ConnectionToString() {
        String result = "";
        String[] connects = getConnections();
        if (connects.length > 0) {
            for (String string : connects) {
                result = result + " " + string;
            }
        }
        return result;
    }

    Station(String name, String lineNumber, String lineName, String transit, String[] connections) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.lineName = lineName;
        this.transit = transit;
        this.connections = connections;
    }

    Station(String name, String lineNumber, String lineName) {
        this(name, lineNumber, lineName, "", new String[0]);
    }

    @Override
    public String toString() {
        return "Станция метро " + name + " (" + lineName + ")";
    }
}
