import com.skillbox.airport.Aircraft;
import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.util.Date;
import java.util.List;

public class Loader
{
    public static void main(String[] args) {
        Airport airportDomodedovo = Airport.getInstance();
        System.out.println(airportDomodedovo.getAllAircrafts());
        List list = airportDomodedovo.getAllAircrafts();
        System.out.println("Итого " + list.size() + " видов самолетов");
        Aircraft myAircraft = new Aircraft("Kukuruznik");
        System.out.println("Вас обслуживает борт " + myAircraft.toString());
        Flight myFirstFly = new Flight("002", Flight.Type.ARRIVAL, new Date(), myAircraft);
        System.out.println("Ваш рейс - " + myFirstFly.toString());
        Terminal terminal = new Terminal("#254");
        terminal.addFlight(myFirstFly);
        terminal.addParkingAircraft(myAircraft);
        System.out.println("В текущем терминале готовится к полету - " + terminal.getParkedAircrafts());
    }
}
