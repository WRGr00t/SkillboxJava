import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&" +
                "useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String password = "Va12821393___";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT course_name, COUNT(*)/(MAX(MONTH(subscription_date))-MIN(MONTH(subscription_date))+1) as subscription_per_month FROM purchaselist GROUP BY course_name");
            while (resultSet.next()) {
                String nameCourses = resultSet.getString("course_name");
                String count = resultSet.getString("subscription_per_month");
                System.out.print(nameCourses + "\tсреднее число подписок в месяц: ");
                System.out.println(count);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
