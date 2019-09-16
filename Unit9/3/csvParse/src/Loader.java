import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import au.com.bytecode.opencsv.CSVReader;

public class Loader {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        Path pathToCSV = Paths.get("src/movementList.csv");
        ArrayList<Operation> operations = parseCSV(pathToCSV);
        double sumDebit = 0;
        double sumCredit = 0;
        for (int i = 0; i < operations.size(); i++) {
            sumDebit += operations.get(i).getDebit();
            sumCredit += operations.get(i).getCredit();
        }
        System.out.println("Сумма расходов перебором - " + sumDebit);
        System.out.println("Сумма расходов через стрим - "
                + operations.stream()
                            .mapToLong(operation -> (long) operation.getDebit()).sum());
        System.out.println("Сумма поступлений перебором - " + sumCredit);
        System.out.println("Сумма поступлений через стрим - "
                + operations.stream()
                .mapToLong(operation -> (long) operation.getCredit()).sum());
        System.out.println("Список расходов:");
        for (Operation operation : operations){
            if (operation.getDebit() > 0.0){
                System.out.println(operation.getOperationDescription());
            }
        }
    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    private static ArrayList<Operation> parseCSV(Path path) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(path.toFile()), ',', '"', 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<String[]> history = reader.readAll();
        reader.close();
        ArrayList<Operation> operations = new ArrayList<>();
        for (int i = 0; i < history.size(); i++) {
            String fragments[] = Arrays.toString(history.get(i)).split(",");
            int INDEX_DATE = 3;
            StringBuffer dateForParse = new StringBuffer(fragments[INDEX_DATE]);
            dateForParse.insert(7, "20"); //переходим на полный формат года
            String data = String.valueOf(dateForParse).trim();
            double credit = 0.0;
            double debit = 0.0;
            if (fragments[7].charAt(fragments[7].length() - 1) != ']') { //борьба с дробной частью, отделенной запятой
                fragments[7] = (fragments[7] + "." + fragments[8]); //путем склеивания двух фрагментов
            }
            credit = Double.parseDouble(fragments[6]);
            debit = Double.parseDouble(removeCharAt(fragments[7], fragments[7].length() - 1));
            Operation operation = new Operation(fragments[0], fragments[1], fragments[2],
                    LocalDate.parse(data, formatter), fragments[4], fragments[5],
                    credit, debit);

            if (operation != null) {
                operations.add(operation);
            }
        }
        return operations;
    }
}