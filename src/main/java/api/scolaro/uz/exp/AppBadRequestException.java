package api.scolaro.uz.exp;

public class AppBadRequestException extends RuntimeException{
    public AppBadRequestException(String message){
        super(message);
    }
}
