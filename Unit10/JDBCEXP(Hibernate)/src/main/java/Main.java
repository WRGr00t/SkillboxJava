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

    static String url = "jdbc:mysql://localhost:3306/skillbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
            "useLegacyDatetimeCode=false&serverTimezone=UTC";
    static String user = "root";
    static String password = "Va12821393___";

    public static void main(String[] args) {

        try {
            Connection connection = CreateConnection();
            Statement statement = CreateStatement(connection);
            AddIdToTable(statement, "purchaselist");
            AddIdToTable(statement, "subscriptions");
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
        System.out.println(perchaselist.getCourseName() + " стоит - " + perchaselist.getPrice());
        Course course = session.get(Course.class, subscription.getCourseId());
        List<Student> students = course.getStudents();
        System.out.println("На курс " + course.getName() + " подписаны:");
        for (Student student : students) {
            System.out.println(student.getName());
        }
        transaction.commit();
        sessionFactory.close();
        try {
            Connection connection = CreateConnection();
            Statement statement = CreateStatement(connection);
            DropIdFromTable(statement, "purchaselist");
            DropIdFromTable(statement, "subscriptions");
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void DropIdFromTable(Statement statement, String table) throws SQLException {
        statement.executeUpdate("ALTER TABLE " + table + " DROP COLUMN id");
    }

    private static Connection CreateConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    private static void AddIdToTable(Statement statement, String table) throws SQLException {
        statement.executeUpdate("ALTER TABLE " + table + " ADD id int NOT NULL FIRST");
        statement.executeUpdate("ALTER TABLE " + table + " ADD INDEX (id)");
        statement.executeUpdate("ALTER TABLE " + table + " CHANGE id id INT NOT NULL AUTO_INCREMENT");
    }

    private static Statement CreateStatement(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        return statement;
    }
}
