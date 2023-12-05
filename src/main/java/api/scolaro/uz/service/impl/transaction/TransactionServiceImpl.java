package api.scolaro.uz.service.impl.transaction;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import api.scolaro.uz.dto.transaction.PaymeCallbackParamsDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.entity.transaction.TransactionsEntity;
import api.scolaro.uz.enums.jsonrpc.PaymeResponseStatus;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.enums.transaction.TransactionStatus;
import api.scolaro.uz.enums.transaction.TransactionType;
import api.scolaro.uz.repository.transaction.TransactionRepository;
import api.scolaro.uz.service.ProfileService;
import api.scolaro.uz.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author 'Mukhtarov Sarvarbek' on 04.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProfileService profileService;

    @Override
    public ApiResponse<TransactionResponseDTO> createTransactionForFillBalance(String currentUserId, Long amount) {
        TransactionsEntity transactions = new TransactionsEntity();
        transactions.setProfileId(currentUserId);
        transactions.setTransactionType(TransactionType.DEBIT);
        transactions.setAmount(amount);
        transactions.setStatus(TransactionStatus.CREATED);
        transactions.setProfileType(ProfileType.PROFILE);
        transactionRepository.save(transactions);

        return ApiResponse.ok(TransactionResponseDTO.toDTO(transactions));
    }

    /**
     * @param time               timestamp like this 1399114284039
     * @param transactionId      our transactionId
     * @param amount             in tiyinax
     * @param paymeTransactionId payme transactionId
     * @param res                response for payme
     * @param entity             transactionEntity
     */
    private void CreateTransaction(Long time, String transactionId, Long amount, String paymeTransactionId, Map<String, Object> res, TransactionsEntity entity) {
        log.info("create transaction time={},transactionId={},amount={},paymeTransactionId={}", time, transactionId, amount, paymeTransactionId);

        if (!Objects.equals(entity.getAmount(), amount)) {
            // todo transactionStatus
            log.warn("Invalid balance");
            res.put("error", Map.of("code", PaymeResponseStatus.INVALID_AMOUNT.getCode(), "message", "Invalid balance"));
            return;
        }

        Instant instant = entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant();

        res.put("result", Map.of(
                "transaction", transactionId,
                "create_time", instant.toEpochMilli(),
                "state", 1
        ));
    }

    private void PerformTransaction(Long performTime, String transactionId, String paymeTransactionId, Map<String, Object> res, TransactionsEntity entity) {
        log.info("PerformTransaction  performTime={},transactionId={},paymeTransactionId={}", performTime, transactionId, paymeTransactionId);

        profileService.fillStudentBalance(entity.getProfileId(), entity.getAmount());
        entity.setStatus(TransactionStatus.SUCCESS);
        entity.setPerformTime(LocalDateTime.now());
        transactionRepository.save(entity);

        Instant instant = entity.getPerformTime().atZone(ZoneId.systemDefault()).toInstant();

        res.put("result", Map.of(
                "transaction", transactionId,
                "perform_time", instant.toEpochMilli(),
                "state", 2
        ));
    }

    @Override
    public Map<String, Object> callBackPayme(PaymeCallBackRequestDTO body) {
        log.info("callBackPayme");
        PaymeCallbackParamsDTO params = body.getParams();
        Map<String, Object> res = new HashMap<>();
        res.put("id", body.getId());
        res.put("jsonrpc", body.getJsonrpc());
        res.put("method", body.getMethod());

        if (Optional.ofNullable(params).isEmpty()
                || body.getParams().getAccount().getOrDefault("order_id", "").equals("")
        ) {
            log.warn("Invalid params body = {}", body);
            PaymeResponseStatus invalidParams = PaymeResponseStatus.INVALID_PARAMS;
            res.put("error", Map.of("code", invalidParams.getCode(), "message", "Order number not found!"));
            return res;
        }

        String orderId = (String) body.getParams().getAccount().getOrDefault("order_id", "");

        Optional<TransactionsEntity> transactionOptional = transactionRepository.findById(orderId);

        if (transactionOptional.isEmpty()
                || !transactionOptional.get().getProfileType().equals(ProfileType.PROFILE)
                || !Objects.equals(transactionOptional.get().getAmount(), body.getParams().getAmount())) {
            // error param
            log.warn("Transaction not found or ProfileType invalid body = {}", body);
            PaymeResponseStatus invalidParams = PaymeResponseStatus.INVALID_AMOUNT;
            res.put("error", Map.of("code", invalidParams.getCode(), "message", "Invalid balance"));
            return res;
        }

        switch (body.getMethod()) {
            case "CreateTransaction" ->
                    CreateTransaction(params.getTime(), orderId, params.getAmount(), params.getId(), res, transactionOptional.get());
            case "PerformTransaction" -> {

            }
        }

        return res;
    }
}
