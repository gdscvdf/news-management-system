import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class Category {
    private String name;
    public static final ArrayList<Category> categories = new ArrayList<>();

//    TODO => methods
    public final ArrayList<News> NewsOfCategory = new ArrayList<>();

    public Category(String name) {
        this.name=name;
        categories.add(this);
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

//    public static String[] getCategoriesFromDB() throws Exception {
//        try {
//            Connection connection = DBConnection.connection();
//
//            String sql = "SELECT name FROM news.category";
//            PreparedStatement psmt = connection.prepareStatement(sql);
//            ResultSet resultSet = psmt.executeQuery();
//            if (resultSet.next()) {
//                Array ArrOfCategories = resultSet.getArray("name");
//                System.out.println(ArrOfCategories);
//            }
//            return null;
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            return null;
//        }
//    }


    public static void displayCategory(){
        for (Category category : categories) {
            System.out.println(category);
        }
    }
    public static void addCategory(Category c){
        categories.add(c);
    }

    public void addNewsToRelatedCategory(News n ){
        NewsOfCategory.add(n);
    }
    public void displayNewsOfCategory() {
        for(int i=0;i<this.NewsOfCategory.size();i++){
            System.out.println(i+1 + " - " + this.NewsOfCategory.get(i).getTitle());
            System.out.println("\t" + this.NewsOfCategory.get(i).getDescription());
        }
    }
    public static String filterByCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose category from the following list:");
        displayCategory();
        System.out.print("Enter the name of the category: ");
        return scanner.next();
    }

}
