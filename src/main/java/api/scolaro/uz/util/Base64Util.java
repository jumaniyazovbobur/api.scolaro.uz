package api.scolaro.uz.util;

import java.util.Base64;

/**
 * @author 'Mukhtarov Sarvarbek' on 10.01.2024
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
public class Base64Util {
    // Encode a string to Base64
    public static String encodeBase64(String str) {
        byte[] encodedBytes = Base64.getEncoder().encode(str.getBytes());
        return new String(encodedBytes);
    }

    // Decode a Base64 string to the original string
    public static String decodeBase64(String base64Str) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Str);
        return new String(decodedBytes);
    }
}
