import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {

  public Admin(String username, String password, int age, boolean isAdmin) {
    super(username, password, isAdmin, age);
  }

  public static void addNews(News news) {News.allNews.add(news);
  }

  public static void removeNews(News news) {
    if (News.allNews.contains(news)) {
      News.allNews.remove(news);
    } else {
      System.out.println("News article not found.");
    }
  }

  public static void updateNews(News news) {
    Scanner scanner = new Scanner(System.in);
    String newTitle = scanner.next();
    String newBody = scanner.next();
    if (News.allNews.contains(news)) {
      if (newTitle != null) {
        news.setTitle(newTitle);
      }
      if (newBody != null) {
        news.setDescription(newBody);
      }
    } else {
      System.out.println("News article not found.");
    }
  }
}
