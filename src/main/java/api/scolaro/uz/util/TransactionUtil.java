package api.scolaro.uz.util;

public class TransactionUtil {
    public static Long sumToTiyin(Long amountInSum) {
        return amountInSum * 100;
    }

    public static Long tiyinToSum(Long amountInTiyin) {
        return amountInTiyin / 100;
    }
}
