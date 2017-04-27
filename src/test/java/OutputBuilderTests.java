import OutputBuilder.JsonWrite.src.jsonwrite.JsonWrite;
import org.json.JSONException;
import org.testng.annotations.*;

/**
 * Created by CupsaA on 27-Apr-17.
 */
public class OutputBuilderTests {
    private static final String jsonFilePath="D:\\Master an 1\\Sem 2\\Sisteme distribuite\\SD\\Proiect\\JsonWrite.json";
    JsonWrite OutputBuilder = new JsonWrite();


    @Test
    public void test() throws JSONException {
        OutputBuilder.jsonWriter();
    }
}
