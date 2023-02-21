package abdulrahman.alluqmani.HyViewerWeb;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.Theme;


public class Displayer {
    private String dataDate;
    private String itemName;
    Span priceFormatted;
    double price;
    String priceBoxColor;
    int stat;
    boolean statState;
    Data dataMachine = new Data();
    Request bazaarRequester;
    Request auctionRequester;
    
    public Displayer(String itemName) throws IOException, JSONException{
        bazaarRequester = new Request("bazaar");
        auctionRequester = new Request("auction");
        this.itemName = Helper.fixItemName(itemName);
        this.price = Data.filterPrice(this.itemName, Request.dataBlob);
        this.dataDate = Data.filterDate(this.itemName, Request.dataBlob);
        Data.save(this.itemName, Request.dataBlob);

        switch (priceStatus(this.itemName, Request.dataBlob)){
            case 1:
                priceBoxColor = "success";
                break;
            case -1:
                priceBoxColor = "error";
                break;
            default:
                priceBoxColor = "contrast";   
        }

        this.priceFormatted = new Span (Data.filterPriceReadable(this.itemName, Request.dataBlob));
        this.priceFormatted.getElement().getThemeList().add("badge " + priceBoxColor);

    }
    public void updateDate(){
        this.dataDate = Data.filterDate(itemName, Request.dataBlob);
    }
    public int priceStatus(String itemName, JSONObject jsonobject){
        double itemPrice = Data.filterPrice(itemName, jsonobject);
        if (itemPrice > avgLast(itemName)){
            return 1;
        } else if (itemPrice < avgLast(itemName)){
            return -1;
        } else {
            return 0;
        }
    }

    public double avgLast(String itemName){
        File file = new File(Data.baseDir + "/Data/" + Helper.fixItemName(itemName) + ".txt");   
        ArrayList<String> lastLines = Data.readLastLine(file, 2);
        double average = 0.0;
        int count = 0;
        for (String x: lastLines){
            String[] bits = x.split("\\,");
            average += Double.parseDouble((bits[bits.length-1]).trim());
            count += 1;
        }
        return average / count;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPriceFormatted(Span priceFormatted) {
        this.priceFormatted = priceFormatted;
    }


    public Span getPriceFormatted() {
        return priceFormatted;
    }

    // public String getPriceFormattedToString() {
    //     return ;
    // }
    
}
