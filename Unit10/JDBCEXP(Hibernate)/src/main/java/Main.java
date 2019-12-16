import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.List;

public class Main {
    //============ Предварительные манипуляции ==============
    /*
    Создание буферной таблицы слиянием трех таблиц:
    CREATE TABLE purchaselist2
        AS
        SELECT P.student_name, S.id "student_id", P.course_name, C.id "course_id", P.subscription_date
        FROM purchaselist P INNER JOIN students S
        ON P.student_name = S.name
        JOIN courses C
        ON P.course_name = C.name;

    Добавление ID
        ALTER TABLE purchaselist2 ADD id int NOT NULL FIRST;
        ALTER TABLE purchaselist2 ADD INDEX (id);
        ALTER TABLE purchaselist2 CHANGE id id INT NOT NULL AUTO_INCREMENT;

    Переименование таблиц:

        ALTER TABLE purchaselist RENAME purchaselist_bak; //исходную в резерв
        ALTER TABLE purchaselist2 RENAME purchaselist; //новую в работу

    Связи:
        ALTER TABLE purchaselist ADD FOREIGN KEY (student_id) REFERENCES students(id);
        ALTER TABLE purchaselist ADD FOREIGN KEY (course_id) REFERENCES courses(id);

     */

    static String url = "jdbc:mysql://localhost:3306/skillbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String user = "root";
    static String password = "Va12821393___";

    public static void main(String[] args) {

        try {
            Connection connection = createConnection();
            Statement statement = createStatement(connection);
            addIdToTable(statement, "purchaselist");
            addIdToTable(statement, "subscriptions");
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
        Perchaselist perchaselist = session.get(Perchaselist.class, 250);
        Subscription subscription = session.get(Subscription.class, 20);
        Course course = session.get(Course.class, subscription.getCourseId());
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
            dropIdFromTable(statement, "purchaselist");
            dropIdFromTable(statement, "subscriptions");
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void dropIdFromTable(Statement statement, String table) throws SQLException {
        statement.executeUpdate("ALTER TABLE " + table + " DROP COLUMN id");
    }

    private static Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    private static void addIdToTable(Statement statement, String table) throws SQLException {
        statement.executeUpdate("ALTER TABLE " + table + " ADD id int NOT NULL FIRST");
        statement.executeUpdate("ALTER TABLE " + table + " ADD INDEX (id)");
        statement.executeUpdate("ALTER TABLE " + table + " CHANGE id id INT NOT NULL AUTO_INCREMENT");
    }

    private static Statement createStatement(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement;
    }
}
