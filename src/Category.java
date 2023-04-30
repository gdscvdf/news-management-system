import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Category {
    private String name;
    private static final ArrayList<Category> categories = new ArrayList<>();

//    TODO => methods
    private final ArrayList<News> NewsOfCategory = new ArrayList<>();

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


    public void displayCategory(){
        for (Category category : categories) {
            System.out.println(category);
        }
    }
    public void addCategory(Category c){
        categories.add(c);
    }
}
