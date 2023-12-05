package api.scolaro.uz.controller.transaction;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/student/payme-callback")
    public ResponseEntity<String> fillBalance(@RequestBody PaymeCallBackRequestDTO body) {
        log.info("Fill student balance body = {}", body);
        return ResponseEntity.ok("""
                {
                "jsonrpc": "2.0", "result": {"isSuccess": true,"message":"Success!","code":200}, "id": %d
                }
                """.formatted(body.getId()));
    }
}
