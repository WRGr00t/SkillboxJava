public class Line {
    private String lineNumber;
    private String lineName;

    @Override
    public String toString() {
        return "Line{" +
                "lineNumber='" + lineNumber + '\'' +
                ", lineName='" + lineName + '\'' +
                '}';
    }

    public Line(String lineNumber, String lineName) {
        this.lineNumber = lineNumber;
        this.lineName = lineName;
    }
}
