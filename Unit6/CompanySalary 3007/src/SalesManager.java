import java.util.Comparator;

public class SalesManager extends Employee {
    private double MIN_PROFIT = 85000.0;
    private double ADD_PEAK_PROFIT = 45000.0;
    private int MIN_SALARY = 10000;
    private int RANGE_TO_HIGH = 50000;
    private int PERCENTAGE_OF_SALES = 5;
    private int monthSalary;
    private int fixPartOfSalary;

    private double shareOfProfit;

    public SalesManager(int fixPartOfSalary) {
        this.fixPartOfSalary = fixPartOfSalary;
        generateProfit();
    }

    public SalesManager() {
        fixPartOfSalary = MIN_SALARY + (int) Math.round(Math.random() * RANGE_TO_HIGH);
        generateProfit();
    }

    public SalesManager(int fixPartOfSalary, double shareOfProfit) {
        this(fixPartOfSalary);
        this.shareOfProfit = shareOfProfit;
    }

    @Override
    public int getMonthSalary() {
        monthSalary = fixPartOfSalary + (int) (shareOfProfit * PERCENTAGE_OF_SALES / 100);
        return monthSalary;
    }

    public void setMonthSalary(int monthSalary) {
        this.monthSalary = monthSalary;
    }

    public int getFixPartOfSalary() {
        return fixPartOfSalary;
    }

    public void setFixPartOfSalary(int fixPartOfSalary) {
        this.fixPartOfSalary = fixPartOfSalary;
    }

    public void printManager() {
        System.out.printf("Менеджер по продажам с месячной заплатой %d и долей в прибыли - %.02f%n",
                getMonthSalary(), shareOfProfit);
    }

    public String toString() {
        return "Менеджер с зарплатой " + getMonthSalary();
    }

    private void generateProfit() {
        this.shareOfProfit = MIN_PROFIT + Math.round(Math.random() * ADD_PEAK_PROFIT);
    }

    @Override
    public double getShareOfProfit() {
        return shareOfProfit;
    }
}
