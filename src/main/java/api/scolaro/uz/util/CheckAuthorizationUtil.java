package api.scolaro.uz.util;

import api.scolaro.uz.enums.jsonrpc.PaymeResponseStatus;

import java.util.Map;
import java.util.Optional;

/**
 * @author 'Mukhtarov Sarvarbek' on 05.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */

public class CheckAuthorizationUtil {

    public static boolean check(String authorization, String paymeAuthToken) {


        return Optional.ofNullable(authorization).isPresent()
                && !authorization.isBlank()
                && authorization.startsWith("Basic")
                && !authorization.substring("Basic".length()).equals(paymeAuthToken);
    }

    public static Map<String, Object> withoutAuthorizationHeader(Long id, String method, String jsonrpc) {
        return Map.of(
                "id", id,
                "jsonrpc", jsonrpc,
                "method", method,
                "error", Map.of(
                        "code", PaymeResponseStatus.NOT_ENOUGH_PRIVILEGES.getCode(),
                        "message", "Invalid params"
                )
        );
    }
}
