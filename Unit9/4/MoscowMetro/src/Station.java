public class Station {

    public String name;
    public String lineNumber;
    public String lineName;
    public String transit;

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

    Station(String name, String lineNumber, String lineName, String transit){
        this.name = name;
        this.lineNumber = lineNumber;
        this.lineName = lineName;
        this.transit = transit;
    }

    Station(String name, String lineNumber, String lineName){
        this(name, lineNumber, lineName, "");
    }

    @Override
    public String toString() {
        return "Станция метро " + name + " (" + lineName + ")";
    }
}
