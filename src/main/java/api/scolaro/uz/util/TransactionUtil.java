package api.scolaro.uz.util;

public class TransactionUtil {
    public static Long sumToTiyin(Long amountInSum) {
        if(amountInSum == null)
            return null;
        return amountInSum * 100;
    }

    public static Long tiyinToSum(Long amountInTiyin) {
        if(amountInTiyin == null)
            return null;
        return amountInTiyin / 100;
    }
}
