
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class DBConnection {
    private static Connection connection;

    private static String dbName = "learn";
    private static String dbUser = "SkillboxUser";
    private static String dbPass = "123456789";

    private static ArrayList<Voter> voters = new ArrayList<>();
    private static StringBuilder insertQuery = new StringBuilder();

    public static Connection getConnection()
    {
        String url = "jdbc:mysql://localhost:3306/" + dbName +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"+
                "&rewriteBatchedStatements=true";
        if(connection == null)
        {
            try {
                connection = DriverManager.getConnection(url, dbUser, dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "name VARCHAR(50) NOT NULL, " +
                        "birthDate DATE NOT NULL, " +
                        "`count` INT NOT NULL, " +
                        "PRIMARY KEY(id), " +
                        "UNIQUE KEY name_date(name(50), birthDate))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsert() throws SQLException {
        /*String sql = "INSERT INTO voter_count(name, birthDate, `count`) VALUES"
                //+ insertQuery.toString() +
                + insertQueryByParts.getQuery().toString() +
                " ON DUPLICATE KEY UPDATE `count`=`count` + 1;";
        getConnection().createStatement().executeUpdate(sql);*/
        String sql = "INSERT INTO voter_count(name, birthDate, `count`) Values ? ON DUPLICATE KEY UPDATE `count`=`count` + 1;";
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        System.out.printf("Загружаем %d записей%n", voters.size());
        for (int i = 0; i < voters.size(); i++) {
            preparedStatement.setString(1, voters.get(i).toQuery());
            if (i < voters.size() - 1){
                preparedStatement.setString(1, ", ");
            }
            preparedStatement.addBatch();
        }
        System.out.println(preparedStatement);
        preparedStatement.executeBatch();
        voters = new ArrayList<>();
    }

    public static void countVoter(String name, String birthDay) throws SQLException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = dateFormat.parse(birthDay);
        voters.add(new Voter(name, date));
        if (insertQuery.toString().length() != 0) {
            insertQuery.append(",");
        }
        insertQuery.append(" ('")
                .append(name)
                .append("', '")
                .append(birthDay)
                .append("', 1)");
        if (voters.size() % 5000 == 0 || XMLHandler.isEnd()) {
            System.out.println("+1");
            executeMultiInsert();
            voters = new ArrayList<>();
        }
        /*if (insertQuery.length() == 0) {
            s = " ('" + name + "', '" + birthDay + "', 1)";
        } else {
            s = "," + " ('" + name + "', '" + birthDay + "', 1)";
        }
        insertQuery.append(s);*/

        /*String sql = "INSERT INTO voter_count(name, birthDate, `count`) " +
                "VALUES('" + name + "', '" + birthDay + "', 1) " +
                "ON DUPLICATE KEY UPDATE `count`=`count` + 1";
        DBConnection.getConnection().createStatement().executeQuery(sql);*/
        /*String sql = "SELECT id FROM voter_count WHERE birthDate='" + birthDay + "' AND name='" + name + "'";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        if(!rs.next())
        {
            DBConnection.getConnection().createStatement()
                    .execute("INSERT INTO voter_count(name, birthDate, `count`) VALUES('" +
                            name + "', '" + birthDay + "', 1)");
        }
        else {
            Integer id = rs.getInt("id");
            DBConnection.getConnection().createStatement()
                    .execute("UPDATE voter_count SET `count`=`count`+1 WHERE id=" + id);
        }
        rs.close();*/
    }

    public static void printVoterCounts() throws SQLException
    {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while(rs.next())
        {
            System.out.println("\t" + rs.getString("name") + " (" +
                    rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
