import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/skillbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String user = "root";
    static String password = "***";

    public static void main(String[] args) throws SQLException {

        try {
            Connection connection = createConnection();
            Statement statement = createStatement(connection);

            createNewPurchaselist(statement, "purchaselist2");
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Connection con = createConnection();
        PreparedStatement stat = con.prepareStatement("SELECT * FROM PURCHASELIST2");
        ResultSet result = stat.executeQuery();
        System.out.println(result.getRow());
        while (result.next()) {
            PurchaseId purchaseId = new PurchaseId(result.getInt(1), result.getInt(2));
            Student stud = session.get(Student.class, purchaseId.getStudentId());
            Course course = session.get(Course.class, purchaseId.getCourseId());
            System.out.println(stud.getName() + " подписан на " + course.getName() + " с " + result.getDate(3));
        }

        PurchaseId purchaseId = new PurchaseId(10, 10);
        Purchaselist2 purchaselist2 = new Purchaselist2(purchaseId);
        Course course = session.get(Course.class, purchaselist2.getId().getCourseId());
        System.out.println("Подписка на " + course.getName());
        Subscription subscription = new Subscription(purchaseId);
        Student stud = session.get(Student.class, subscription.getId().getStudentId());
        System.out.println(stud.getName());
        course = session.get(Course.class, subscription.getId().getStudentId());
        List<Student> students = course.getStudents();
        System.out.println("На курс " + course.getName() + " подписаны:");
        for (Student student : students) {
            System.out.println(student.getName());
        }
        transaction.commit();
        sessionFactory.close();
        try {
            Connection connection = createConnection();
            Statement statement = createStatement(connection);
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    private static void createNewPurchaselist(Statement statement, String table) throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS " + table);
        statement.executeUpdate("CREATE TABLE "+ table + "\n" +
                "    AS\n" +
                "    SELECT S.id \"student_id\", C.id \"course_id\", P.subscription_date\n" +
                "    FROM purchaselist P INNER JOIN students S\n" +
                "    ON P.student_name = S.name\n" +
                "    JOIN courses C\n" +
                "    ON P.course_name = C.name;");
        statement.executeUpdate("ALTER TABLE " + table + " ADD FOREIGN KEY (student_id) REFERENCES students(id)");
        statement.executeUpdate("ALTER TABLE " + table + " ADD FOREIGN KEY (course_id) REFERENCES courses(id)");
    }

    private static Statement createStatement(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement;
    }
}
