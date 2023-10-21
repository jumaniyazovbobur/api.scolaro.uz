package api.scolaro.uz.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class MapperUtil {
    public static Boolean getVisibleValue(Object o) {
        return o == null ? null : (Boolean) o;
    }

    public static String getStringValue(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    public static Double getDoubleValue(Object o) {
        return o == null ? null : (Double) o;
    }

    public static Long getLongValue(Object o) {
        return o == null ? null : (Long) o;
    }

    public static Integer getIntegerValue(Object o) {
        return o == null ? null : (Integer) o;
    }

    public static LocalDateTime getDateValue(Object o) {
        return o == null ? null : (LocalDateTime) o;
    }

    public static LocalDate getLocalDateValue(Object o) {
        return o == null ? null : (LocalDate) o;
    }

    public static LocalDateTime getLocalDateTime(Object o) {
        String dateTime = getStringValue(o);
        if (dateTime == null) return null;
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS");
        return LocalDateTime.parse(dateTime, ofPattern);
    }
}
