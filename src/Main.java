import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static Stack<News> fixedNews = new Stack<News>();
    public static void main(String[] arg) throws Exception {
        retrieveData();
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
        try {
            System.out.print("Enter username: \n");
            String username = scanner.next();

            System.out.print("Enter password: \n");
            String password = scanner.next();

            for (int i = 0; i < User.allUsers.size(); i++) {
                if (username.equals(User.allUsers.get(i).getUserName())) {
                    if (password.equals(User.allUsers.get(i).getPassword())) {
                        displayNews(User.allUsers.get(i));
                    } else {
                        System.out.println("Invalid Password");
                        startup();
                        return;
                    }
                }
            }
            System.out.println("the username is invalid\n");
            startup();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            startup();
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
                user.addPreferences();
                displayNews(user);

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

    public static void retrieveData() throws Exception {
        try {
            Connection connection = DBConnection.connection();

            // retrieve all users from DB
            String userSql = "SELECT * FROM news.user";
            PreparedStatement userPsmt = connection.prepareStatement(userSql);
            ResultSet userResultSet = userPsmt.executeQuery();
            while (userResultSet.next()) {
                String username = userResultSet.getString(2);
                String password = userResultSet.getString(3);
                boolean isAdmin = userResultSet.getBoolean(4);
                int age = userResultSet.getInt(5);
                User user = new User(username, password, isAdmin, age);
            }
            // retrieve all news from DB
            String newsSql = "SELECT * FROM news.news";
            PreparedStatement newsPsmt = connection.prepareStatement(newsSql);
            ResultSet newsResultSet = newsPsmt.executeQuery();
            while (newsResultSet.next()) {
                Date date = newsResultSet.getDate(2);
                float rate = newsResultSet.getFloat(3);
                String description = newsResultSet.getString(4);
                String title = newsResultSet.getString(5);
                String categoryName = newsResultSet.getString(6);
                Category category = new Category(categoryName);
                News news = new News(description, title, category, new LinkedList<>(), rate, date);
            }
            // retrieve all preferences from DB
            String prefSql = "SELECT * FROM news.preferences";
            PreparedStatement prefPsmt = connection.prepareStatement(prefSql);
            ResultSet prefResultSet = prefPsmt.executeQuery();
            while (prefResultSet.next()) {
                String categoryName = prefResultSet.getString(1);
                String username = prefResultSet.getString(2);
                for (int i = 0; i < Category.categories.size(); i++) {
                    if (categoryName.equals(Category.categories.get(i).getName())) {
                        for (int j = 0; j < User.allUsers.size(); j++) {
                            if (username.equals(User.allUsers.get(i).getUserName())) {
                                User.allUsers.get(i).addPreferences(Category.categories.get(i));
                            }
                        }
                    }
                }
            }
            // retrieve all categories from DB
            String catSql = "SELECT * FROM news.category";
            PreparedStatement catPsmt = connection.prepareStatement(catSql);
            ResultSet catResultSet = catPsmt.executeQuery();
            while (catResultSet.next()) {
                String categoryName = catResultSet.getString(2);
                Category category = new Category(categoryName);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            startup();
        }
    }

    public void options(User user) throws Exception {
        try {
            int choice;
            if(!user.isAdmin()) {
                System.out.println("1-Choose news to comment or rate \n 2-Filter news by category \n  ");
                System.out.println("Enter the number of option you want: \n ");
                 choice = scanner.nextInt();
            }else {
                System.out.println("1-To comment or rate \n 2-Filter news by category \n 3-Add news \n 4-Remove news \n 5-Update news \n ");
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 1:
                    System.out.println("Choose news to open \n ");
                    int numberOfNews = scanner.nextInt()-1;
                    Iterator<News> iterate = fixedNews.iterator();
                    int counter = 0;
                    News chosenNews =new News() ;
                    while (iterate.hasNext()){
                     chosenNews = iterate.next();
                        if(counter == numberOfNews)
                            break;
                        counter++;
                       }
                    chosenNews.newsToOpen(user);


                        break;
                case 2:
                        String filteredCategory = Category.filterByCategory();
                        for(int i=0 ; i<Category.categories.size(); i++){
                            if(Category.categories.get(i).getName() == filteredCategory){
                                Category.categories.get(i).displayNewsOfCategory();
                                break;
                            }
                        }
                        break;
                case 3:
                    System.out.println("Enter the description :\n ");
                    String description = scanner.next();
                    System.out.println("Enter the title :\n ");
                    String title = scanner.next();
                    System.out.println("Enter the category :\n ");
                    String name = scanner.next();
                    for (int j = 0; j < Category.categories.size(); j++) {
                        if (name.equals(Category.categories.get(j).getName())) {
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
                case 4:
                        String removedTitle = scanner.next();
                        Iterator<News> it = News.allNews.iterator();
                        while (it.hasNext()){
                           News news = it.next();
                           if(news.getTitle() == removedTitle ) {
                               Admin.removeNews(news);
                               break;
                           }
                        }
                        break;
                case 5:
                        String updatedTitle = scanner.next();
                        Iterator<News> itt = News.allNews.iterator();
                        while (itt.hasNext()){
                            News news = itt.next();
                            if(news.getTitle() == updatedTitle ) {
                                Admin.updateNews(news);
                                break;
                            }
                        }
                        break;
            }
        }
        catch (Exception e){
            System.out.println(" invalid input ! ");
        }
    }
    public static void displayNews(User user) {
        for (News n : News.allNews) {
            if (n.getRate() < 2 && n.getTotalRates().size() > 2) {
                News.allNews.remove(n);
            }
        }
        if (user.isAdmin()) {
            for (News n : News.allNews)
                fixedNews.push(n);
        } else {
            for (int i = 0; i < user.getPreferences().size(); i++) {
                for (News n : News.allNews)
                    if (!user.getPreferences().get(i).getName().equals(n.getCategory().getName())) {
                        fixedNews.push(n);
                    }
            }

            for (int i = 0; i < user.getPreferences().size(); i++) {
                for (News n : News.allNews) {
                    if (user.getPreferences().get(i).getName().equals(n.getCategory().getName())) {
                        fixedNews.push(n);
                    }
                }
            }
        }
       Iterator<News> it = fixedNews.iterator();
        int counter = 0;
        while (it.hasNext()){
            News news = it.next();
            counter++;
            System.out.println( counter + " - " + news.getTitle());
        }
    }
}