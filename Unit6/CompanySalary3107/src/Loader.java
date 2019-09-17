import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Loader {
    public static void main(String[] args) {
        SalesManager salesManager = new SalesManager();
        salesManager.printManager();
        TopManager topManager = new TopManager();
        topManager.printTopManager();
        Operator operator = new Operator(15000);
        operator.printOperator();
        Company company = new Company();
        hireStaff(270);
        upDateData(company);

        cuteStaff();
        upDateData(company);

        hireStaff(25);
        upDateData(company);

        cuteStaff(250);
        upDateData(company);

        cuteStaff(37);
        upDateData(company);

    }

    public static void cuteStaff() {
        double RATIO_CUTS = 0.05;
        int amountCut = (int) (Company.getCompanySize() * RATIO_CUTS);
        cuteStaff(amountCut);
    }

    public static void cuteStaff(int amountCut) {
        System.out.printf("Будут уволены %d человек%n", amountCut); //можно сделать счетчик удачных увольнений и удалить ровно нужное количество
        for (int i = 0; i < amountCut; i++) {
            int recNoForCut = (int) (Company.getCompanySize() * Math.random());
            Company.cuteStaff(recNoForCut);
        }
    }

    public static void upDateData(Company company) {
        int COUNT = 5; //количество сотрудников на конкурс "Самый"
        System.out.println("Штат компании составляет - " + Company.getCompanySize());
        System.out.println("Прибыль компании составила - " + Company.getProfit());
        ArrayList<Employee> lowestList = company.getLowestSalaryStaff(COUNT);
        System.out.println("Список сотрудников с самой низкой зарплатой:");
        if (Company.getCompanySize() > COUNT){
            for (Employee staff : lowestList) {
                System.out.println(staff.toString());
            }
            ArrayList<Employee> topList = company.getTopSalaryStaff(COUNT);
            System.out.println("Список сотрудников с самой высокой зарплатой:");
            for (Employee staff : topList) {
                System.out.println(staff.toString());
            }
        } else {
            System.out.println("Штат компании меньше запрошенного числа, вывожу всех:");
            Company.printStaff();
        }
    }
    public static void hireStaff(int amount) {
        int RATIO_SM = 35;
        int RATIO_TM = 5;
        int salesManagerAmount = 0;
        int topManagerAmount = 0;
        int operatorAmount = 0;
        salesManagerAmount = amount * RATIO_SM / 100;
        topManagerAmount = amount * RATIO_TM / 100;
        operatorAmount = amount - (topManagerAmount + salesManagerAmount);
        System.out.println(salesManagerAmount + " продажников\n" + topManagerAmount + " топчиков\n" + operatorAmount + " операторов");
        for (int i = 0; i < salesManagerAmount; i++) {
            Company.hireStaff(new SalesManager());
        }
        for (int i = 0; i < topManagerAmount; i++) {
            Company.hireStaff(new TopManager());
        }
        for (int i = 0; i < operatorAmount; i++) {
            Company.hireStaff(new Operator());
        }
    }
    private void hireStaff() {
        int amount = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество сотрудников в компании");
        amount = scanner.nextInt();
        hireStaff(amount);
    }
}
