/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReadJson;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.net.URL;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;



/**
 *
 * @author Roxana
 */
public class ReadJson 
{
    private ArrayList<String> symbol;
    private ArrayList<String> date;
    private ArrayList<String> open;
    private ArrayList<String> high;
    private ArrayList<String> low;
    private ArrayList<String> close;
    private ArrayList<String> volume;
    private ArrayList<String> adjClose;
    
    public ReadJson()
    {
        symbol = new ArrayList<String>();
        date = new ArrayList<String>();
        open = new ArrayList<String>();
        high = new ArrayList<String>();
        low = new ArrayList<String>();
        close = new ArrayList<String>();
        volume = new ArrayList<String>();
        adjClose = new ArrayList<String>();
    }
    
    public JSONObject getJson(String symbol, String start, String end) 
    { //get Json from yahoo finance
        
        String str = new String();
        try {
            String query = "select * from yahoo.finance.historicaldata where symbol = '" + symbol + "' and startDate = '" + start + "' and endDate = '" + end + "'";
            //encode query
            String uri = new URI(null, null, query, null).getRawPath();
             // build a URL
            uri = "http://query.yahooapis.com/v1/public/yql?q=" + uri + "&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
            //System.out.println(uri);
            URL yahoofUrl = new URL(uri); 
            // read from the URL
            try (Scanner scan = new Scanner(yahoofUrl.openStream())) {
                while (scan.hasNext()) {
                    str += scan.nextLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(ReadJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException | MalformedURLException e){
            System.out.println(e);
        }
         //build a JSON object
        JSONObject obj = new JSONObject(str);
        return obj;
    }
    
    public void parseJson(JSONObject obj)
    {
   
        JSONObject query = obj.getJSONObject("query");
        if(Arrays.toString(JSONObject.getNames(query)).contains("results")) {
            JSONObject results = query.getJSONObject("results"); 
            // get results
            if(Arrays.toString(JSONObject.getNames(results)).contains("quote")) {
                 //parse Results
                for (int i = 0; i < results.getJSONArray("quote").length(); i++) {
                    // check if the keys exist in the JsonObject and add to arrays the values                 
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("High")) {
                        high.add(results.getJSONArray("quote").getJSONObject(i).getString("High"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Low")) {
                        low.add(results.getJSONArray("quote").getJSONObject(i).getString("Low"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Volume")) {  
                        volume.add(results.getJSONArray("quote").getJSONObject(i).getString("Volume"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Symbol")) {  
                        symbol.add(results.getJSONArray("quote").getJSONObject(i).getString("Symbol"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Adj_Close")) {  
                       adjClose.add(results.getJSONArray("quote").getJSONObject(i).getString("Adj_Close"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Close")) {  
                        close.add(results.getJSONArray("quote").getJSONObject(i).getString("Close"));
                    }    
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Date")) {  
                        date.add(results.getJSONArray("quote").getJSONObject(i).getString("Date"));
                    } 
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Open")) {  
                       open.add(results.getJSONArray("quote").getJSONObject(i).getString("Open"));
                    }
                }
            }
        }
    }
  
    public ArrayList getHigh()
    {
        return high;
    }
    
    public ArrayList getLow()
    {
        return low;
    }
    
    public ArrayList getVolume()
    {
        return volume;
    }
    public ArrayList getSymbol()
    {
        return symbol;
    }
    
    public ArrayList getAdjClose()
    {
        return adjClose;
    }
    public ArrayList getClose()
    {
        return close;
    }
    public ArrayList getDate()
    {
        return date;
    }
    
    public ArrayList getOpen()
    {
        return open;
    }
    
    
}
