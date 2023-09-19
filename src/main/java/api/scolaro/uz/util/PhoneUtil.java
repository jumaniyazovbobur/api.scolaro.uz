package api.scolaro.uz.util;

public class PhoneUtil {
    public static boolean isValidPhone(String phone) {
        String pattern = "^998[0-9]{9}$";
        return phone.matches(pattern);
    }
}
