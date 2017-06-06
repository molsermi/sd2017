import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClientReq {



    public int sendGet(String service, String beginDate, String endDate, String sym1, String extra, String exchange) throws IOException {

        StringBuilder stringBuilder = new StringBuilder("http://localhost:8080/");
        stringBuilder.append(service);
        stringBuilder.append("?begindate=" + beginDate);
        stringBuilder.append("&enddate=" + endDate);
        stringBuilder.append("&symbol1=" + sym1);
        stringBuilder.append("&extra=" + extra);
        stringBuilder.append("&exchange=" + exchange);

        URL url = new URL(stringBuilder.toString());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");

        System.out.println("\nSending request to URL : " + url);
        System.out.println("Response Code : " + connection.getResponseCode());
        System.out.println("Response Message : " + connection.getResponseMessage());

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            System.out.println(response.toString());
        }catch (IOException exception){
            return connection.getResponseCode();
        }
        return connection.getResponseCode();
    }
}
