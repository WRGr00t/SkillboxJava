import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Loader {
    public static void main(String[] args) throws IOException {
        String fileName = "src/employee.csv";
        ArrayList<String> employeesList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis,
                     StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {

                for (String e : nextLine) {
                    employeesList.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<Employee> employees = parseCSV(employeesList);
        StringBuilder sb = new StringBuilder();
        sb.append("Фамилия;Имя;Отчество;дата рождения;ИНН;пенсионное свидетельство;Страна;Область;город;район;улица;дом ;квартира;должность;награды (через запятую);Образование;Учебное заведение;специальность;квалификация; год окончания;категория повышения квалификации;учреждение;номер;дата;дата окончания;организация профессиональной переподготовки;год;специальность\n");
        for (Employee employee : employees) {
            sb.append(employee.employeesToImport());
        }

            try (PrintWriter writer = new PrintWriter(new File("employeeForImport.csv"))) {

                writer.write(sb.toString());
                System.out.println("done!");

            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
    }

    private static ArrayList<Employee> parseCSV(ArrayList<String> employees) {
        ArrayList<Employee> workers = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (int i = 0; i < employees.size(); i++) {
            String fragments[] = employees.get(i).split(";");
            int INDEX_FIO = 1;
            String name = "";
            String surename = "";
            String middleName = "";
            try {
                String fio[] = fragments[INDEX_FIO].split(" ");
                name = fio[1];
                surename = fio[0];
                middleName = fio[2];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(employees.get(i));
            }
            int INDEX_DATE = 2;
            StringBuffer dateForParse = new StringBuffer(fragments[INDEX_DATE]);
            String data = String.valueOf(dateForParse).trim();
            int INDEX_POSITION = 3;
            StringBuffer positionForParse = new StringBuffer(fragments[INDEX_POSITION]);
            String position = String.valueOf(positionForParse).trim();
            workers.add(new Employee(surename, name, middleName, LocalDate.parse(data, formatter), position));
        }
        return workers;
    }
}
