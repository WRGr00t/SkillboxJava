public class Student {
    private String name;
    private int age;
    private String courses;

    public Student() {
    }

    public Student(String name, int age, String courses) {
        this.name = name;
        this.age = age;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String toSting() {
        String string = String.format("Student %s age: %d courses: %s",
                this.getName(),
                this.getAge(),
                this.getCourses());
        return string;
    }
}
