import org.w3c.dom.ls.LSOutput;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Connect {
    private static Connection connection;
    private static String dbName = "learn";
    private static String dbUser = "SkillboxUser";
    private static String dbPass = "123456789";
    private static ArrayList<Voter> voters = new ArrayList();

    public Connect() {
    }

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/" + dbName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&rewriteBatchedStatements=true";
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, dbUser, dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                //connection.createStatement().execute("CREATE TABLE voter_count(id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NOT NULL, birthDate DATE NOT NULL, `count` INT NOT NULL, PRIMARY KEY(id), UNIQUE KEY name_date(name(50), birthDate))");
                connection.createStatement().execute("CREATE TABLE voter_count(id INT NOT NULL AUTO_INCREMENT, name VARCHAR(50) NOT NULL, birthDate DATE NOT NULL, PRIMARY KEY(id))");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        return connection;
    }

    public static void countVoter(String name, String birthDay) throws SQLException, ParseException {
        //final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        //final LocalDate date = LocalDate.parse(birthDay, dtf);
        birthDay = birthDay.replace('.', '-');
        voters.add(new Voter(name, birthDay));
        if (voters.size() > 10_000 || XMLHandler.isEnd()) {
            save(voters);
            voters = new ArrayList<>();
        }
    }

    public static void save(List<Voter> voters) throws SQLException {
        if (connection != null) {
            //connection.createStatement().execute("DROP INDEX name_date(name(50), birthDate)) ON voter_count");

            connection.setAutoCommit(false);
            //String SQL_INSERT = "INSERT INTO voter_count(name, birthDate, `count`) Values (?, ? , ?) ON DUPLICATE KEY UPDATE `count`=`count` + 1;";
            String SQL_INSERT = "INSERT INTO voter_count(name, birthDate) Values (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
                for (Voter voter : voters) {
                    statement.setString(1, voter.getName());
                    statement.setDate(2, java.sql.Date.valueOf(voter.getBirthDay()));
                    //statement.setInt(3, 1);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
            connection.setAutoCommit(true);
        }
    }

    public static void printVoterCounts() throws SQLException {
        //String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        String sql = "SELECT name, birthDate, COUNT(*) AS `count` FROM voter_count GROUP BY name, birthDate HAVING `count` > 1;";
        ResultSet rs = getConnection().createStatement().executeQuery(sql);

        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" + rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }

    public static void addIndex() throws SQLException {
        connection.createStatement().execute("ALTER TABLE `voter_count` ADD KEY name_date(name(50), birthDate)");
    }
}
