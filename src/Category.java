import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Category {
    private String name;
    private ArrayList<Category> categories;

    public Category( ArrayList<Category> arrOfCategory, String name) {
        categories = arrOfCategory;
        this.name=name;
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

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public void displayCategory(){
        for(int i=0; i<categories.size();i++) {
            System.out.println(categories.get(i));
        }
    }
    public void addCategory(Category c){
        categories.add(c);
    }
}
