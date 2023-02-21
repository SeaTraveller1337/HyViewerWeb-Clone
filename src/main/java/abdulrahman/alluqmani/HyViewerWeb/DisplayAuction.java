package abdulrahman.alluqmani.HyViewerWeb;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.flow.component.html.Span;

public class DisplayAuction {
    String dataDate;
    String itemName;
    Span priceFormatted;
    double price;
    double averagePrice;
    String priceBoxColor;
    Data dataMachine = new Data();
    Request bazaarRequester;
    Request auctionRequester;

    public DisplayAuction(String itemName) throws Exception{
        bazaarRequester = new Request("bazaar");
        auctionRequester = new Request("auction");
        this.itemName = itemName;
        this.dataDate = Data.filterDate(itemName, Request.dataBlobAuction);
        this.averagePrice = getAverage();

    }

    public double getAverage() throws Exception{
        JSONObject auctionData = Request.dataBlobAuction;
        JSONArray auctions = auctionData.getJSONArray("auctions");
        int count = 0;
        ArrayList<JSONObject> listOfObjects = new ArrayList<>();

        for (int i = 0; i < auctions.length(); i++){
            listOfObjects.add(auctions.getJSONObject(i));
        }

        double averageBin = 0.0;

        for (JSONObject x: listOfObjects){
            if ((x.getString("item_name").toLowerCase()).equals((this.itemName.toLowerCase()))){
                if (x.getBoolean("bin") == true){
                    averageBin += (double) x.getInt("starting_bid");
                    count += 1;
                }
    
            }
        }
        System.out.println(averageBin);
        System.out.println(count);
        if(averageBin == 0 && count == 0){
            throw new Exception("Offer doesn't exist or doesn't have any BIN.");
        }
        return averageBin / count;
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

    public Span getPriceFormatted() {
        return priceFormatted;
    }

    public void setPriceFormatted(Span priceFormatted) {
        this.priceFormatted = priceFormatted;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }
    
}
