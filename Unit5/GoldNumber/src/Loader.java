import java.util.*;

public class Loader {
    public static void main(String[] args) {
        String chars[] = new String[]{"А", "В", "Е", "К", "М", "Н", "О", "Р", "С", "Т", "У", "Х"}; //допустимые буквы

//========== Регионы не берем в интересах экономии ресурсов
      /*  String region[] = new String[]{"01", "02", "102", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "113", "14", "15", "16", "116", "716", "17", "18", "19", "21", "121", "22", "23", "93", "123",
                "24", "84", "88", "124", "25", "125",
                "26", "126", "27", "28", "29", "30", "31", "32", "33", "34", "134",
                "35", "36", "136", "37", "38", "85", "138", "39", "91", "40", "41", "42", "142", "43", "44", "45", "46",
                "47", "147", "48", "49", "50", "90", "150", "190", "750", "51", "52", "152", "53", "54", "154", "55",
                "56", "57", "58", "59", "81", "159", "60", "61", "161", "62", "63", "163", "763", "64", "164", "65",
                "66", "96", "67", "68", "69", "70", "71", "72", "73", "173", "74", "174", "75", "80", "76", "77",
                "97", "99", "177", "197", "199", "777", "799", "78", "98", "178", "198", "79", "82", "83", "86", "186",
                "87", "89", "92", "94", "20", "95", "196"}; //номера регионов*/
//===============генерация номеров
        //"000", "001", "002", "003", "004", "005", "006", "007", "008", "009" - номера до 10
        //"100", "200", "300", "400", "500", "600", "700", "800", "900" - номера, кратные 100
        // "111", "222", "333", "444", "555", "666", "777", "888", "999" - "золотые" номера
        // "010".."989" - зеркальные номера
        // "012", "123" .. "987" - "горки"
        ArrayList<String> number = new ArrayList<>();
        for (int digit = 0; digit < 10; digit++) {
            number.add("00" + digit); //"00n"
            if (digit > 0) {
                number.add(digit + "00"); //"n00"
                for (int j = 0; j <= 9; j++) {
                    number.add(String.valueOf(j) + digit + String.valueOf(j)); //"зеркальные номера"
                }
            }
            if (digit < 8) {
                number.add(String.valueOf(digit) + String.valueOf(digit + 1) + String.valueOf(digit + 2)); //"горка вверх"
                number.add(String.valueOf(digit + 2) + String.valueOf(digit + 1) + String.valueOf(digit)); // "горка вниз"
            }
        }
 /* //================ вывод номеров на печать
        for (int i = 0; i < number.size(); i++) {
            System.out.print(number.get(i) + " ");
            if (i % 20 == 0) {
                System.out.println('\n');
            }
        }
*/
        ArrayList<String> blatNumber = new ArrayList<>();
        for (int firstChar = 0; firstChar < chars.length; firstChar++) { //индекс первой буквы госномера
            for (int secondChar = 0; secondChar < chars.length; secondChar++) { //вторая буква госномера
                for (int thirdChar = 0; thirdChar < chars.length; thirdChar++) { //третья буква госномера
                    for (int digits = 0; digits < number.size(); digits++) {
                        blatNumber.add(chars[firstChar] + number.get(digits) + chars[secondChar] + chars[thirdChar]);
                    }
                }
            }
        }
/*//============ Алгоритм с регионами
        ArrayList<String> blatNumberRegion = new ArrayList<>();
        for (int i = 0; i < region.length; i++){ //добавляем регион
            for (int j = 0; j < blatNumber.size(); j++){
                blatNumberRegion.add(blatNumber.get(j) + region[i]);
            }
        }

       for (int i = 0; i < blatNumberRegion.size(); i++) {
            System.out.print(blatNumberRegion.get(i) + " ");
            if (i % 20 == 0) {
                System.out.println('\n');
            }
        }*/
        System.out.println('\n' + "Количество номеров в базе - " + blatNumber.size());
        System.out.println("Введите интересующий номер в формате А123ВС (кириллица) (для выхода напишите EXIT):");
        for (; ; ) {
            Scanner num = new Scanner(System.in);
            String checkNumber = num.next();
            if (checkNumber.toLowerCase().equals("exit")) {
                break;
            }
            long start = System.currentTimeMillis();
            System.out.println("Линейный поиск...");
            boolean found = false;
            for (int j = 0; j < blatNumber.size(); j++) {
                if (checkNumber.equals(blatNumber.get(j))) {
                    System.out.println("true (" + (System.currentTimeMillis() - start) + " мс)");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("false (" + (System.currentTimeMillis() - start) + " мс)");
            }
            System.out.println("Бинарный поиск...");
            start = System.currentTimeMillis();
            Collections.sort(blatNumber);
            int index = Collections.binarySearch(blatNumber, checkNumber);
            if (index >= 0) {
                System.out.println("true (" + (System.currentTimeMillis() - start) + " мс)");
            } else {
                System.out.println("false (" + (System.currentTimeMillis() - start) + " мс)");
            }

            System.out.println("Поиск по HashSet...");
            HashSet<String> numberInHash = new HashSet<>();
            for (String transfer : blatNumber) { //перенос массива в HashSet
                numberInHash.add(transfer);
            }
            start = System.currentTimeMillis();
            if (numberInHash.contains(checkNumber)) {
                System.out.println("true (" + (System.currentTimeMillis() - start) + " мс)");
            } else {
                System.out.println("false (" + (System.currentTimeMillis() - start) + " мс)");
            }

            System.out.println("Поиск по TreeSet...");
            TreeSet<String> numberInTree = new TreeSet<>();
            for (String transfer : blatNumber) { //перенос массива в TreeSet
                numberInTree.add(transfer);
            }
            start = System.currentTimeMillis();
            if (numberInTree.contains(checkNumber)) {
                System.out.println("true (" + (System.currentTimeMillis() - start) + " мс)");
            } else {
                System.out.println("false (" + (System.currentTimeMillis() - start) + " мс)");
            }
        }
       // System.out.println('\n' + "Количество номеров - " + blatNumberRegion.size());
    }
}
