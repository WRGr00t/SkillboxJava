import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

    public static ArrayList<String> toDoList = new ArrayList<>();

    public static void main(String[] args) {

        Alias commandName = Alias.ERROR;
        for (; ; ) {
            System.out.println("Введите команды управления:" +
                    "\n LIST - вывести список " +
                    "\n ADD n дело - добавить дело на n-ое место (без указания номера дело помещается в конец списка" +
                    "\n EDIT n дело - заменить дело на месте n " +
                    "\n DELETE n - удалить дело с номером n, без указания номера будут удалены все дела" +
                    "\n EXIT(закрытие программы)");
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine().trim();
            String[] substr = command.split(" ", 3);
            if(getCommand(command) == Alias.ERROR){
                System.out.println("Неверный формат команды, повторите ввод");
            }
            if (getCommand(command) == Alias.ADD) {
                addWork(toDoList.size(), "Дело без названия");
                System.out.println("Дело без названия добавлено в конец списка");
            }
            if (getCommand(command) == Alias.LIST) {
                if (toDoList.size() != 0){
                    System.out.println("Запланировано: " + toDoList.size() + " дел/о/а:");
                    for (int i = 0; i < toDoList.size(); i++) {
                        System.out.println((i+1) + ": " + toDoList.get(i));
                    }
                } else {
                    System.out.println("Список дел пуст");
                }
            }
            if (getCommand(command) == Alias.ADDN) {
                String workName = "Дело без названия";
                int workNo = toDoList.size(); //если не найдет номер, добавит в конец
                if (substr.length > 1){
                    if (substr.length > 2) {
                        workName = substr[2];
                    }
                    workNo = Integer.parseInt(substr[1]);
                }
                if (workNo > toDoList.size()) {
                    workNo = toDoList.size();
                }
                addWork(workNo, workName);
                System.out.println("Дело " + workName + " добавлено на " + (workNo + 1) + " строку");
            }
            if (getCommand(command) == Alias.EDIT) {
                int workNo = 0;
                String workName = "Дело без названия";
                if (substr.length > 2){
                    workNo = Integer.parseInt(substr[1]) - 1;
                    workName = substr[2];
                }
                if (workNo < toDoList.size()){
                    toDoList.remove(workNo);
                    addWork(workNo, workName);
                    System.out.println("Дело изменено на " + (workNo + 1) + " строке");
                } else {
                    System.out.println("Внести изменения не удалось");
                }

            }

            if (getCommand(command) == Alias.DELETE) {
                toDoList.clear();
                System.out.println("Список очищен");
            }

            if (getCommand(command) == Alias.DELETEN) {
                int workNo = Integer.parseInt(substr[1]) - 1;
                toDoList.remove(workNo);
                System.out.println("Дело на " + (workNo + 1) + " строке успешно удалено");
            }

            if (getCommand(command) == Alias.EXIT) {
                break;
            }
        }
    }

    private static void addWork(int number, String workName) {
        int recNo = number;
        if (toDoList.size()+1 == number) {
            recNo = number;
            number = toDoList.size();
        }
            toDoList.add(number, workName);
            //System.out.println("Дело добавлено в список под номером " + (recNo + 1));

    }

    private static Alias getCommand (String text){
        String[] substr = text.split(" ", 3);

 /*       for (String test : text.split(" ", 3)) { //тестировка разбиения
            System.out.println(test);
        }*/
        switch (substr[0].toLowerCase()) {
            case "add": {
                if (substr.length > 1){
                    if (isDigit(substr[1])){
                        return Alias.ADDN;
                    }
                }
                else {
                    return Alias.ADD;
                }
            }
            case "list": {
                return Alias.LIST;
            }
            case "edit": {
                return Alias.EDIT;
            }
            case "delete": {
                if (substr.length > 1){
                    if (isDigit(substr[1])) {
                        return Alias.DELETEN;
                    }
                } else {
                    return Alias.DELETE;
                }
            }
            case "exit": {
                return Alias.EXIT;
            }
            default: {
                return Alias.ERROR;
            }
        }
    }

    private static boolean isDigit(String string) throws NumberFormatException {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

