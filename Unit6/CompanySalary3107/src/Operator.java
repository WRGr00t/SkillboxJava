public class Operator extends Employee {
    private int monthSalary;
    private final int MIN_SALARY = 10000;
    private final int RANGE_TO_HIGH = 5000;
    private final double shareOfProfit = 0.0;

    Operator() {
        monthSalary = MIN_SALARY + (int) Math.round(RANGE_TO_HIGH * Math.random());
    }

    Operator(int monthSalary) {
        this.monthSalary = monthSalary;
    }

    @Override
    public int getMonthSalary() {
        return monthSalary;
    }

    @Override
    public double getShareOfProfit() {
        return shareOfProfit;
    }

    public void printOperator() {
        System.out.printf("Оператор с месячной заплатой %d%n", getMonthSalary());
    }

    public String toString() {
        return "Оператор с зарплатой " + getMonthSalary();
    }
}
