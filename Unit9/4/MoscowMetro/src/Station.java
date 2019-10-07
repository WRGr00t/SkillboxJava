public class Station {

    public String name;
    public String numberLine;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberLine() {
        return numberLine;
    }

    public void setNumberLine(String numberLine) {
        this.numberLine = numberLine;
    }

    Station(){
        name = "";
        numberLine = "";
    }

    Station(String name, String numberLine){
        this.name = name;
        this.numberLine = numberLine;
    }

    @Override
    public String toString() {
        return "Станция метро " + name + " линии №" + numberLine;
    }
}
