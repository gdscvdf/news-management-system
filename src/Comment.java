import java.util.Date;

public class Comment {
  private User user;
  private String comment;
  private Date date;

  public Comment(User user, String comment) {
    this.user = user;
    this.comment = comment;
    this.date = new Date();
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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

  public void displayComment() {
    System.out.println(this.user.getUserName() + "\n" + this.comment + "\n" + this.date);
  }
}
