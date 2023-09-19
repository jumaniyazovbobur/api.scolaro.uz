package api.scolaro.uz.exp;

public class SmsLimitOverException extends RuntimeException {
    public SmsLimitOverException(String message) {
        super(message);
    }
}
