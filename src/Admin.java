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

  public static void updateNews(News news)throws Exception {
    Scanner scanner = new Scanner(System.in);

    try{
      System.out.println("1-Update the title only \n 2-Update the body only \n 3-Update both");
      int choise = scanner.nextInt();
      switch (choise){
        case 1:
          System.out.println("Enter the new title: \n");
          String newTitle = scanner.next();
          if (newTitle != null) {
            news.setTitle(newTitle);
          }
          else
            System.out.println("Invalid input");
          break;
        case 2:
          System.out.println("Enter the new description: \n");
          String newBody = scanner.next();
          if (newBody != null) {
            news.setDescription(newBody);
          } else
            System.out.println("Invalid input");
          break;
        case 3:
          System.out.println("Enter the new title: \n");
          String newtitle = scanner.next();
          if (newtitle != null) {
            news.setTitle(newtitle);
          }
          else
            System.out.println("Invalid input");
          System.out.println("Enter the new description: \n");
          String newbody = scanner.next();
          if (newbody != null) {
            news.setDescription(newbody);
          } else
            System.out.println("Invalid input");
          break;
        default:
          updateNews(news);
          break;
      }
    }catch(Exception e){
      System.out.println(e.getMessage());
    }

  }
}
