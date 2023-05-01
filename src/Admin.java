import java.util.ArrayList;

public class Admin extends User {
  private ArrayList<News> newsList;

  public Admin(String username, String password, int age, boolean isAdmin) {
    super(username, password, isAdmin, age);
  }

  public void addNews(News news) {
    this.newsList.add(news);
  }

  public void removeNews(News news) {
    if (this.newsList.contains(news)) {
      this.newsList.remove(news);
    } else {
      System.out.println("News article not found.");
    }
  }

  public void updateNews(News news, String newTitle, String newBody) {
    if (this.newsList.contains(news)) {
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
