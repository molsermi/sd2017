public class RequestObject {
    private String service;
    private String beginDate;
    private String endDate;
    private String sym1;
    private String extra;
    private String exchange;

    public RequestObject(String service, String beginDate, String endDate, String sym1, String extra, String exchange) {
        this.service = service;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sym1 = sym1;
        this.extra = extra;
        this.exchange = exchange;
    }

    public String getService() {
        return service;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSym1() {
        return sym1;
    }

    public String getExtra() {
        return extra;
    }

    public String getExchange() {
        return exchange;
    }
}
