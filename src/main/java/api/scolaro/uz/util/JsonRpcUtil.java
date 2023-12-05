package api.scolaro.uz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;

/**
 * @author 'Mukhtarov Sarvarbek' on 05.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */

public class JsonRpcUtil {
    public static String toJson(Map<String, Object> res) {
        try {
            // Create ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert the Map to a JSON string
            String jsonString = objectMapper.writeValueAsString(res);

            // Print the resulting JSON string
            System.out.println(jsonString);

            return jsonString;
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static String mapToJsonString(Map<String, Object> map) {
        return """
                {
                "result": "%s",
                "id": %d,
                "jsonrpc": "%s"
                }
                """.formatted(map.get("result"), (Long) map.get("id"), map.get("jsonrpc"));
    }
}
