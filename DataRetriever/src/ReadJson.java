/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
	private ArrayList<DataEntry> objects;
    
    public ReadJson()
    {
        objects = new ArrayList<DataEntry>();
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
                	DataEntry entry = new DataEntry();
             
                    // check if the keys exist in the JsonObject and add to arrays the values                 
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("High")) {
                        entry.setHigh(results.getJSONArray("quote").getJSONObject(i).getString("High"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Low")) {
                        entry.setLow(results.getJSONArray("quote").getJSONObject(i).getString("Low"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Volume")) {  
                        entry.setVolume(results.getJSONArray("quote").getJSONObject(i).getString("Volume"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Symbol")) {  
                        entry.setSymbol(results.getJSONArray("quote").getJSONObject(i).getString("Symbol"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Adj_Close")) {  
                       entry.setAdjClose(results.getJSONArray("quote").getJSONObject(i).getString("Adj_Close"));
                    }
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Close")) {  
                        entry.setClose(results.getJSONArray("quote").getJSONObject(i).getString("Close"));
                    }    
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Date")) {  
                        entry.setDate(results.getJSONArray("quote").getJSONObject(i).getString("Date"));
                    } 
                    if(Arrays.toString(JSONObject.getNames(results.getJSONArray("quote").getJSONObject(i))).contains("Open")) {  
                       entry.setOpen(results.getJSONArray("quote").getJSONObject(i).getString("Open"));
                    }
                    
                    objects.add(entry);
                }
            }
        }
    }

	public ArrayList<DataEntry> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<DataEntry> objects) {
		this.objects = objects;
	}
}
