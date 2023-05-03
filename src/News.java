import java.util.*;

public class News {
    private String description;
    private String title;
    private Date date;
    private static ArrayList<Integer> totalRates;
    private float rate;
    private Category category;
//    TODO => methods
    public static Queue<News> allNews;
    private Queue<Comment> comments;

    public News(String description, String title, Category category, Queue<Comment> comment) {
        this.description = description;
        this.title = title;
        this.date = new Date();
        this.category = category;
        this.comments = new LinkedList<Comment>();
        ///////////////////////////////////////
        this.category.addNewsToRelatedCategory(this);

    }

    public void setDate() {
        this.date = new Date();
    }

    public static ArrayList<Integer> getTotalRates() {
        return totalRates;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Queue<Comment> getComments() {
        return comments;
    }

    public void setComment() {
        this.comments = new LinkedList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public float getRate() {
        return rate;
    }

    public void addNewComment(Comment comment) {
        this.comments.add(comment);
    }

    public void displayComments() {
        Iterator<Comment> it = this.comments.iterator();
        while (it.hasNext()) {
            it.next().displayComment();
        }
    }

    public void addRate(int rate) {
        totalRates.add(rate);
    }

    public void setRate() {
        if (totalRates.size() != 0) {
            for (int i = 0; i < totalRates.size(); i++) {
                this.rate += totalRates.get(i);
            }
            this.rate /= totalRates.size();
        } else this.rate = 0;
    }
}
