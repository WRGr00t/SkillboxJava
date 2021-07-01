
import java.util.*;

import static java.lang.Integer.parseInt;

public class Loader {
    public static void main(String[] args) {
        String dbName = "test";

        String nextCommand = "Введите команду (\"" + UserCommand.HELP + "\" - справка по формату команд): ";
        String errorString = "Неверный формат команды";
        String addShopCommandDescription = "Добавить магазин - " + UserCommand.ADD_SHOP + " [имя магазина]\n" +
                "(команда без имени магазина автоматически сгененирует магазины из csv-файла)";
        String addItemCommandDescription = "Добавить товар - " + UserCommand.ADD_ITEM + " [наименование] [цена]\n" +
                "(команда без параметров автоматически сгененирует товары из csv-файла)";
        String addSubmitItemCommandDescription = "Добавить товар в магазин - " + UserCommand.SUBMIT_ITEM + " [товар] [магазин]";
        String getAllShopsDescription = "Вывести все магазины - " + UserCommand.PRINT_SHOPS;
        String getAllItemsDescription = "Вывести все товары с ценами и описанием - " + UserCommand.PRINT_ITEMS;
        String getStatisticDescription = "Вывести статистику по коллекциям - " + UserCommand.PRINT_STAT;
        String quitDescription = "Для выхода введите команду - " + UserCommand.QUIT;
        MongoStorage storage = MongoStorage.getStorage().connectDb(dbName);
        MongoService service = new MongoService(storage);

        boolean exit = false;
        //Scanner in = new Scanner(System.in);
        MyScanner in = new MyScanner(System.in);
        while (!exit) {
            System.out.print(nextCommand);
            String command = in.nextLine().trim();
            if (command.equals(UserCommand.HELP)) {
                System.out.println(addShopCommandDescription);
                System.out.println(addItemCommandDescription);
                System.out.println(addSubmitItemCommandDescription);
                System.out.println(getAllShopsDescription);
                System.out.println(getAllItemsDescription);
                System.out.println(getStatisticDescription);
                System.out.println(quitDescription);

            } else if (command.toUpperCase().indexOf(UserCommand.ADD_SHOP.toString()) == 0) {
                if (command.toUpperCase().equalsIgnoreCase(UserCommand.ADD_SHOP.toString())){
                    service.addAllShops();
                } else {
                    String shopName = parseNameFromCommand(command);
                    service.addShop(storage, shopName);
                }
            } else if (command.toUpperCase().indexOf(UserCommand.ADD_ITEM.toString()) == 0) {
                if (command.toUpperCase().equalsIgnoreCase(UserCommand.ADD_ITEM.toString())) {
                    service.addAllItems();
                } else {
                    ArrayList<String> partCommand = parseCommand(command);
                    if (partCommand != null){
                        int NAME_POSITION = 0;
                        int PRICE_POSITION = 1;
                        String itemName = partCommand.get(NAME_POSITION);
                        int price = Integer.parseInt(partCommand.get(PRICE_POSITION));
                        service.addItem(storage, itemName, price);
                    }
                }
            } else if (command.toUpperCase().indexOf(UserCommand.SUBMIT_ITEM.toString()) == 0) {
                ArrayList<String> partCommand = parseCommand(command);
                if (partCommand != null) {
                    int ITEM_POSITION = 0;
                    int SHOP_POSITION = 1;
                    String itemName = partCommand.get(ITEM_POSITION);
                    String shopName = partCommand.get(SHOP_POSITION);
                    service.addItemToShop(itemName, shopName);
                }
            } else if (command.toUpperCase().indexOf(UserCommand.PRINT_SHOPS.toString()) == 0) {
                service.printFromCursorAll("shops");
            } else if (command.toUpperCase().indexOf(UserCommand.PRINT_ITEMS.toString()) == 0) {
                service.printFromCursorAll("items");
            } else if (command.toUpperCase().indexOf(UserCommand.QUIT.toString()) == 0) {
                exit = true;
            } else if (command.toUpperCase().indexOf(UserCommand.PRINT_STAT.toString()) == 0) {
                service.printStat();
            } else {
                System.out.println(errorString);
            }
        }
        //in.close();

        service.editNameItem("Птица", "Курица");

        service.printFromCursorAll("shops");
        service.printFromCursorAll("items");
    }

    private static ArrayList<String> parseCommand(String command) {
        ArrayList<String> result = new ArrayList<>();
        int CORRECT_WORDS_COUNT = 3;
        int POSITION_FIRST_PARAM = 1;
        int POSITION_SECOND_PARAM = 2;
        String[] strings = command.split(" ");
        if (strings.length == CORRECT_WORDS_COUNT) {
            result.add(strings[POSITION_FIRST_PARAM]);
            result.add(strings[POSITION_SECOND_PARAM]);
            return result;
        } else {
            return null;
        }
    }

    private static String parseNameFromCommand(String command) {
        int posName = 1;
        String[] strings = command.split(" ");
        return strings[posName];
    }

    static class MyScanner {

        private static final String ДОБАВИТЬ_МАГАЗИН = "ADD_SHOP";
        private static final String ДОБАВИТЬ_ТОВАР = "ADD_ITEM";
        private static final String ВЫСТАВИТЬ_ТОВАР = "SUBMIT_ITEM";
        private static final String СТАТИСТИКА_ТОВАРОВ = "PRINT_STAT";
        private static final String ВЫХОД = "QUIT";

        List<String> commands = new ArrayList<>() {{
            add(ДОБАВИТЬ_МАГАЗИН);
            add(ДОБАВИТЬ_ТОВАР);
            add(ДОБАВИТЬ_ТОВАР + " Молоко 200");
            add(ДОБАВИТЬ_ТОВАР + " Дешевое_Молоко 1");
            for (String shop : Arrays.asList("Лента", "Окей")) {
                add(ДОБАВИТЬ_МАГАЗИН + " " + shop);
                add(ВЫСТАВИТЬ_ТОВАР + " Дешевое_Молоко " + shop);
                add(ВЫСТАВИТЬ_ТОВАР + " Молоко " + shop);
            }
            add(СТАТИСТИКА_ТОВАРОВ);
            add(ВЫХОД);
        }};
        Iterator<String> it = commands.iterator();

        public MyScanner(Object in) {

        }

        public String nextLine() {
            if (it.hasNext()) {
                String next = it.next();
                System.out.println(" > " + next);

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                return next;
            }
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return it.next();
        }
    }

}
