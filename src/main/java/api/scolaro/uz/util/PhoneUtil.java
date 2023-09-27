package api.scolaro.uz.util;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
    private static final
    String PHONE_PATTERN = "(?:\\d{12})";

    public static boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);

        if (Optional.ofNullable(phone).isPresent()) {
            Matcher matcher = pattern.matcher(phone);
            return matcher.matches();
        }
        return false;
    }
}
