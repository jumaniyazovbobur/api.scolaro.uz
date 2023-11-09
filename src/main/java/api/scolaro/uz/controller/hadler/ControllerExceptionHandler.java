package api.scolaro.uz.controller.hadler;


import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.exp.AppBadRequestException;
import api.scolaro.uz.exp.ItemNotFoundException;
import api.scolaro.uz.exp.SmsLimitOverException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<ApiResponse<?>> handlerException(ItemNotFoundException e) {
        return ResponseEntity.ok(new ApiResponse<>(404, true, e.getMessage()));
    }

    @ExceptionHandler({AppBadRequestException.class})
    public ResponseEntity<ApiResponse<?>> handlerException(AppBadRequestException e) {
        return ResponseEntity.ok(new ApiResponse<>(400, true, e.getMessage()));
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ApiResponse<?>> handler(MethodNotAllowedException exp) {
        return ResponseEntity.ok(new ApiResponse<>(405, true, exp.getMessage()));
    }

//    @ExceptionHandler(UnAuthorizedException.class)
//    public ResponseEntity<String> handler(UnAuthorizedException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//    }
//
//    @ExceptionHandler(AppMethodNotAllowedException.class)
//    public ResponseEntity<String> handler(AppMethodNotAllowedException e) {
//        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handler(AccessDeniedException e) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.METHOD_NOT_ALLOWED.value(), true, e.getMessage()));
    }

    @ExceptionHandler(SmsLimitOverException.class)
    public ResponseEntity<ApiResponse<?>> handler(SmsLimitOverException e) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.METHOD_NOT_ALLOWED.value(), true, e.getMessage()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handlerException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.ok(new ApiResponse<>(500, true, e.getMessage()));
    }

}
