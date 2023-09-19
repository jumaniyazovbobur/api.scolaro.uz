package api.scolaro.uz.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {

    private String message;

    private Integer code;

    private Boolean isError;

    private T data;

    public ApiResponse() {
    }

    public ApiResponse(Integer code, Boolean isError) {
        this.code = code;
        this.isError = isError;
        this.data = null;
    }

    public ApiResponse(String message, Integer code, Boolean isError) {
        this.message = message;
        this.code = code;
        this.isError = isError;
        this.data = null;
    }

    public ApiResponse(String message, Integer code) {
        this.message = message;
        this.code = code;
        this.data = null;
    }

    public ApiResponse(Integer code, Boolean isError, T data) {
        this.code = code;
        this.isError = isError;
        this.data = data;
    }

    public ApiResponse(String message, Integer code, Boolean isError, T data) {
        this.message = message;
        this.code = code;
        this.isError = isError;
        this.data = data;
    }


    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<T>(200, false, data);
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<T>(200, false);
    }

    public static <T> ApiResponse<T> bad(String message) {
        return new ApiResponse<T>(message, 400, true);
    }
}
