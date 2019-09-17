import java.util.ArrayList;

public class Loader {
    public static void main(String[] args) {
        SalesManager salesManager = new SalesManager();
        salesManager.printManager();
        TopManager topManager = new TopManager();
        topManager.printTopManager();
        Operator operator = new Operator(15000);
        operator.printOperator();
        Company company = new Company();
        company.hireStaff(270);
        upDateData(company);

        cuteStaff();
        upDateData(company);

        company.hireStaff(25);
        upDateData(company);

        cuteStaff(250);
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
            int recNoForCut = (int) ((Company.getCompanySize() / 2) * (Math.random() * Math.random()));
            Company.cuteStaff(recNoForCut);
        }
    }

    public static void upDateData(Company company) {
        int COUNT = 5; //количество сотрудников на конкурс "Самый"
        System.out.println("Штат компании составляет - " + Company.getCompanySize());
        System.out.println("Прибыль компании составила - " + Company.getProfit());
        ArrayList<Employee> lowestList = company.getLowestSalaryStaff(COUNT);
        System.out.println("Список сотрудников с самой низкой зарплатой:");
        for (Employee staff : lowestList) {
            System.out.println(staff.toString());
        }
        ArrayList<Employee> topList = company.getTopSalaryStaff(COUNT);
        System.out.println("Список сотрудников с самой высокой зарплатой:");
        for (Employee staff : topList) {
            System.out.println(staff.toString());
        }
    }
}
