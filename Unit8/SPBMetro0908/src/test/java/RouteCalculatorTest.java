import core.Line;
import core.Station;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class RouteCalculatorTest {

    //============== создаем линии нашего метро ========
    private HashMap<Integer, Line> lines = new HashMap<>();
    private Line greenLine, orangeLine, redLine;

    //============== создаем станции ================
    private TreeSet<Station> stations = new TreeSet<>();
    private Station Mayakovsky, Elisarovsky, Novocherkassky, LigovskyAvenue, NevskySquare1,
            NevskySquare2, Chernyshevsky, VosstaniyaSquare;

    //============ указываем узловые станции ============
    private TreeMap<Station, TreeSet<Station>> connectionStations = new TreeMap<>();
    private StationIndex smallMetro = new StationIndex();
    private RouteCalculator routeCalculator = new RouteCalculator(smallMetro);


    @Before
    public void setUp() throws Exception {

        greenLine = new Line(3, "Зеленая");
        orangeLine = new Line(4, "Оранжевая");
        redLine = new Line(1, "Красная");
        Mayakovsky = new Station("Маяковская", greenLine);
        Elisarovsky = new Station("Елизаровская", greenLine);
        Novocherkassky = new Station("Новочеркасская", orangeLine);
        LigovskyAvenue = new Station("Лиговский проспект", orangeLine);
        NevskySquare1 = new Station("Площадь Александра Невского Зеленая", greenLine);
        NevskySquare2 = new Station("Площадь Александра Невского Оранжевая", orangeLine);
        Chernyshevsky = new Station("Чернышевская", redLine);
        VosstaniyaSquare = new Station("Площадь восстания", redLine);
        smallMetro.addStation(Mayakovsky);
        smallMetro.addStation(NevskySquare1);
        smallMetro.addStation(NevskySquare2);
        smallMetro.addStation(Elisarovsky);
        smallMetro.addStation(Novocherkassky);
        smallMetro.addStation(LigovskyAvenue);
        smallMetro.addStation(Chernyshevsky);
        smallMetro.addStation(VosstaniyaSquare);

        List<Station> stationsInLine = Arrays.asList(Elisarovsky, NevskySquare1, Mayakovsky);
        greenLine.setStations(stationsInLine);

        stationsInLine = Arrays.asList(Novocherkassky, NevskySquare2, LigovskyAvenue);
        orangeLine.setStations(stationsInLine);

        stationsInLine = Arrays.asList(Chernyshevsky, VosstaniyaSquare);
        redLine.setStations(stationsInLine);

        smallMetro.setNumber2line(lines);
        smallMetro.addLine(greenLine);
        smallMetro.addLine(orangeLine);
        smallMetro.addLine(redLine);

        TreeMap<Station, TreeSet<Station>> connect = new TreeMap<>();

        TreeSet<Station> connectStation = new TreeSet<>();
        TreeSet<Station> buff2 = new TreeSet<>();
        TreeSet<Station> buff1 = new TreeSet<>();
        TreeSet<Station> buff3 = new TreeSet<>();
        connectStation.add(NevskySquare2);
        buff2.addAll(connectStation);
        connect.put(NevskySquare1, buff2);
        connectStation.clear();

        connectStation.add(VosstaniyaSquare);
        buff1.addAll(connectStation);
        connect.put(Mayakovsky, buff1);
        connectStation.clear();

        connectStation.add(Mayakovsky);
        buff3.addAll(connectStation);
        connect.put(VosstaniyaSquare, buff3);
        connectStation.clear();

        connectStation.add(NevskySquare1);
        connect.put(NevskySquare2, connectStation);
        smallMetro.setConnections(connect);
        System.out.println(smallMetro.connections);
    }

    @Test
    public void getShortestRoute_3_Station_On_1_Line() {
        routeCalculator = new RouteCalculator(smallMetro);
        List<Station> actual = routeCalculator.getShortestRoute(Mayakovsky, Elisarovsky);
        List<Station> expected = Arrays.asList(Mayakovsky, NevskySquare1, Elisarovsky);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getShortestRoute_to_same_station() {
        routeCalculator = new RouteCalculator(smallMetro);
        List<Station> actual = routeCalculator.getShortestRoute(Mayakovsky, Mayakovsky);
        List<Station> expected = Arrays.asList(Mayakovsky);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getShortestRoute_3_stations_with_one_transfer() {
        routeCalculator = new RouteCalculator(smallMetro);
        List<Station> actual = routeCalculator.getShortestRoute(Mayakovsky, Novocherkassky);
        List<Station> expected = Arrays.asList(Mayakovsky, NevskySquare1, NevskySquare2, Novocherkassky);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getShortestRoute_3_stations_with_two_transfer() {
        routeCalculator = new RouteCalculator(smallMetro);
        List<Station> actual = routeCalculator.getShortestRoute(Chernyshevsky, Novocherkassky);
        List<Station> expected = Arrays.asList(Chernyshevsky, VosstaniyaSquare, Mayakovsky, NevskySquare1,
                NevskySquare2, Novocherkassky);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void calculateDuration_3_Station_On_1_Line() {
        List<Station> route = routeCalculator.getShortestRoute(Mayakovsky, Elisarovsky);
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 2.5 + 2.5;
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void calculateDuration_to_same_station() {
        List<Station> route = routeCalculator.getShortestRoute(Mayakovsky, Mayakovsky);
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 0;
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void calculateDuration_3_stations_with_one_transfer() {
        List<Station> route = routeCalculator.getShortestRoute(Mayakovsky, Novocherkassky);
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 2.5 + 3.5 + 2.5;
        Assert.assertEquals(expected, actual, 0);
    }

    @Test
    public void calculateDuration_3_stations_with_two_transfer() {
        List<Station> route = routeCalculator.getShortestRoute(Chernyshevsky, Novocherkassky);
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 2.5 + 3.5 + 2.5 + 3.5 + 2.5;
        Assert.assertEquals(expected, actual, 0);
    }

    private static void printRoute(List<Station> route) //угоним для отладки
    {
        System.out.println(route);
        Station previousStation = null;
        for(Station station : route)
        {
            if(previousStation != null)
            {
                Line prevLine = previousStation.getLine();
                Line nextLine = station.getLine();
                if(!prevLine.equals(nextLine))
                {
                    System.out.println("\tПереход на станцию " +
                            station.getName() + " (" + nextLine.getName() + " линия)");
                }
            }
            System.out.println("\t" + station.getName());
            previousStation = station;
        }
    }
}