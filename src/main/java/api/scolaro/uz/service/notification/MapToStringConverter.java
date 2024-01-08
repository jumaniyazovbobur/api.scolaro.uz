package api.scolaro.uz.service.notification;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 'Mukhtarov Sarvarbek' on 24.11.2023
 * @project api.solve.question
 * @contact @sarvargo
 */
@Converter
@Slf4j
public class MapToStringConverter implements AttributeConverter<Map<String, String>, String> {
    @Override
    public String convertToDatabaseColumn(Map<String, String> stringStringMap) {
        return stringStringMap.toString();
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String string) {
        try {
            return string != null ? parseString(string) : Map.of();
        } catch (Exception e) {
            log.warn("Error during parse string to map value={}", string);
            return Collections.emptyMap();
        }
    }

    public static Map<String, String> parseString(String input) {
        Map<String, String> resultMap = new HashMap<>();

        // Remove curly braces and split by commas
        String[] pairs = input.substring(1, input.length() - 1).split(", ");

        for (String pair : pairs) {
            // Split each pair into key and value
            String[] keyValue = pair.split("=");

            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                // Add key-value pair to the map
                resultMap.put(key, value);
            }
        }
        return resultMap;
    }
}
