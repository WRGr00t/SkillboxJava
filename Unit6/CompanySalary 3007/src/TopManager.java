public class TopManager extends Employee {
    private final int RANGE_TO_HIGH = 30000;
    private final int MIN_TOP_SALARY = 50000;
    private final double shareOfProfit = 0.0;
    private int monthSalary;
    private int fixPartSalary;

    TopManager() {
        fixPartSalary = MIN_TOP_SALARY + (int) Math.round(Math.random() * RANGE_TO_HIGH);
    }

    TopManager(int fixPartSalary) {
        this.fixPartSalary = fixPartSalary;
    }

    @Override
    public int getMonthSalary() {
        if (Company.getProfit() >= Company.getProfitLevel()) {
            monthSalary = fixPartSalary + RANGE_TO_HIGH;
        } else {
            monthSalary = fixPartSalary;
        }
        return monthSalary;
    }

    @Override
    public double getShareOfProfit() {
        return shareOfProfit;
    }

    public void printTopManager() {
        System.out.printf("Топ-менеджер с месячной заплатой %d%n", getMonthSalary());
    }

    public String toString() {
        return "Топ-менеждер с зарплатой " + getMonthSalary();
    }
}
