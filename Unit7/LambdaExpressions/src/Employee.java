import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Employee implements Comparable<Employee> {
    private String name;
    private Integer salary;
    private LocalDate workStart;

    public Employee(String name, Integer salary, LocalDate workStart) {
        this.name = name;
        this.salary = salary;
        this.workStart = workStart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public LocalDate getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalDate workStart) {
        this.workStart = workStart;
    }

    public String toString() {
        return name + " - " + salary + " - " +
                (workStart);
    }

    @Override
    public int compareTo(Employee o) {
        if (this.getWorkStart().isBefore(o.getWorkStart())) {
            return 1;
        } else if (this.getWorkStart().isAfter(o.getWorkStart())) {
            return -1;
        } else {
            return 0;
        }
    }
}
