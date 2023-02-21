package abdulrahman.alluqmani.HyViewerWeb;

import java.io.BufferedWriter;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.Instant;
import java.time.LocalDateTime;

public class Data {

    public static Path baseDir = FileSystems.getDefault().getPath(".").toAbsolutePath();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
    DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    public Data(){
        File theDir = new File(baseDir + "/Data");
        if (!theDir.exists()){
            theDir.mkdirs();
        }        
    }

    public static void save(String itemName, JSONObject json) throws IOException {
        File file = new File(baseDir + "/Data/" + itemName + ".txt");
        FileWriter myFile = new FileWriter(file, true);
        BufferedWriter out = new BufferedWriter(myFile);
        String content = filter(itemName, json);
        out.write("\n" + content);
        out.close();
    }

    public String get(String itemName, JSONObject json) throws IOException {
        return filter(itemName, json);
    }

    public String getReadable(String itemName, JSONObject json) throws IOException {
        return filterReadable(itemName, json);
    }


    public void save(String itemName, String data) throws IOException {

        
        LocalDateTime now = LocalDateTime.now();
        itemName = Helper.fixItemName(itemName);
        File file = new File(baseDir + "/Data/" + itemName + "_" + dtf.format(now) + ".txt");
        FileWriter myFile = new FileWriter(file, true);
        BufferedWriter out = new BufferedWriter(myFile);
        out.write(data);
        out.close();
    }

    public static void save(String data) throws IOException {

        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");


        File file = new File(baseDir + "/Data/general_" + dtf.format(now) + ".txt");
        FileWriter myFile = new FileWriter(file, true);
        BufferedWriter out = new BufferedWriter(myFile);
        out.write(data);
        out.close();
    }


    public static String filter(String itemName, JSONObject json) {
        itemName = Helper.fixItemName(itemName);

        JSONObject itemStatus = json.getJSONObject("products").getJSONObject(itemName).getJSONObject("quick_status");
        return json.getLong("lastUpdated") + ", " + itemStatus.get("buyVolume") + ", " + itemStatus.get("buyPrice");
    }


    // Makes it human readable, for web-ui viewing rather than computer orinated language.
    public String filterReadable(String itemName, JSONObject json) {
        JSONObject itemStatus = json.getJSONObject("products").getJSONObject(itemName).getJSONObject("quick_status");
        Instant instant = Instant.ofEpochMilli(json.getLong("lastUpfd"));
        System.out.println(json.getLong("lastUpdated"));
        Date date = Date.from(instant);

        return df.format(date) + ": " + itemStatus.get("buyVolume") + " quantity, $" + String.format("%,.2f", itemStatus.get("buyPrice")) + " each.";
    }

    public static String filterDate(String itemName, JSONObject json) {
        Instant instant = Instant.ofEpochMilli(json.getLong("lastUpdated"));
        Date date = Date.from(instant);
        DateFormat dfStatic = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        return dfStatic.format(date);
    }


    public static void writeToFile(String savingData) throws IOException {
        File file = new File("test.txt");
        FileWriter myFile = new FileWriter(file, true);
        BufferedWriter out = new BufferedWriter(myFile);
        out.write(savingData);
        out.close();
    }
    
    public static double filterPrice(String itemName, JSONObject json) {
        itemName = Helper.fixItemName(itemName);
        JSONObject itemStatus = json.getJSONObject("products").getJSONObject(itemName).getJSONObject("quick_status");
        return itemStatus.getDouble("buyPrice");
    }

    public static String filterPriceReadable(String itemName, JSONObject json) {
        itemName = Helper.fixItemName(itemName);
        JSONObject itemStatus = json.getJSONObject("products").getJSONObject(itemName).getJSONObject("quick_status");
        return "$" + String.format("%,.2f", itemStatus.get("buyPrice"));
    }
    
    public static ArrayList<String> readLastLine(File file, int numLastLineToRead) {

        ArrayList<String> result = new ArrayList<>();

        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8)) {

            String line = "";
            while ((line = reader.readLine()) != null && result.size() < numLastLineToRead) {
                result.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
