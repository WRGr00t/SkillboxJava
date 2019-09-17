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
    public static ArrayList<Alias> aliases;
    public static void main(String[] args) throws Exception {

        Path pathToCSV = Paths.get("src/movementList.csv");
        ArrayList<Operation> operations = parseCSV(pathToCSV);
        initAlias();
        double sumDebit = 0;
        double sumCredit = 0;
        for (int i = 0; i < operations.size(); i++) {
            sumDebit += operations.get(i).getDebit();
            sumCredit += operations.get(i).getCredit();
        }
        System.out.println("Сумма расходов перебором - " + sumDebit);
        System.out.println("Сумма расходов через стрим - "
                + operations.stream()
                            .mapToDouble(operation -> operation.getDebit()).sum());
        System.out.println("Сумма поступлений перебором - " + sumCredit);
        System.out.println("Сумма поступлений через стрим - "
                + operations.stream()
                .mapToDouble(operation -> operation.getCredit()).sum());
        System.out.println("Список расходов:");
        for (Alias alias : aliases){
            double result = 0;
            for (Operation operation : operations){
                if (operation.getDebit() > 0.0 && operation.getOperationDescription().equals(alias.description)){
                    result =+ (operation.getCredit() - operation.getDebit());
                }
            }
            if (result > 0){
                System.out.println(alias.getNameForUser() + " - поступления на сумму - " + result);
            } else if (result < 0){
                System.out.println(alias.getNameForUser() + " - расходы на сумму - " + Math.abs(result));
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
            int INDEX_CHAR_CENTURY = 7;
            String COUNT_CENTURY = "20";
            StringBuffer dateForParse = new StringBuffer(fragments[INDEX_DATE]);
            dateForParse.insert(INDEX_CHAR_CENTURY, COUNT_CENTURY); //переходим на полный формат года
            String data = String.valueOf(dateForParse).trim();
            double credit = 0.0;
            double debit = 0.0;
            int INDEX_CREDIT = 6;
            int INDEX_DEBIT = 7;
            int INDEX_DEBIT_LAST_PART = 8;
            if (fragments[INDEX_DEBIT].charAt(fragments[INDEX_DEBIT].length() - 1) != ']') { //борьба с дробной частью, отделенной запятой
                fragments[7] = (fragments[INDEX_DEBIT] + "." + fragments[INDEX_DEBIT_LAST_PART]); //путем склеивания двух фрагментов
            }
            credit = Double.parseDouble(fragments[INDEX_CREDIT]);
            debit = Double.parseDouble(removeCharAt(fragments[INDEX_DEBIT], fragments[INDEX_DEBIT].length() - 1));
            int INDEX_TYPE = 0;
            int INDEX_NUMBER = 1;
            int INDEX_CURRENCY = 2;
            int INDEX_REFERENCE = 4;
            int INDEX_DESCRIPTION = 5;

            Operation operation = new Operation(fragments[INDEX_TYPE], fragments[INDEX_NUMBER], fragments[INDEX_CURRENCY],
                    LocalDate.parse(data, formatter), fragments[INDEX_REFERENCE], fragments[INDEX_DESCRIPTION],
                    credit, debit);
            operations.add(operation);
        }
        return operations;
    }
    private static void initAlias(){
        Alias alfaC2C = new Alias("CARD2CARD ALFA_MOBILE", "Перевод с карты на карту Альфа-банк");
        Alias kuschavel = new Alias("KUSCHAVEL", "Кафе Кушавель");
        Alias alfaIss = new Alias("Alfa Iss", "Альфа-чек");
        Alias yandexTaxi = new Alias("YANDEX TAXI", "Яндекс-такси");
        Alias subway = new Alias("SUBWAY", "Кафе Сабвэй");
        Alias ryabin = new Alias("RYABINOVAYA 5", "Рябиновая, 5");
        Alias amazon = new Alias("AWS EMEA", "Amazon");
        Alias delivery = new Alias("delivery club", "Delivery Club");
        Alias zoo = new Alias("ZOOMAGAZIN 4", "Зоомагазин");
        Alias hetzner = new Alias("WWW HETZNER D", "Hetzner Online");
        Alias yandexEda = new Alias("YANDEX EDA", "Яндекс Еда");
        Alias dixi = new Alias("DIXY", "ГК Дикси");
        Alias google = new Alias("GOOGLE GOOGLE", "Google");
        Alias l_etoilel = new Alias("L ETOILE", "Л'Этуаль");
        Alias raikhona = new Alias("RAIKHONA", "Ресторан Райхона");
        Alias fastSpring = new Alias("FSPRG UK", "FastSpring");
        Alias kfc = new Alias("KFC ASHAN MAR", "KFC в Ашане");
        Alias zotman = new Alias("ZOTMAN", "ресторан Zotman Pizza Pie");
        Alias vpsnet = new Alias("VPS NET", "vps.net");
        Alias loveRepublic = new Alias("LOVE REPUBLIC", "магазин Love Republic");
        aliases.add(alfaC2C);
        aliases.add(kuschavel);
        aliases.add(alfaIss);
        aliases.add(yandexTaxi);
        aliases.add(subway);
        aliases.add(ryabin);
        aliases.add(amazon);
        aliases.add(delivery);
        aliases.add(zoo);
        aliases.add(hetzner);
        aliases.add(yandexEda);
        aliases.add(dixi);
        aliases.add(google);
        aliases.add(l_etoilel);
        aliases.add(raikhona);
        aliases.add(fastSpring);
        aliases.add(kfc);
        aliases.add(zotman);
        aliases.add(vpsnet);
        aliases.add(loveRepublic);
    }
}