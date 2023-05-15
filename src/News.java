import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class News {
    private String description;
    private String title;
    private static final ArrayList<Float> totalRates = new ArrayList<>();
    private float rate;
    private Category category;
    public static ArrayList<News> allNews = new ArrayList<>();

    public static Map<String, ArrayList<News>> MappedNews = new HashMap<>();
    private Queue<Comment> comments;

    public News() {
    }

    public News(String description, String title, Category category, Queue<Comment> comment) {
        this.description = description;
        this.title = title;
        this.category = category;
        this.comments = new LinkedList<Comment>();
        ///////////////////////////////////////
        this.category.addNewsToRelatedCategory(this);
        News.allNews.add(this);
    }


    public News(String description, String title, Category category, Queue<Comment> comment,float rate) {
        this.description = description;
        this.title = title;
        this.category = category;
        this.comments = new LinkedList<Comment>();
        this.rate = rate;
        ///////////////////////////////////////
        this.category.addNewsToRelatedCategory(this);
        News.allNews.add(this);
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

    public float getRate() {
        if (totalRates.size() != 0) {
            for (Float totalRate : totalRates) {
                this.rate += totalRate;
            }
            this.rate /= totalRates.size();
        } else this.rate = 0;
        return rate;
    }

    public void addNewComment(Comment comment) throws Exception {
        this.comments.add(comment);
        try {
            Connection connection = DBConnection.connection();
            String query = "Insert INTO news.comment (title, description, username) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, comment.getNewsTitle());
            statement.setString(2, comment.getComment());
            statement.setString(3, comment.getUsername());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Main.startup();
        }
    }

    public void displayComments() {
        for (Comment c : this.comments) {
            c.displayComment();
        }
    }

    public void addRate(float rate) {
        totalRates.add(rate);
    }

    public void newsToOpen (User user) throws Exception {
        try {
            System.out.println(this.getTitle());
            System.out.println("\t" + this.getDescription());
            System.out.println("Comments: \n");
            this.displayComments();
            System.out.println("\t" + "For rating press 1 , For comment press 2 ");
            Scanner input = new Scanner(System.in);
            int i = input.nextInt();
            if (i == 1) {
                System.out.println("Enter your rate ");
                int userRate = input.nextInt();
                this.addRate(userRate);
            } else if (i == 2) {
                System.out.println("Enter your comment");
                String userComment = input.next();
                this.addNewComment(new Comment(user.getUserName(), new Date(), userComment));
                System.out.println("Added");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Main.startup();
        }

    }


}
