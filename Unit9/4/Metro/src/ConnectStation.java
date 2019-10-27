import com.google.gson.annotations.SerializedName;

public class ConnectStation {
    @SerializedName("line")
    private String lineNumber;
    @SerializedName("station")
    private String stationName;

    public ConnectStation(String lineNumber, String stationName) {
        this.lineNumber = lineNumber;
        this.stationName = stationName;
    }
}
