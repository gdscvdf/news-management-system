import java.util.Date;

public class News {
    String description;

    String title;
    Date date;
    static int totalRates[];
    int Rate;
    //Categories Category;
    //Comments comment[];

    public News(String description, String title) {
        description = description;
        title = title;
        date =new Date() ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }

    public Date getDate() {
        return date;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        if(totalRates.length!=0){
            for(int i=0;i<totalRates.length;i++){
                Rate+=totalRates[i];
            }
            Rate/=totalRates.length;
        }
        else Rate = 0;
    }
}
