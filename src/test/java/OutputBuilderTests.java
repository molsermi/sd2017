import OutputBuilder.JsonWrite.src.jsonwrite.JsonObjectValues;
import OutputBuilder.JsonWrite.src.jsonwrite.JsonWrite;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Created by CupsaA on 27-Apr-17.
 */
public class OutputBuilderTests {
    JsonWrite OutputBuilder = new JsonWrite();

    @Test
    public void OutputBuilder_Success() throws JSONException {
        OutputBuilder.jsonWriter();
        Assert.assertEquals(OutputBuilder.jsonWriter().getString("SERVICE_NAME"),JsonObjectValues.SERVICE_VERSION.value(),"The value for the SERVICE_NAME field was inccorect");
        Assert.assertEquals(OutputBuilder.jsonWriter().getString("VALUE"),JsonObjectValues.VALUE.value(),"The value for the VALUE filed was inccorect");
    }
}
