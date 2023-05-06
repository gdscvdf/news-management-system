import java.util.*;

public class News {
    private String description;
    private String title;
    private Date date;
    private static ArrayList<Float> totalRates;
    private float rate;
    private Category category;
//    TODO => methods
    public static Queue<News> allNews = new LinkedList<>();
    private Queue<Comment> comments;

    public News(String description, String title, Category category, Queue<Comment> comment) {
        this.description = description;
        this.title = title;
        this.date = new Date();
        this.category = category;
        this.comments = new LinkedList<Comment>();
        ///////////////////////////////////////
        this.category.addNewsToRelatedCategory(this);
        News.allNews.add(this);
    }

    public News(String description, String title, Category category, Queue<Comment> comment,float rate,Date date) {
        this.description = description;
        this.title = title;
        this.date = new Date();
        this.category = category;
        this.comments = new LinkedList<Comment>();
        this.rate = rate;
        this.date = date;
        ///////////////////////////////////////
        this.category.addNewsToRelatedCategory(this);
        News.allNews.add(this);
    }

    public void setDate() {
        this.date = new Date();
    }

    public ArrayList<Float> getTotalRates() {
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
        if (totalRates.size() != 0) {
            for (int i = 0; i < totalRates.size(); i++) {
                this.rate += totalRates.get(i);
            }
            this.rate /= totalRates.size();
        } else this.rate = 0;
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

    public void addRate(float rate) {
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
    public void newsToOpen () {
        System.out.println(this.getTitle());
        System.out.println("\t" + this.getDescription());
        System.out.println( "\t" + "For rating press 1 , For comment press 2 ");
        Scanner input = new Scanner(System.in);
        int i = input.nextInt();
        if(i==1){
            System.out.println("Enter your rate ");
            int userRate = input.nextInt();
            this.addRate(userRate);
        } else if (i==2) {
            System.out.println("Enter your comment");
            String userComment = input.next();
//            User u;
//            Comment comment(,)
//            this.addNewComment();

        }


    }


}
