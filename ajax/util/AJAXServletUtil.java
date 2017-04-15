package by.galkina.game.ajax.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class AJAXServletUtil ...
 *
 * @author Виталий
 * Created on 19.06.2016
 */
public class AJAXServletUtil {
    /** Field APPLICATION_JSON  */
    public static final String APPLICATION_JSON = "application/json";

    /** Field UTF_8  */
    public static final String UTF_8 = "UTF-8";


    /**
     * Method getRequestBody ...
     *
     * @param request of type HttpServletRequest
     * @return String
     * @throws java.io.IOException when
     */
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}
