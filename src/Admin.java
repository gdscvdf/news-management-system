import java.util.ArrayList;

public class Admin extends User {

  public Admin(String username, String password, int age, boolean isAdmin) {
    super(username, password, isAdmin, age);
  }

  public void addNews(News news) {News.allNews.add(news);
  }

  public void removeNews(News news) {
    if (News.allNews.contains(news)) {
      News.allNews.remove(news);
    } else {
      System.out.println("News article not found.");
    }
  }

  public void updateNews(News news, String newTitle, String newBody) {
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
