/**
 * Created by CupsaA on 27-Apr-17.
 */
import OutputBuilder.JsonWrite.src.jsonwrite.JsonObjectValues;
import OutputBuilder.JsonWrite.src.jsonwrite.JsonWrite;
import fsega.distributedsystems.server.util.*;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.*;

public class OutputBuilderTests {
    @Test
    public void outputBuilderTest_Success() throws Exception {
        OutputBuilder outputBuilder = new OutputBuilder();
        outputBuilder.getJsonForParsedUrl("asd");
    }
}
