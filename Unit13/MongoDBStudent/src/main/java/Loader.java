import com.mongodb.DBCursor;

import java.io.IOException;
import java.util.ArrayList;

public class Loader {
    public static void main(String[] args) throws IOException {
        MongoService service = MongoService.get().connectDb("test");
        String path = "src/main/java/mongo.csv";
        service.createCollection("students", path);
        System.out.println("Первая задача — общее количество студентов в базе.\n");

        System.out.printf("The size of the student base is - %d records\n", service.getCount("students"));

        System.out.println("==================================================");
        System.out.println("Вторая задача — список/количество студентов старше 40 лет.");

        int age = 40;
        DBCursor result = service.filterByAgeGT("students", age);
        ArrayList<Student> students = service.getStudentsFromCursor(result);
        for (Student student : students) {
            System.out.println(student.toSting());
        }

        System.out.printf("Условию соответствуют %d чел.\n", students.size());


        System.out.println("==================================================");
        System.out.println("Третья задача — имя самого молодого студента.");

        result = service.showExtreme("students", "age", Direction.DIRECT_DOWN);
        students = service.getStudentsFromCursor(result);
        for (Student student : students) {
            System.out.println(student.getName());
        }

        System.out.println("==================================================");
        System.out.println("Четвертая задача — список курсов самого старого студента.");

        result = service.showExtreme("students", "age", Direction.DIRECT_UP);
        students = service.getStudentsFromCursor(result);
        for (Student student : students) {
            System.out.println(student.getCourses());
        }
    }
}
