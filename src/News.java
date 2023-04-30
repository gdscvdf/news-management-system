import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class News {
    private String description;
    private String title;
    private Date date;
    private static ArrayList<Integer> totalRates;
    private float rate;
    private Category category;
    private Queue<Comment> comment;

    public News(String description, String title, int rate, Category category, Queue<Comment> comment) {
        this.description = description;
        this.title = title;
        this.date = new Date();
        this.category = category;
        this.comment = new LinkedList<Comment>();
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

    public Queue<Comment> getComment() {
        return comment;
    }

    public void setComment() {
        this.comment = new LinkedList<>();
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
        this.comment.add(comment);
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
