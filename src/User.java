import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private final int age;
    private final ArrayList<Category> preferences;

    public static ArrayList<User> allUsers = new ArrayList<>();
    private boolean isAdmin;

    public User(String UserName, String password, boolean isAdmin, int age) {
        this.username = UserName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.age = age;
        this.preferences = new ArrayList<>();
        allUsers.add(this);
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public void addPreferences () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose two categories from the following list:");
        for(int i=0; i<Category.categories.size(); i++){
            System.out.println((i+1) +". "+ Category.categories.get(i).getName());
        }
        System.out.print("Enter the number of the first category: ");
        int firstIndex = scanner.nextInt() - 1;
        preferences.add(Category.categories.get(firstIndex));

        System.out.print("Enter the number of the second category: ");
        int secondIndex = scanner.nextInt() - 1;
        preferences.add(Category.categories.get(secondIndex));
    }

    public void addPreferences(Category category) {
        this.preferences.add(category);
    }
}
