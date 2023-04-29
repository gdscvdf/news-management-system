import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private int age;
    private ArrayList<Category> preferences;
    private boolean isAdmin;

    public User(String UserName, String password, boolean isAdmin, int age) {
        this.username = UserName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.age = age;
    }

    public void save() throws Exception {
        // insert new user data in db
        Connection connection = DBConnection.connection();
        String insertSql = "INSERT INTO news.user (username, password, isAdmin, age) VALUES (?, ?, 0, ?)";
        PreparedStatement prepareStatement = connection.prepareStatement(insertSql);

        // Set the parameters for the statement
        prepareStatement.setString(1, this.username);
        prepareStatement.setString(2, this.password);
        prepareStatement.setInt(3, age);

        // Execute the statement
        int rows = prepareStatement.executeUpdate();
    }

    public void setUserName(String userName) {
        username = userName;
    }

    public String getUserName() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Category> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<Category> preferences) {
        this.preferences = preferences;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
