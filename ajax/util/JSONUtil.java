package by.galkina.game.ajax.util;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONUtil {
    /** Field COMMAND  */
    private static final String COMMAND = "command";
    private static final String SUM = "sum";

    /** Field UPDATE  */
    private static final String UPDATE = "update";

    /** Field ID  */
    private static final String ID = "id";

    /** Field TEXT  */
    private static final String TEXT = "text";


    /**
     * Constructor JSONUtil creates a new JSONUtil instance.
     */
    public JSONUtil() {}

    /**
     * Method getCommand ...
     *
     * @param json of type JSONObject
     * @return String
     */
    public static String getCommand(JSONObject json) {
        return (String) json.get(COMMAND);
    }

    /**
     * Method getUpdateType ...
     *
     * @param json of type JSONObject
     * @return String
     */
    public static String getUpdateType(JSONObject json) {
        return (String)json.get(UPDATE);
    }

    /**
     * Method stringToJson ...
     *
     * @param data of type String
     * @return JSONObject
     * @throws ParseException when
     */
    public static JSONObject stringToJson(String data) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        return (JSONObject) jsonParser.parse(data.trim());
    }

    /**
     * Method jsonToId ...
     *
     * @param json of type JSONObject
     * @return Long
     */
    public static Long jsonToId(JSONObject json) {
        return Long.parseLong((String)json.get(ID));
    }

    /**
     * Method jsonToReviewText ...
     *
     * @param json of type JSONObject
     * @return String
     */
    public static String jsonToReviewText(JSONObject json) {
        return ((String)json.get(TEXT)).trim();
    }

    public static String jsonToSum(JSONObject json) {
        return ((String) json.get(SUM)).trim();
    }

}
