import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static ArrayList<News> fixedNews = new ArrayList<>();
    static List<News> uniqueList = null;
    public static void main(String[] arg) throws Exception {
        retrieveData();
        startup();
    }

    public static void startup() throws Exception {
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\tWelcome to News System\n");
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
                        options(User.allUsers.get(i));
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
                options(user);

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


            // retrieve all categories from DB
            String catSql = "SELECT * FROM news.category";
            PreparedStatement catPsmt = connection.prepareStatement(catSql);
            ResultSet catResultSet = catPsmt.executeQuery();
            while (catResultSet.next()) {
                String categoryName = catResultSet.getString(2);
                Category category = new Category(categoryName);
                Category.categories.add(category);
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
            // retrieve all users from DB
            String userSql = "SELECT * FROM news.user";
            PreparedStatement userPsmt = connection.prepareStatement(userSql);
            ResultSet userResultSet = userPsmt.executeQuery();
            while (userResultSet.next()) {
                String username = userResultSet.getString(2);
                String password = userResultSet.getString(3);
                boolean isAdmin = userResultSet.getBoolean(4);
                int age = userResultSet.getInt(5);
                new User(username, password, isAdmin, age);
            }
            for (int i = 0; i < Category.categories.size(); i++) {
                for (int j = 0; j < News.allNews.size(); j++) {
                    if (News.allNews.get(j).getCategory().getName().equals(Category.categories.get(i).getName())) {
                        Category.categories.get(i).addNewsToCategory(News.allNews.get(j));
                    }
                }
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
            ///////////
            for (int i = 0; i < Category.categories.size(); i++) {
                News.MappedNews.put(Category.categories.get(i).getName(), Category.categories.get(i).getNewsOfCategory());
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            startup();
        }
    }

    public static void options(User user) throws Exception {
        try {
            int choice = 0;
            if(!user.isAdmin()) {
                System.out.println("1-Choose news to read\n2-To comment or rate\n3-Filter news by category\n4-Enter keyword to search for news\n5-Log Out");
                System.out.println("Enter the number of option you want: \n");
                choice = scanner.nextInt();
            }else {
                System.out.println("1-Choose news to read\n2-To comment or rate\n3-Filter news by category\n4-Enter keyword to search for news\n5-Log Out\n6-Add news\n7-Remove news\n8-Update news");
                choice = scanner.nextInt();
            }
            switch (choice) {
                case 1:
                    System.out.println("Choose the index of the news: \n");
                    int chosenNum = scanner.nextInt();
                    int count = 1;
                    for (News news : uniqueList) {
                        if (chosenNum==count){
                            System.out.println(news.getTitle());
                            System.out.println(news.getDescription());
                            System.out.println(news.getDate());
                            System.out.println("Comments: \n");
                            news.displayComments();
                            break;
                                }
                        count++;
                    }
                    System.out.println("To return enter 1 else enter 0 \n");
                    int returnNum = scanner.nextInt();
                    if(returnNum ==1){
                        displayNews(user);
                        options(user);
                    }
                    else System.exit(0);
                    break;
                case 2:
                    System.out.println("Choose news to open \n");
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
                    System.out.println("To return enter 1 else enter 0 \n");
                    int returnNum1 = scanner.nextInt();
                    if(returnNum1 ==1){
                        displayNews(user);
                        options(user);
                    }
                    else System.exit(0);
                    break;
                case 3:
                    String filteredCategory = Category.filterByCategory();
                    for(int i=0 ; i<Category.categories.size(); i++){
                        if(Category.categories.get(i).getName() == filteredCategory){
                            Category.categories.get(i).displayNewsOfCategory();
                            break;
                        }
                    }
                    System.out.println("To return enter 1 else enter 0 \n");
                    int returnNum2 = scanner.nextInt();
                    if(returnNum2 ==1){
                        displayNews(user);
                        options(user);
                    }
                    else System.exit(0);
                    break ;
                case 4:
                    String keyword =scanner.next();
                    Search(keyword);
                    System.out.println("To return enter 1 else enter 0 \n");
                    int returnNum3 = scanner.nextInt();
                    if(returnNum3 ==1){
                        displayNews(user);
                        options(user);
                    }
                    else System.exit(0);
                    break;
                case 5:
                    System.exit(0);
                    break;
                case 6:
                    System.out.println("Enter the description :\n");
                    String description = scanner.next();
                    System.out.println("Enter the title :\n");
                    String title = scanner.next();
                    System.out.println("Enter the category :\n");
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
                    }
                    System.out.println("To return enter 1 else enter 0");
                    int returnNum4 = scanner.nextInt();
                    if(returnNum4 ==1){
                        displayNews(user);
                        options(user);
                    }
                    else System.exit(0);
                    break;
                case 7:
                    System.out.println("Choose index to remove: \n");
                    int chosennum = scanner.nextInt();
                    for(int i= 0;i <uniqueList.size();i++){
                        if(chosennum== i){
                            Admin.removeNews(uniqueList.get(i));
                            break;
                        }
                    }System.out.println("To return enter 1 else enter 0");
                    int returnNum5 = scanner.nextInt();
                    if(returnNum5 ==1){
                        displayNews(user);
                        options(user);
                    }
                    else System.exit(0);

                    break;
                case 8:
                    System.out.println("Choose index to update: \n");
                    int choseNum =scanner.nextInt();
                    for(int i= 0;i <uniqueList.size();i++){
                        if(choseNum== i){
                            Admin.updateNews(uniqueList.get(i));
                            break;
                        }
                    }
                    System.out.println("To return enter 1 else enter 0");
                    int returnNum6 = scanner.nextInt();
                    if(returnNum6 ==1){
                        displayNews(user);
                        options(user);
                    }
                    else System.exit(0);
                    break;
                default:
                    displayNews(user);
                    options(user);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void displayNews(User user) {
        for (News n : News.allNews) {
            if (n.getRate() < 2 && n.getTotalRates().size() > 2) {
                News.allNews.remove(n);
            }
        }

        if (user.isAdmin()) {
            fixedNews.addAll(News.allNews);
        } else {
            fixedNews.addAll(user.getPreferences().get(0).getNewsOfCategory());
            fixedNews.addAll(user.getPreferences().get(1).getNewsOfCategory());
            fixedNews.addAll(News.allNews);
            uniqueList = fixedNews.stream().distinct().toList();
        }
        int i = 1;
        for (News news : uniqueList) {
            System.out.println(i + " - " + news.getTitle());
            i++;
        }
    }

    public static void Search(String keyword) {
        ArrayList<News> filteredNews = new ArrayList<>();
        for (News news : News.allNews) {
            if (news.getTitle().contains(keyword) || news.getDescription().contains(keyword)) {
                filteredNews.add(news);
            }
        }
        int i =1 ;
        for(News news: filteredNews){
            System.out.println(i + " - " + news.getTitle());
            i++;
        }
    }
}