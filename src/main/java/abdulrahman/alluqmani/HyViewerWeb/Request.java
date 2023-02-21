package abdulrahman.alluqmani.HyViewerWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Request {

    // These will never change.
    public final String API_BASE = "https://api.hypixel.net";

    // YOUR API KEY HERE (https://api.hypixel.net/#section/Authentication)
    private final String API_KEY = "";
    public final String API_BAZAAR = "/skyblock/bazaar";
    public final String API_AUCTION = "/skyblock/auctions";
    public static JSONObject dataBlob;
    public static JSONObject dataBlobAuction;
    public String itemName;
    public String url;
    public static boolean runningThread = false;

    // Make object with what it's supposed to be.
    public Request(String endpoint) throws IOException, JSONException {
        if (endpoint.toLowerCase().equals("bazaar")) {
            this.url = API_BASE + API_BAZAAR;
        } else if (endpoint.toLowerCase().equals("auction")){
            this.url = API_BASE + API_AUCTION;
        } else {
            this.url = endpoint;
        }
    }
    
    public Runnable infinityRunner() throws IOException, InterruptedException{
        runningThread = true;
        while (true){
            Request bazaarRequester = new Request("bazaar");
            Request auctionRequester = new Request("auction");
            dataBlob = bazaarRequester.getJson();
            dataBlobAuction = auctionRequester.getJsonAuction();
            System.out.println("looped");
            Thread.sleep(20000);
        }
    }

    public JSONObject getDataBlob(){
        return dataBlob;
    }

    public JSONObject data() throws IOException, JSONException {
        return getJson();
    }

    public String dataString() throws IOException, JSONException {
        return data().toString();
    }

    // Grab json from the object's current api, this method if for hypixel api only.
    public JSONObject getJson() throws IOException, JSONException {
        URL urlStream = new URL(this.url);
        HttpURLConnection con = (HttpURLConnection) urlStream.openConnection();

        con.setRequestProperty("API-Key", API_KEY);
        con.setDoOutput(true);
        con.setRequestMethod("GET");

        try ( InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }

    }

    public JSONObject getJsonAuction() throws IOException, JSONException {
        URL urlStream = new URL(API_BASE + API_AUCTION);
        HttpURLConnection con = (HttpURLConnection) urlStream.openConnection();

        con.setRequestProperty("API-Key", API_KEY);
        con.setDoOutput(true);
        con.setRequestMethod("GET");

        try ( InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }

    }
    
    // Construct data from buffer
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    // some sites respond with json array instead of json objects
    public JSONArray getArray(String url) throws IOException, JSONException {
        URL urlStream = new URL(url);
        HttpURLConnection con = (HttpURLConnection) urlStream.openConnection();

        con.setDoOutput(true);
        con.setRequestMethod("GET");

        try ( InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }

    }


    public String checkToken() throws JSONException, MalformedURLException, IOException {
        URL url = new URL("https://api.hypixel.net/key");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestProperty("API-Key", API_KEY);
        con.setDoOutput(true);
        con.setRequestMethod("GET");

        BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
        String jsonText = readAll(rd);

        JSONObject jsonData = new JSONObject(jsonText);

        return jsonData.toString();

    }

    public URI appendUri(String uri, String appendQuery) throws URISyntaxException {
        URI oldUri = new URI(uri);

        String newQuery = oldUri.getQuery();
        if (newQuery == null) {
            newQuery = appendQuery;
        } else {
            newQuery += "&" + appendQuery;
        }

        return new URI(oldUri.getScheme(), oldUri.getAuthority(),
                oldUri.getPath(), newQuery, oldUri.getFragment());
    }

    // public String specifiyItem(String itemName){
    //     Data.save(itemName.toUpperCase().replace(" ", "_"));
    // }
}
