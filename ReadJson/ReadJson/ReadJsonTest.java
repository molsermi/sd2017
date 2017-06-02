/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ReadJson;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONObject;
/**
 *
 * @author Roxana
 */
public class ReadJsonTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ReadJson test = new ReadJson();
                                   // symbol , startDate, endDate
        JSONObject obj= test.getJson("NOKIA.HE", "2016-01-02", "2017-01-02");
        test.parseJson(obj);
        ArrayList symbol = new ArrayList();
        symbol= test.getSymbol();
        System.out.println(symbol.get(1));
    }
    
}
