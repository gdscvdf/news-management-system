import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Admin extends User {

  public Admin(String username, String password, int age, boolean isAdmin) {
    super(username, password, isAdmin, age);
  }

  public static void addNews(News news) throws Exception {
    News.allNews.add(news);
    try {
      Connection connection = DBConnection.connection();
      String query = "Insert INTO news.news (rate, description, title, category_name) VALUES (?, ?, ?, ?)";
      PreparedStatement statement = connection.prepareStatement(query);
      statement.setFloat(1, news.getRate());
      statement.setString(2, news.getDescription());
      statement.setString(3, news.getTitle());
      statement.setString(4, news.getCategory().getName());
      statement.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      Main.startup();
    }
  }

  public static void removeNews(int idx) throws Exception {
    try {
      if (idx >= 0 && idx < News.allNews.size()) {
        News news = News.allNews.get(idx);
        News.allNews.remove(idx);
        Main.uniqueList = News.allNews;

        Connection connection = DBConnection.connection();
        String query = "DELETE FROM news.news WHERE title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, news.getTitle());
        statement.executeUpdate();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void updateNews(News news)throws Exception {
    Scanner scanner = new Scanner(System.in);

    try{
      System.out.println("1-Update the title only \n 2-Update the body only \n 3-Update both");
      int choice = scanner.nextInt();
      switch (choice) {
        case 1 -> {
          System.out.println("Enter the new title: \n");
          String newTitle = scanner.next();
          if (newTitle != null) {
            news.setTitle(newTitle);
            ///////
            Connection connection = DBConnection.connection();
            String query = "UPDATE news.news SET title = ? WHERE description = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newTitle);
            statement.setString(2, news.getDescription());
            statement.executeUpdate();
          } else
            System.out.println("Invalid input");
        }
        case 2 -> {
          System.out.println("Enter the new description: \n");
          String newBody = scanner.next();
          if (newBody != null) {
            news.setDescription(newBody);
            //////
            Connection connection = DBConnection.connection();
            String query = "UPDATE news.news SET description = ?, WHERE title = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newBody);
            statement.setString(2, news.getTitle());
            statement.executeUpdate();
          } else
            System.out.println("Invalid input");
        }
        case 3 -> {
          System.out.println("Enter the new title: \n");
          String newTitle = scanner.next();
          if (newTitle != null) {
            news.setTitle(newTitle);
            Connection connection = DBConnection.connection();
            String query = "UPDATE news.news SET title = ?, WHERE description = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newTitle);
            statement.setString(2, news.getDescription());
            statement.executeUpdate();
          } else
            System.out.println("Invalid input");
          System.out.println("Enter the new description: \n");
          String newBody = scanner.next();
          if (newBody != null) {
            news.setDescription(newBody);
            Connection connection = DBConnection.connection();
            String query = "UPDATE news.news SET description = ?, WHERE title = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newBody);
            statement.setString(2, news.getTitle());
            statement.executeUpdate();
          } else
            System.out.println("Invalid input");
        }
        default -> updateNews(news);
      }
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
}
