import java.sql.*;

public class DBConnection {
    public static Statement statement() throws Exception {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/news",
                    "root", "password");

            //ResultSet resultSet = statement.executeQuery("Select * from news.user");

            return connection.createStatement();
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new Exception(exception.getMessage());
        }
    }

    public static Connection connection() throws Exception {
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/news",
                    "root", "peter2192003");

            return connection;
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new Exception(exception.getMessage());
        }
    }
}
