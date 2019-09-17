import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static String staffFile = "data/staff.txt";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final int DATE_LABEL = 2017;
    private static final int INTERVAL = 2;
    private static Date date, dateNow = new Date();
    private static Calendar calendar = new GregorianCalendar();

    public static void main(String[] args) {
        ArrayList<Employee> staff = loadStaffFromFile();
        staff.stream()
                .filter(e -> (e.getWorkStart().getYear() == DATE_LABEL))
                .max(Comparator.naturalOrder())
                .ifPresent(max -> System.out.println(
                        "Сотрудник с максимальной зарплатой, принятый в 2017 году - " + max));

        staff.stream()
                .filter(e -> (e.getWorkStart().getYear() == DATE_LABEL))
                .min(Comparator.naturalOrder())
                .ifPresent(min -> System.out.println(
                        "Сотрудник с минимальной зарплатой, принятый в 2017 году - " + min));
        //============ А теперь полетели =========

        Airport airport = Airport.getInstance();
        calendar.add(Calendar.HOUR, INTERVAL);
        date = calendar.getTime();
        List<Terminal> terminals = airport.getTerminals();
        ArrayList<Flight> result = new ArrayList<Flight>();
        for (int i = 1; i < terminals.size(); i++) {
            result.addAll(terminals.get(i).getFlights());
        }
        System.out.printf("В ближайшие %d часа вылетают следующие рейсы:%n",INTERVAL);
        result.stream()
                .filter(e -> (e.getDate().before(date) && e.getDate().after(dateNow)))
                .filter(e -> e.getType() == Flight.Type.DEPARTURE)
                .map(e -> e.toString() + " борт " + e.getAircraft().getModel())
                .forEach(System.out::println);
    }

    private static ArrayList<Employee> loadStaffFromFile() {
        ArrayList<Employee> staff = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for (String line : lines) {
                String[] fragments = line.split("\t");
                if (fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                LocalDate localDate = LocalDate.parse(fragments[2], formatter);
                staff.add(new Employee(
                        fragments[0],
                        Integer.parseInt(fragments[1]),
                        localDate
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}