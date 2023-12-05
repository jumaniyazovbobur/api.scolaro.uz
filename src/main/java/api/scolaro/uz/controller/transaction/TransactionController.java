package api.scolaro.uz.controller.transaction;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 'Mukhtarov Sarvarbek' on 04.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction")
@Slf4j
public class TransactionController {


    @GetMapping("/student/payme-callback")
    public ResponseEntity<ApiResponse<?>> fillBalance(@RequestBody PaymeCallBackRequestDTO body) {
        log.info("Fill student balance body = {}",body);
        return ResponseEntity.ok(ApiResponse.ok(body));
    }
}
