import java.util.ArrayList;
import java.util.Scanner;

public class Category {
    private String name;
    public static final ArrayList<Category> categories = new ArrayList<>();

    public final ArrayList<News> NewsOfCategory = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public ArrayList<News> getNewsOfCategory() {
        return NewsOfCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public static void displayCategory(){
        int i = 1;
        for (Category category : categories) {
            System.out.println(i + " - " + category.getName());
            i++;
        }
    }
    public static void addCategory(Category c){
        categories.add(c);
    }

    public void addNewsToCategory(News news) {
        NewsOfCategory.add(news);
    }

    public void addNewsToRelatedCategory(News n){
        NewsOfCategory.add(n);
    }
    public void displayNewsOfCategory() {
        for(int i=0;i<this.NewsOfCategory.size();i++){
            System.out.println(i+1 + " - " + this.NewsOfCategory.get(i).getTitle());
            System.out.println("\t" + this.NewsOfCategory.get(i).getDescription());
        }
    }
    public static int filterByCategory() throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose category from the following list:");
            displayCategory();
            System.out.print("Enter the name of the category: ");
            return scanner.nextInt();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Main.startup();
            return -1;
        }
    }

}
