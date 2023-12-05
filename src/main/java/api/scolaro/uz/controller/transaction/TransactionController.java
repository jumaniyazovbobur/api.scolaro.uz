package api.scolaro.uz.controller.transaction;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.service.transaction.TransactionService;
import api.scolaro.uz.util.JsonRpcUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    private final TransactionService transactionService;

    @PostMapping("/student/fill-callback")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> createTransactionForFillBalance(@RequestParam("amount")
                                                                                               @Valid
                                                                                               @Min(value = 0, message = "Amount must not be less than 0")
                                                                                               Long amount) {
        String currentUserId = EntityDetails.getCurrentUserId();
        log.info("Create transaction for fill balance userId={}", currentUserId);
        return ResponseEntity.ok(transactionService.createTransactionForFillBalance(currentUserId, amount));
    }

    @PostMapping("/student/payme-callback")
    public Map<String, Object> paymeCallback(@RequestBody PaymeCallBackRequestDTO body) {
        log.info("PaymeCallback body = {}", body);
        Map<String, Object> res = transactionService.callBackPayme(body); // return as jsonrpc
        String s = JsonRpcUtil.mapToJsonString(res);
        System.out.println(s);
        return res;
    }
}
