package abdulrahman.alluqmani.HyViewerWeb;

import java.io.IOException;

import org.json.JSONObject;

public class Helper {

    // Handle user input from the web-ui, so the api can use it with no issues.
    public static String fixItemName(String itemName){
        return itemName.toUpperCase().replace(" ", "_");
    }


    private JSONObject dataBlob;



    // public static String convertStringToUserfriendly(String name){
    //     return 
    // }
}
