
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Company {

    private static final int profitLevel = 10000000;
    private static ArrayList<Employee> staff = new ArrayList<>();

    public static int calculationProfit() {
        int profit = 0;
        for (int i = 0; i < staff.size(); i++) {
            profit = profit + (int) staff.get(i).getShareOfProfit();
        }
        return profit;
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

    public static void hireStaff(Employee employee) {
        staff.add(employee);
        Collections.sort(staff, Comparator.comparingInt(Employee::getMonthSalary));
        calculationProfit();
    }

    public ArrayList<Employee> getTopSalaryStaff(int count) {
        if (count < staff.size()) {
            return (ArrayList<Employee>) staff
                    .stream()
                    .sorted(Comparator.comparing(Employee::getMonthSalary).reversed())
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            return staff;
        }
    }
        /* =====старый алгоритм======
            ArrayList<Employee> resultList = new ArrayList<>();
            for (int i = (staff.size() - count); i < staff.size(); i++) {
                resultList.add(staff.get(i));
            }
            return resultList;
        }
        ArrayList<Employee> resultList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                resultList.add(staff.get(i));
            }
            return resultList;
        */


    public ArrayList<Employee> getLowestSalaryStaff(int count) {
        if (count < staff.size()) {
            return (ArrayList<Employee>) staff
                    .stream()
                    .sorted(Comparator.comparing(Employee::getMonthSalary))
                    .limit(count)
                    .collect(Collectors.toList());
        } else {
            return staff;
        }
    }

    public static int getProfit() {
        return calculationProfit();
    }

    public static Employee getEmployee(int recNo) {
        if (recNo > 0 && recNo < staff.size()) {
            return staff.get(recNo);
        } else {
            return null;
        }
    }

    public static void printStaff(){
        for (Employee employee : staff){
            System.out.println(employee.toString());
        }
    }

}