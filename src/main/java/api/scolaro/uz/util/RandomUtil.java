package api.scolaro.uz.util;

import java.util.Random;

public class RandomUtil {
    public static final String upper = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase();
    public static final String digits = "123456789";
    public static final String alphanum = lower + digits;


    public static String getRandomSmsCode() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(random.nextInt(8) + 1);
        }
        return builder.toString();
    }

    public static String getRandomString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int n = random.nextInt(alphanum.length());
            builder.append(alphanum.charAt(n));
        }
        return builder.toString();
    }
}
