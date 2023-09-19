package api.scolaro.uz.util;

import java.util.List;

public class StringUtil {
    public static String join(String delimiter, List<?> objectList) {
        if (objectList == null)
            return "";
        StringBuilder builder = new StringBuilder();
        int n = objectList.size();
        for (int i = 0; i < n; i++) {
            builder.append(objectList.get(i));
            if (i != n - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
