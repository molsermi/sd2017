package OutputBuilder.JsonWrite.src.jsonwrite;

/**
 * Created by CupsaA on 27-Apr-17.
 */
public enum JsonObjectValues {
    FIELD_SERVICE_NAME("SERVICE_NAME"),
    FIELD_VALUE("VALUE"),
    SERVICE_VERSION("S1"),
    VALUE("3.47");

    private String value;

    JsonObjectValues(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
