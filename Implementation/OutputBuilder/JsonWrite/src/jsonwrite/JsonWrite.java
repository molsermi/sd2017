/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OutputBuilder.JsonWrite.src.jsonwrite;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.io.FileWriter;

import org.json.JSONException;

/**
 * @author Florin
 */
public class JsonWrite {
    private static final String jsonFilePath = "C:\\SD\\sd2017\\JsonWrite.json";

    public JSONObject jsonWriter() throws JSONException {
        // TODO code application logic here
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JsonObjectValues.FIELD_SERVICE_NAME.value(), JsonObjectValues.SERVICE_VERSION.value());
        jsonObject.put(JsonObjectValues.FIELD_VALUE.value(), JsonObjectValues.VALUE.value());

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(JsonObjectValues.FIELD_SERVICE_NAME.value() + ":" + JsonObjectValues.SERVICE_VERSION.value());
        jsonArray.put(JsonObjectValues.FIELD_VALUE.value() + ":" + JsonObjectValues.VALUE.value());
        jsonObject.put(JsonObjectValues.ACTION.value(), jsonArray);


        try {
            FileWriter jsonFileWriter = new FileWriter(jsonFilePath);
            jsonFileWriter.write(jsonObject.toString());
            jsonFileWriter.flush();
            jsonFileWriter.close();
            System.out.println(jsonObject);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
