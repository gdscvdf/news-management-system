import java.util.Date;

public class Comment {
  private String username;
  private String comment;

  private String newsTitle;
  private Date date;

  public Comment(String comment, Date date,String username) {
    this.username = username;
    this.comment = comment;
    this.date = date;
  }
  public Comment(String comment,String username, String newsTitle) {
    this.username = username;
    this.comment = comment;
    this.newsTitle = newsTitle;
  }
  public String getUser() {
    return username;
  }

  public void setUser(String user) {
    this.username = user;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Date getDate() {
    return date;
  }

  public void setDate() {
    this.date = new Date();
  }

  public String getUsername() {
    return username;
  }

  public String getNewsTitle() {
    return newsTitle;
  }

  public void displayComment() {
    System.out.println(this.username + "\n" + this.comment + "\n" + this.date);
  }
}
