package api.scolaro.uz.controller.transaction;

import api.scolaro.uz.config.details.EntityDetails;
import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsAdminDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsConsultingDTO;
import api.scolaro.uz.dto.transaction.request.TransactionFilterAsStudentDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsAdminDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsConsultingDTO;
import api.scolaro.uz.dto.transaction.response.TransactionResponseAsStudentDTO;
import api.scolaro.uz.dto.transaction.response.payme.PaymeResponseDTO;
import api.scolaro.uz.service.transaction.TransactionService;
import api.scolaro.uz.util.CheckAuthorizationUtil;
import api.scolaro.uz.util.PaginationUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.IllegalFormatCodePointException;
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
    @Value("${payme.auth.token}")
    private String paymeAuthToken;

    @PostMapping("/student/fill-callback")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> createTransactionForFillBalance(@RequestParam("amount")
                                                                                               @Valid
                                                                                               @Min(value = 0, message = "Amount must not be less than 0")
                                                                                               Long amount) {
        String currentUserId = EntityDetails.getCurrentUserId();
        log.info("Create transaction for fill balance userId={}", currentUserId);
        return ResponseEntity.ok(transactionService.createTransactionForFillBalance(currentUserId, amount));
    }

    @PostMapping("/student/payme-callback")
    public ResponseEntity<PaymeResponseDTO> paymeCallback(@RequestBody PaymeCallBackRequestDTO body,
                                                          @RequestHeader(value = "Authorization", required = false) String authorization) {
        log.info("PaymeCallback body = {}", body);

        if (!CheckAuthorizationUtil.check(authorization, paymeAuthToken))
            return ResponseEntity
                    .ok(
                            CheckAuthorizationUtil
                                    .withoutAuthorizationHeader(
                                            body.getId(),
                                            body.getMethod(),
                                            body.getJsonrpc()
                                    )
                    );

        return ResponseEntity.ok(transactionService.callBackPayme(body));
    }


    @PostMapping("/filter/student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<PageImpl<TransactionResponseAsStudentDTO>> filterAsStudent(@RequestBody(required = false) TransactionFilterAsStudentDTO dto,
                                                                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                                                                     @RequestParam(value = "size", defaultValue = "50") int size) {
        log.info("Filter student");
        return ResponseEntity
                .ok(transactionService.filterAsStudent(
                        dto, PaginationUtil.page(page), size
                ));
    }

    @PostMapping("/filter/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PageImpl<TransactionResponseAsAdminDTO>> filterAsAdmin(@RequestBody(required = false) TransactionFilterAsAdminDTO dto,
                                                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                                                 @RequestParam(value = "size", defaultValue = "50") int size) {
        log.info("Filter admin");
        return ResponseEntity
                .ok(transactionService.filterAsAdmin(
                        dto, PaginationUtil.page(page), size
                ));
    }

    @PostMapping("/filter/consulting")
    @PreAuthorize("hasAnyRole('ROLE_CONSULTING','ROLE_CONSULTING_PROFILE')")
    public ResponseEntity<PageImpl<TransactionResponseAsConsultingDTO>> filterAsConsulting(@RequestBody(required = false) TransactionFilterAsConsultingDTO dto,
                                                                                           @RequestParam(value = "page", defaultValue = "1") int page,
                                                                                           @RequestParam(value = "size", defaultValue = "50") int size) {
        log.info("Filter consulting");
        return ResponseEntity
                .ok(transactionService.filterAsConsulting(
                        dto, PaginationUtil.page(page), size
                ));
    }
}