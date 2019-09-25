import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Loader {
    public static void main(String[] args) {
        Path pathToCSV = Paths.get("src/movementList.csv");
        List<String> historyList = readFromCsvFile(",", pathToCSV.toString());
        ArrayList<Operation> operations = convertToOperation(historyList);
        System.out.println("Всего " + operations.size() + " записей");
        System.out.println("Сумма расходов - "
                + operations
                .stream()
                .mapToDouble(Operation::getDebit)
                .sum());
        System.out.println("Сумма поступлений - "
                + operations
                .stream()
                .mapToDouble(Operation::getCredit)
                .sum());
        Map<String, Double> newOp = operations
                .stream()
                .collect(
                        Collectors
                                .groupingBy(Operation::getCounteragent,
                                Collectors.summingDouble(Operation::getCredit)));
        for (Map.Entry<String, Double> entry : newOp.entrySet()) {
            if (entry.getValue() > 0 ){
                System.out.println(entry.getKey() + "\t поступило \t" + entry.getValue());
            }
        }
        newOp = operations
                .stream()
                .collect(
                        Collectors.groupingBy(Operation::getCounteragent,
                                Collectors.summingDouble(Operation::getDebit)));
        for (Map.Entry<String, Double> entry : newOp.entrySet()) {
            if (entry.getValue() > 0 ){
                System.out.println(entry.getKey() + "\t потрачено \t" + entry.getValue());
            }
        }
    }

    public static List<String> readFromCsvFile(String separator, String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            List<String> list = new ArrayList<>();
            String line = "";
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            list.remove(0); //удаление шапки
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Operation> convertToOperation(List<String> arrayStringFromCSV) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        ArrayList<Operation> operations = new ArrayList<>();

        for (int recNo = 0; recNo < arrayStringFromCSV.size(); recNo++) {
            String stringForEdit = arrayStringFromCSV.get(recNo);
            if (stringForEdit.contains("\"")) { //избавляемся от подстрок вида "123,5"
                String numberWithQuotes = stringForEdit.substring((stringForEdit.indexOf("\"") + 1),
                        stringForEdit.lastIndexOf("\""));
                String[] parts = numberWithQuotes.split(",");
                String numberAfterEdit = parts[0] + "." + parts[1];
                stringForEdit = stringForEdit.substring(0, stringForEdit.indexOf("\"")) + numberAfterEdit +
                        stringForEdit.substring(stringForEdit.lastIndexOf("\""),
                                stringForEdit.length()-1);
            }
            String[] strings = stringForEdit.split(",");
            int INDEX_TYPE = 0;
            int INDEX_NUMBER = 1;
            int INDEX_CURRENCY = 2;
            int INDEX_DATE = 3;
            int INDEX_CHAR_CENTURY = 7;
            String COUNT_CENTURY = "20";
            int INDEX_REFERENCE = 4;
            int INDEX_DESCRIPTION = 5;
            int INDEX_CREDIT = 6;
            int INDEX_DEBIT = 7;

            String typeAccount = strings[INDEX_TYPE];
            String numberAccount = strings[INDEX_NUMBER];
            String currency = strings[INDEX_CURRENCY];
            StringBuffer dateForParse = new StringBuffer(strings[INDEX_DATE]);
            dateForParse.insert(INDEX_CHAR_CENTURY, COUNT_CENTURY); //переходим на полный формат года
            String data = String.valueOf(dateForParse).trim();
            LocalDate transactionDate = LocalDate.parse(data, formatter);
            String referenceTransactions = strings [INDEX_REFERENCE];
            String operationDescription = strings[INDEX_DESCRIPTION];
            double credit = Double.valueOf(strings[INDEX_CREDIT]);
            double debit = Double.valueOf(strings[INDEX_DEBIT]);

            operations.add(new Operation(typeAccount, numberAccount, currency, transactionDate, referenceTransactions,
                    operationDescription, credit, debit));
        }
        return operations;
    }
}
