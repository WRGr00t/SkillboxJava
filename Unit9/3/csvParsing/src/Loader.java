import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Loader {
    public static void main(String[] args) {
        Path pathToCSV = Paths.get("src/movementList.csv");
        List<String> historyList = readFromCsvFile(",", pathToCSV.toString());
        ArrayList<Operation> operations = convertToOperation(historyList);
        System.out.println("Всего " + operations.size() + " записей");
        System.out.println("Сумма расходов - "
                + operations.stream()
                .mapToDouble(Operation::getDebit).sum());
        System.out.println("Сумма поступлений - "
                + operations.stream()
                .mapToDouble(Operation::getCredit).sum());
        int i = 1;
        double sum = 0;
        //operations.stream().collect(Collectors.groupingBy(Operation::getCounteragent), Collectors.reducing(operations));
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
            String typeAccount = strings[0];
            String numberAccount = strings[1];
            String currency = strings[2];
            int INDEX_DATE = 3;
            int INDEX_CHAR_CENTURY = 7;
            String COUNT_CENTURY = "20";
            StringBuffer dateForParse = new StringBuffer(strings[INDEX_DATE]);
            dateForParse.insert(INDEX_CHAR_CENTURY, COUNT_CENTURY); //переходим на полный формат года
            String data = String.valueOf(dateForParse).trim();
            LocalDate transactionDate = LocalDate.parse(data, formatter);
            String referenceTransactions = strings [4];
            String operationDescription = strings[5];
            double credit = Double.valueOf(strings[6]);
            double debit = Double.valueOf(strings[7]);
            operations.add(new Operation(typeAccount, numberAccount, currency, transactionDate, referenceTransactions,
                    operationDescription, credit, debit));
        }
        return operations;
    }
}
