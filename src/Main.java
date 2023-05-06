import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] arg) throws Exception {
        startup();
    }

    public static void startup() throws Exception {
        System.out.println("\nWelcome to News System\n");
        System.out.println("Press 1 for login, 2 for register\n");
        try {
            boolean checker = false;
            do {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> {
                        checker = false;
                        login();
                    }
                    case 2 -> {
                        checker = false;
                        register();
                    }
                    default -> {
                        checker = true;
                        System.out.println("Invalid input try again");
                    }
                }
            } while (checker);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            startup();
        }
    }

    public static void login() throws Exception {
        System.out.print("Enter username: \n");
        String username = scanner.next();

        System.out.print("Enter password: \n");
        String password = scanner.next();

        Connection connection = DBConnection.connection();
        String searchSql = "SELECT * FROM news.user where username = ?";
        PreparedStatement psmt = connection.prepareStatement(searchSql);
        psmt.setString(1, username);
        ResultSet resultSet = psmt.executeQuery();
        if(resultSet.next()) {
            String dbPassword = resultSet.getString(3);
            if (dbPassword.equals(password)) {
                System.out.println("True password");
                boolean isAdmin = resultSet.getBoolean(4);
                int age = resultSet.getInt(5);
                if (isAdmin) {
                    User admin = new Admin(username, dbPassword, age, true);
                }
                else {
                    User user = new User(username, dbPassword, false, age);
                }
            } else {
                System.out.println("Username or password is Invalid");
                startup();
            }
        }
    }

    public static void register() throws Exception {
        try {
            System.out.print("Enter username: \n");
            String username = scanner.next();

            System.out.print("Enter password: \n");
            String password = scanner.next();

            System.out.print("Enter Age: \n");
            int age = scanner.nextInt();

            // Validate the username and password
            if (isValid(username, password)) {
                Connection connection = DBConnection.connection();

                // check if the username is already used
                String searchSql = "SELECT * FROM news.user where username = ?";
                PreparedStatement psmt = connection.prepareStatement(searchSql);
                psmt.setString(1, username);
                ResultSet resultSet = psmt.executeQuery();
                if(resultSet.next()) {
                    System.out.println("this username is Already used");
                    startup();
                    return;
                }
                User user = new User(username, password, false, age);
                user.save();

            } else {
                System.out.println("Invalid inputs.");
                register();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            register();
        }
    }

    public static boolean isValid(String username, String password) {
        // Username must be at least 6 characters long and contain only letters and numbers
        // Password must be at least 8 characters long, contain at least one uppercase letter,
        // at least one lowercase letter, and at least one number

        String usernameRegex = "^[a-zA-Z0\\d]{6,}$";
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";

        return username.matches(usernameRegex) && password.matches(passwordRegex);
    }
//  TODO
    public static void retrieveData() {

    }
    public void options() {
        int i = 0;
        String description = scanner.next();
        String title = scanner.next();
        String name = scanner.next();
        switch (i){
            case 1:
                break;
            case 2:
                Category.filterByCategory();
                        break;
            case 3:
                for (int j=0;j<Category.categories.size();j++) {
                    if (name == Category.categories.get(j).getName()) {
                        News news = new News(description, title, Category.categories.get(j), new LinkedList<Comment>());
                        Admin.addNews(news);
                    } else {
                        Category category = new Category(name);
                        Category.addCategory(category);
                        News news = new News(description, title, Category.categories.get(j), new LinkedList<Comment>());
                        Admin.addNews(news);
                    }
                        break;
                }
//            case 4:
//                Admin.removeNews();
//                break;
//            case 5:
//                Admin.updateNews();
//                break;



        }
    }
}