import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;

public class ServerRequestTest {

    // DO NOT FORGET TO START THE SERVER BEFORE RUNNING THE TESTS

    /**
     * Invalid requests -> expected result 404
     * !!! Some of the test will fail (request will be valid - 200 although it should be 404 - not found). This is because the functionality of the server is not fully implemented !!!
     * New scenarios can be added to the Data Provider (DataProviderUtils.createInvalidRequest)
     */
    @Test(alwaysRun = true, dataProviderClass = DataProviderUtils.class, dataProvider = "createInvalidRequest")
    public void sendInvalidRequest(RequestObject requestObject) throws IOException {
        HttpClientReq httpClientReq = new HttpClientReq();
        int responseCode = httpClientReq.sendGet(requestObject.getService(), requestObject.getBeginDate(), requestObject.getEndDate(), requestObject.getSym1(), requestObject.getExtra(), requestObject.getExchange());
        Assert.assertEquals(responseCode, 404, "The request was successful");
    }

    /**
     *     Valid requests -> expcted result : 200
     *     New scenarios can be added to the Data Provider (DataProviderUtils.createValidRequest)
     */
    @Test(alwaysRun = true, dataProviderClass = DataProviderUtils.class, dataProvider = "createValidRequest")
    public void sendValidRequest(RequestObject requestObject) throws IOException {
        HttpClientReq httpClientReq = new HttpClientReq();
        int responseCode = httpClientReq.sendGet(requestObject.getService(), requestObject.getBeginDate(), requestObject.getEndDate(), requestObject.getSym1(), requestObject.getExtra(), requestObject.getExchange());
        Assert.assertEquals(responseCode, 200, "The request was not successful");

    }
}
