/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonwrite;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.FileWriter;
import org.json.JSONException;
/**
 *
 * @author Florin
 */
public class JsonWrite {
    private static final String jsonFilePath="D:\\Master an 1\\Sem 2\\Sisteme distribuite\\SD\\Proiect\\JsonWrite.json";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JSONException {
        // TODO code application logic here
        JSONObject jsonObject = new JSONObject();
        jsonObject .put("SERVICE_NAME:", "S1");
        jsonObject .put("VALUE:", "3.47");
        
        
        
        
        try{
            FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
            jsonFileWriter.write(jsonObject.toString());
            jsonFileWriter.flush();
            jsonFileWriter.close();
            System.out.println(jsonObject);
        
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
