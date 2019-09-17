import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Company {
    private final int RATIO_SM = 35;
    private final int RATIO_TM = 5;

    private static final int profitLevel = 10000000;
    private static int profit = 0;
    private static ArrayList<Employee> staff = new ArrayList<>();

    private void hireStaff() {
        int amount = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество сотрудников в компании");
        amount = scanner.nextInt();
        hireStaff(amount);
    }

    public static void calculationProfit() {
        profit = 0;
        for (int i = 0; i < staff.size(); i++) {
            profit = profit + (int) staff.get(i).getShareOfProfit();
        }
    }

    public static int getCompanySize() {
        return staff.size();
    }

    public static int getProfitLevel() {
        return profitLevel;
    }

    public static void cuteStaff(int recNo) {
        Employee employee = getEmployee(recNo);
        if (employee != null) {
            System.out.println("Уволен сотрудник №" + recNo + " " + employee.toString());
            staff.remove(recNo);
        } else {
            System.out.println("Ошибка увольнения...");
        }
    }

    public void hireStaff(int amount) {
        int salesManagerAmount = 0;
        int topManagerAmount = 0;
        int operatorAmount = 0;
        salesManagerAmount = amount * RATIO_SM / 100;
        topManagerAmount = amount * RATIO_TM / 100;
        operatorAmount = amount - (topManagerAmount + salesManagerAmount);
        System.out.println(salesManagerAmount + " продажников\n" + topManagerAmount + " топчиков\n" + operatorAmount + " операторов");
        for (int i = 0; i < salesManagerAmount; i++) {
            staff.add(new SalesManager());
        }
        for (int i = 0; i < topManagerAmount; i++) {
            staff.add(new TopManager());
        }
        for (int i = 0; i < operatorAmount; i++) {
            staff.add(new Operator());
        }
        Collections.sort(staff, Comparator.comparingInt(Employee::getMonthSalary));
        calculationProfit();
    }

    public ArrayList<Employee> getTopSalaryStaff(int count) {
        if (count <= staff.size()) {
            ArrayList<Employee> resultList = new ArrayList<>();
            for (int i = (staff.size() - count); i < staff.size(); i++) {
                resultList.add(staff.get(i));
            }
            return resultList;
        } else {
            return null;
        }
    }

    public ArrayList<Employee> getLowestSalaryStaff(int count) {
        if (count <= staff.size()) {
            ArrayList<Employee> resultList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                resultList.add(staff.get(i));
            }
            return resultList;
        } else {
            return null;
        }
    }

    public static int getProfit() {
        calculationProfit();
        return profit;
    }

    public static Employee getEmployee(int recNo) {
        if (recNo > 0 && recNo < staff.size()) {
            return staff.get(recNo);
        } else {
            return null;
        }
    }

}