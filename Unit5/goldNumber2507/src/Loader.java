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

    public static void main(String[] args) {

//===============генерация номеров
        Add00X(); //"000", "001", "002", "003", "004", "005", "006", "007", "008", "009" - номера до 10
        AddX00(); //"100", "200", "300", "400", "500", "600", "700", "800", "900" - номера, кратные 100
        AddGoldNumber(); // "111", "222", "333", "444", "555", "666", "777", "888", "999" - "золотые" номера
        AddMirrorNumber();// "010".."989" - зеркальные номера
        AddHillNumber(); // "012", "123" .. "987" - "горки"
        GenerateGoldNumber(); // формируем номера без региона
        GenerateFullNumber(); // формируем окончательный вид номера
        Array2Hash(); //копируем массив в HashSet
        Array2Tree(); //копируем массив в TreeSet


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
        System.out.println('\n' + "Количество номеров в базе - " + blatNumberRegion.size());
        System.out.println("Введите интересующий номер в формате А123ВС45 (кириллица) (для выхода напишите EXIT):");
        for (; ; ) {
            Scanner num = new Scanner(System.in);
            String checkNumber = num.next();
            if (checkNumber.toLowerCase().equals("exit")) {
                break;
            }
            long start = System.currentTimeMillis();
            long startTimeLine = System.nanoTime();
            System.out.println("Линейный поиск...");
            СountingTimeArray(start, startTimeLine, checkNumber, blatNumberRegion, false);

            System.out.println("Бинарный поиск...");
            Collections.sort(blatNumberRegion);
            start = System.currentTimeMillis();
            long startTimeBin = System.nanoTime();
            СountingTimeArray(start, startTimeBin, checkNumber, blatNumberRegion, true);

            System.out.println("Поиск по HashSet...");
            start = System.currentTimeMillis();
            long startTimeHash = System.nanoTime();
            СountingTimeSet(start, startTimeHash, checkNumber, numberInHash);


            System.out.println("Поиск по TreeSet...");
            start = System.currentTimeMillis();
            long startTimeTree = System.nanoTime();
            СountingTimeSet(start, startTimeTree, checkNumber, numberInTree);
        }
        // System.out.println('\n' + "Количество номеров - " + blatNumberRegion.size());
    }
    public static void Add00X(){
        for (int digit = 0; digit < 10; digit++) {
            number.add("00" + digit); //"00n"
        }
    }
    public static void AddX00(){
        for (int digit = 1; digit < 10; digit++) {
            number.add(digit + "00"); //"n00"
        }
    }
    public static void AddGoldNumber(){ //"XXX"
        for (int digit = 1; digit < 10; digit++) {
            number.add("" + digit + digit + digit);
        }
    }
    public static void AddMirrorNumber(){ //"XYX"
        for (int digitFirstLast = 0; digitFirstLast < 10; digitFirstLast++){
            for (int digitAverage = 0; digitAverage < 10; digitAverage++){
                if (digitAverage != digitFirstLast){
                    number.add("" + digitFirstLast + digitAverage + digitFirstLast);
                }
            }
        }
    }
    public static void AddHillNumber(){
        for (int startDigit = 0; startDigit < 9; startDigit++){
            number.add("" + startDigit + (startDigit + 1) + (startDigit + 2)); //"012"
            number.add("" + (startDigit + 2) + (startDigit + 1) + startDigit); //"987"
        }
    }
    public static void GenerateGoldNumber(){
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
    public static void GenerateFullNumber(){
        for (int i = 0; i < REGION.length; i++){ //добавляем регион
            for (int j = 0; j < blatNumber.size(); j++){
                blatNumberRegion.add(blatNumber.get(j) + REGION[i]);
            }
        }
    }
    public static void Array2Hash(){
        for (String transfer : blatNumberRegion) { //перенос массива в HashSet
            numberInHash.add(transfer);
        }
    }
    public static void Array2Tree(){
        for (String transfer : blatNumberRegion) { //перенос массива в TreeSet
            numberInTree.add(transfer);
        }
    }
    public static void СountingTimeArray(long startMC, long startHC, String checkNumber, ArrayList arrayList, Boolean isBinary){
        if (isBinary) {
            int index = Collections.binarySearch(blatNumberRegion, checkNumber);
            if (index >= 0) {
                System.out.println("true (" + (System.currentTimeMillis() - startMC) + " мс)");
                System.out.println("Время по НаноТайм - " + (System.nanoTime() - startHC) + " нс");
            } else {
                System.out.println("false (" + (System.currentTimeMillis() - startMC) + " мс)");
                System.out.println("Время по НаноТайм - " + (System.nanoTime() - startHC) + " нс");
            }
        } else {
            boolean found = false;
            for (int j = 0; j < arrayList.size(); j++) {
                if (checkNumber.equals(arrayList.get(j))) {
                    System.out.println("true (" + (System.currentTimeMillis() - startMC) + " мс)");
                    System.out.println("Время по НаноТайм - " + (System.nanoTime() - startHC) + " нс");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("false (" + (System.currentTimeMillis() - startMC) + " мс)");
                System.out.println("Время по НаноТайм - " + (System.nanoTime() - startHC) + " нс");
            }
        }
    }
    public static void СountingTimeSet(long startMC, long startHC, String checkNumber, Set set){
        if (set.contains(checkNumber)) {
            System.out.println("true (" + (System.currentTimeMillis() - startMC) + " мс)");
            System.out.println("Время по НаноТайм - " + (System.nanoTime() - startHC) + " нс");
        } else {
            System.out.println("false (" + (System.currentTimeMillis() - startMC) + " мс)");
            System.out.println("Время по НаноТайм - " + (System.nanoTime() - startHC) + " нс");
        }
    }

}
