public class Employee {
    private int monthSalary;
    private double shareOfProfit;

    public int compareTo(Employee o) {
        if (this.monthSalary == o.getMonthSalary()) {
            return 0;
        } else if (this.monthSalary > o.getMonthSalary()) {
            return 1;
        } else {
            return -1;
        }
    }

    public int getMonthSalary() {
        return 0;
    }

    public double getShareOfProfit() {
        return shareOfProfit;
    }
}
