import java.util.Date;

public class News {
    String Description;
    String Title;
    Date date;
    static int TotalRates[];
    int Rate;
    //Categories Category;
    //Comments comment[];


    public News(String description, String title) {
        Description = description;
        Title = title;
        date =new Date() ;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Date getDate() {
        return date;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        if(TotalRates.length!=0){
            for(int i=0;i<TotalRates.length;i++){
                Rate+=TotalRates[i];
            }
            Rate/=TotalRates.length;
        }
        else
            Rate = 0;
    }
}
