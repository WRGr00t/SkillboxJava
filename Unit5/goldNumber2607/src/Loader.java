import java.util.*;

public class Loader {

    private static final String CHARS[] = new String[]{"А", "В", "Е", "К", "М", "Н",
            "О", "Р", "С", "Т", "У", "Х"}; //допустимые буквы
    private static final String REGION[] = new String[]{"01", "02", "102", "03", "04", "05", "06",
            "07", "08", "09", "10", "11", "12", "13", "113", "14", "15", "16", "116", "716", "17",
            "18", "19", "21", "121", "22", "23", "93", "123", "24", "84", "88", "124", "25", "125",
            "26", "126", "27", "28", "29", "30", "31", "32", "33", "34", "134",
            "35", "36", "136", "37", "38", "85", "138", "39", "91", "40", "41", "42", "142", "43", "44", "45", "46",
            "47", "147", "48", "49", "50", "90", "150", "190", "750", "51", "52", "152", "53", "54", "154", "55",
            "56", "57", "58", "59", "81", "159", "60", "61", "161", "62", "63", "163", "763", "64", "164", "65",
            "66", "96", "67", "68", "69", "70", "71", "72", "73", "173", "74", "174", "75", "80", "76", "77",
            "97", "99", "177", "197", "199", "777", "799", "78", "98", "178", "198", "79", "82", "83", "86", "186",
            "87", "89", "92", "94", "20", "95", "196"}; //номера регионов
    public static ArrayList<String> number = new ArrayList<>();
    public static ArrayList<String> blatNumber = new ArrayList<>();
    public static ArrayList<String> blatNumberRegion = new ArrayList<>();
    public static HashSet<String> numberInHash = new HashSet<>();
    public static TreeSet<String> numberInTree = new TreeSet<>();
    enum Algorithm {LINE, BYLINE};

    public static void main(String[] args) {

//===============генерация номеров
        add00X(); //"000", "001", "002", "003", "004", "005", "006", "007", "008", "009" - номера до 10
        addX00(); //"100", "200", "300", "400", "500", "600", "700", "800", "900" - номера, кратные 100
        addGoldNumber(); // "111", "222", "333", "444", "555", "666", "777", "888", "999" - "золотые" номера
        addMirrorNumber();// "010".."989" - зеркальные номера
        addHillNumber(); // "012", "123" .. "987" - "горки"
        generateGoldNumber(); // формируем номера без региона
        //generateFullNumber(); // формируем окончательный вид номера
        Collections.sort(blatNumber); // сортируем сформированный массив
        numberInHash = new HashSet<String>(blatNumber); //переносим массив в Hash
        numberInTree = new TreeSet<String>(blatNumber); //переносим массив в Tree

 /* //================ вывод номеров на печать
        for (int i = 0; i < number.size(); i++) {
            System.out.print(number.get(i) + " ");
            if (i % 20 == 0) {
                System.out.println('\n');
            }
        }
        for (int i = 0; i < blatNumberRegion.size(); i++) {
            System.out.print(blatNumberRegion.get(i) + " ");
            if (i % 20 == 0) {
                System.out.println('\n');
            }
        }*/
        System.out.println('\n' + "Количество номеров в базе - " + blatNumber.size());
        System.out.println("Введите интересующий номер в формате А123ВС (кириллица)(для выхода напишите EXIT):");
        for (; ; ) {
            Scanner num = new Scanner(System.in);
            String checkNumber = num.next();
            if (checkNumber.toLowerCase().equals("exit")) {
                break;
            }
            long start = System.currentTimeMillis();
            long startTimeLine = System.nanoTime();
            System.out.println("Линейный поиск...");
            сountingTimeArray(checkNumber, blatNumber, Algorithm.LINE);

            System.out.println("Бинарный поиск...");
            start = System.currentTimeMillis();
            long startTimeBin = System.nanoTime();
            сountingTimeArray(checkNumber, blatNumber, Algorithm.BYLINE);

            System.out.println("Поиск по HashSet...");
            start = System.currentTimeMillis();
            long startTimeHash = System.nanoTime();
            сountingTimeSet(checkNumber, numberInHash);


            System.out.println("Поиск по TreeSet...");
            start = System.currentTimeMillis();
            long startTimeTree = System.nanoTime();
            сountingTimeSet(checkNumber, numberInTree);
        }
        // System.out.println('\n' + "Количество номеров - " + blatNumberRegion.size());
    }

    public static void add00X() {
        for (int digit = 0; digit < 10; digit++) {
            number.add("00" + digit); //"00n"
        }
    }

    public static void addX00() {
        for (int digit = 1; digit < 10; digit++) {
            number.add(digit + "00"); //"n00"
        }
    }

    public static void addGoldNumber() { //"XXX"
        for (int digit = 1; digit < 10; digit++) {
            number.add("" + digit + digit + digit);
        }
    }

    public static void addMirrorNumber() { //"XYX"
        for (int digitFirstLast = 0; digitFirstLast < 10; digitFirstLast++) {
            for (int digitAverage = 0; digitAverage < 10; digitAverage++) {
                if (digitAverage != digitFirstLast) {
                    number.add("" + digitFirstLast + digitAverage + digitFirstLast);
                }
            }
        }
    }

    public static void addHillNumber() {
        for (int startDigit = 0; startDigit < 9; startDigit++) {
            number.add("" + startDigit + (startDigit + 1) + (startDigit + 2)); //"012"
            number.add("" + (startDigit + 2) + (startDigit + 1) + startDigit); //"987"
        }
    }

    public static void generateGoldNumber() {
        for (int firstChar = 0; firstChar < CHARS.length; firstChar++) {
            for (int secondChar = 0; secondChar < CHARS.length; secondChar++) {
                for (int thirdChar = 0; thirdChar < CHARS.length; thirdChar++) {
                    for (int digits = 0; digits < number.size(); digits++) {
                        blatNumber.add(CHARS[firstChar] + number.get(digits) + CHARS[secondChar] + CHARS[thirdChar]);
                    }
                }
            }
        }
    }

    public static void generateFullNumber() {
        for (int i = 0; i < REGION.length; i++) { //добавляем регион
            for (int j = 0; j < blatNumber.size(); j++) {
                blatNumberRegion.add(blatNumber.get(j) + REGION[i]);
            }
        }
    }

    public static void сountingTimeArray(String checkNumber, ArrayList arrayList, Algorithm algorithm) {
        long startMC = System.currentTimeMillis();
        long startHC = System.nanoTime();
        if (algorithm == Algorithm.BYLINE) {
            int index = Collections.binarySearch(blatNumber, checkNumber);
            long timeInMillis = System.currentTimeMillis() - startMC;
            long timeInNanos = System.nanoTime() - startHC;
            System.out.printf("%s время поиска составило: %d мс\nили по НаноТайм - %d нс\n", (index >= 0),
                    timeInMillis, timeInNanos);
        } else {
            boolean isFound = false;
            for (int j = 0; j < arrayList.size(); j++) {
                if (checkNumber.equals(arrayList.get(j))) {
                    long timeInMillis = System.currentTimeMillis() - startMC;
                    long timeInNanos = System.nanoTime() - startHC;
                    isFound = true;
                    System.out.printf("%s (время поиска составило: %d мс\nили по НаноТайм - %d нс\n", isFound,
                            timeInMillis, timeInNanos);
                    break;
                }
            }
            if (!isFound) {
                long timeInMillis = System.currentTimeMillis() - startMC;
                long timeInNanos = System.nanoTime() - startHC;
                System.out.printf("%s (время поиска составило: %d мс\nили по НаноТайм - %d нс\n", (!isFound),
                        timeInMillis, timeInNanos);
            }
        }
    }

    public static void сountingTimeSet(String checkNumber, Set set) {
        long startMC = System.currentTimeMillis();
        long startHC = System.nanoTime();
        boolean isFound = set.contains(checkNumber);
        long timeInMillis = System.currentTimeMillis() - startMC;
        long timeInNanos = System.nanoTime() - startHC;
        System.out.printf("%s (время поиска составило: %d мс\nили по НаноТайм - %d нс\n", (isFound),
                timeInMillis, timeInNanos);
    }
}
