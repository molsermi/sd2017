import org.testng.annotations.DataProvider;

import java.util.Map;

/**
 * HERE CAN BE ADDED NEW VALID / INVALID HTTP REQUESTS WHICH WILL BE SIMULATED IN THE TEST CLASS
 */
public class DataProviderUtils {

    @DataProvider(name = "createValidRequest")
    public static Object[][] createValidRequest() {
        return new Object[][]{
                {new RequestObject("S1", "2012", "2016", "symbol1", "extra", "exchange")},
                {new RequestObject("S2", "2012", "2016", "abc123", "exatras", "200")},
                {new RequestObject("S1", "2016", "2017", "sasacsd", "extra", "exchange")},
                {new RequestObject("S2", "2017", "2017", "123456", "extra", "dsav")},
                {new RequestObject("S1", "2012", "2016", "symbol1", "extra", "exchange")},
                {new RequestObject("S1", "2012", "2016", "symbol1", "extra", "exchange")},
        };
    }

        @DataProvider(name = "createInvalidRequest")
        public static Object[][] createInvalidRequest() {
            return new Object[][]{
                    {new RequestObject("S3", "2012", "2016", "symbol1", "extra", "exchange")}, // wrong service
                    {new RequestObject("1", "2012", "2016", "abc123", "exatras", "200")}, // wrong service
                    {new RequestObject("S1", "2020", "2017", "dsac", "extra", "exchange")}, //begin date > current date
                    {new RequestObject("S2", "2012", "2011", "123456", "extra", "exchange")}, //begin date > end date
                    {new RequestObject("S1", "2012", "2020", "symbol1", "extra", "exchange")}, //end date > current date
                    {new RequestObject("S1", "2012m", "2017", "symbol1", "extra", "exchange")}, //start date contains caracters
                    {new RequestObject("S1", "2012", "2017a", "symbol1", "extra", "exchange")}, //end date contains caracters
                    {new RequestObject("S1", "2012", "2016", "", "extra", "exchange")}, //empty symbol1
                    {new RequestObject("S1", "2012", "2016", "lasld", "", "exchange")}, //empty extra
                    {new RequestObject("S1", "2012", "2016", "lasld", "extra", "")}, // empty exchange
                    {new RequestObject("", "2012", "2016", "lasld", "extra", "exch")}, // empty service
            };
    }
}

