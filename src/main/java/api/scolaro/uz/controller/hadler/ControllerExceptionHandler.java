package api.scolaro.uz.controller.hadler;

import api.dean.db.exp.AppBadRequestException;
import api.dean.db.exp.ItemNotFoundException;
import api.dean.db.exp.SmsLimitOverException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MethodNotAllowedException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<?> handlerException(ItemNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({AppBadRequestException.class})
    public ResponseEntity<?> handlerException(AppBadRequestException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<?> handler(MethodNotAllowedException exp) {
        return ResponseEntity.badRequest().body(exp.getMessage());
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
    public ResponseEntity<String> handler(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler(SmsLimitOverException.class)
    public ResponseEntity<String> handler(SmsLimitOverException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handlerException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
