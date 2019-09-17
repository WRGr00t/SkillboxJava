import core.Station;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RouteCalculator
{
    private StationIndex stationIndex;

    private static double interStationDuration = 2.5;
    private static double interConnectionDuration = 3.5;

    public RouteCalculator(StationIndex stationIndex) //калькулятор конкретного метро
    {
        this.stationIndex = stationIndex;
    }

    public List<Station> getShortestRoute(Station from, Station to)
    {
        List<Station> route = getRouteOnTheLine(from, to);
        if(route != null) {
            return route;
        }

        route = getRouteWithOneConnection(from, to);
        if(route != null) {
            return route;
        }

        route = getRouteWithTwoConnections(from, to);
        return route;
    }

    public static double calculateDuration(List<Station> route)
    {
        double duration = 0;
        Station previousStation = null;
        for(int i = 0; i < route.size(); i++)
        {
            Station station = route.get(i);
            if(i > 0)
            {
                duration += previousStation.getLine().equals(station.getLine()) ?
                    interStationDuration : interConnectionDuration;
            }
            previousStation = station;
        }
        return duration;
    }

    //=========================================================================

    private List<Station> getRouteOnTheLine(Station from, Station to)
    {
        if(!from.getLine().equals(to.getLine())) {
            return null;
        }
        ArrayList<Station> route = new ArrayList<>();
        List<Station> stations = from.getLine().getStations();
        int direction = 0;
        for(Station station : stations)
        {
            if(direction == 0)
            {
                if(station.equals(from)) {
                    direction = 1;
                } else if(station.equals(to)) {
                    direction = -1;
                }
            }

            if(direction != 0) {
                route.add(station);
            }

            if((direction == 1 && station.equals(to)) ||
                (direction == -1 && station.equals(from))) {
                break;
            }
        }
        if(direction == -1) {
            Collections.reverse(route);
        }
        return route;
    }

    private List<Station> getRouteWithOneConnection(Station from, Station to) //путь с одной пересадкой
    {
        if(from.getLine().equals(to.getLine())) { //если линии станций отправления и назначения совпадают, то это не к нам
            return null;
        }

        ArrayList<Station> route = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations(); //берем все станции ветки станции отправления
        List<Station> toLineStations = to.getLine().getStations(); //берем все станции ветки станции назначения
        for(Station srcStation : fromLineStations) //перебираем станции обоих веток
        {
            for(Station dstStation : toLineStations)
            {
                if(isConnected(srcStation, dstStation)) //в поисках пересадочной
                {
                    ArrayList<Station> way = new ArrayList<>();
                    way.addAll(getRouteOnTheLine(from, srcStation)); //как только нашли собираем две половинки пути в путь
                    way.addAll(getRouteOnTheLine(dstStation, to));
                    if(route.isEmpty() || route.size() > way.size()) //если маршрут так и остался пустой или маршрут больше пути
                    {
                        route.clear();
                        route.addAll(way); //берем за маршрут путь, так как он либо единственный, либо минимальный
                    }
                }
            }
        }
        if (route.size() == 0) {
            return null;
        } else {
            return route;
        }
    }

    private List<Station> getRouteWithTwoConnections(Station from, Station to)
    {
        if (from.getLine().equals(to.getLine())) {
            return null;
        }

        ArrayList<Station> route = new ArrayList<>();

        List<Station> fromLineStations = from.getLine().getStations();
        List<Station> toLineStations = to.getLine().getStations();
        for(Station srcStation : fromLineStations)
        {
            for (Station dstStation : toLineStations)
            {
                List<Station> connectedLineRoute =
                        getRouteViaConnectedLine(srcStation, dstStation);
                if(connectedLineRoute == null) {
                    continue;
                }
                ArrayList<Station> way = new ArrayList<>();
                way.addAll(getRouteOnTheLine(from, srcStation));
                way.addAll(connectedLineRoute);
                way.addAll(getRouteOnTheLine(dstStation, to));
                if(route.isEmpty() || route.size() > way.size())
                {
                    route.clear();
                    route.addAll(way);
                }
            }
        }

        return route;
    }

    private boolean isConnected(Station station1, Station station2)
    {
        Set<Station> connected = stationIndex.getConnectedStations(station1); //все станции, на которые можно попасть при пересадке с указанной
        return connected.contains(station2); //есть ли в этом списке указанная станция
    }

    private List<Station> getRouteViaConnectedLine(Station from, Station to)
    {
        Set<Station> fromConnected = stationIndex.getConnectedStations(from);
        Set<Station> toConnected = stationIndex.getConnectedStations(to);
        for(Station srcStation : fromConnected)
        {
            for(Station dstStation : toConnected)
            {
                if(srcStation.getLine().equals(dstStation.getLine())) {
                    return getRouteOnTheLine(srcStation, dstStation);
                }
            }
        }
        return null;
    }
}