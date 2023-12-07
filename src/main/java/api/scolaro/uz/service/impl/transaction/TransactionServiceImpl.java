package api.scolaro.uz.service.impl.transaction;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import api.scolaro.uz.dto.transaction.PaymeCallbackParamsDTO;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.entity.transaction.TransactionsEntity;
import api.scolaro.uz.enums.jsonrpc.PaymeResponseStatus;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.enums.transaction.TransactionState;
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

import static api.scolaro.uz.enums.jsonrpc.PaymeResponseStatus.*;

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
    private final Long time_expired = 43_200_000L;

    @Override
    public ApiResponse<TransactionResponseDTO> createTransactionForFillBalance(String currentUserId, Long amount) {
        TransactionsEntity transactions = new TransactionsEntity();
        transactions.setProfileId(currentUserId);
        transactions.setTransactionType(TransactionType.DEBIT);
        transactions.setAmount(amount);
        transactions.setStatus(TransactionStatus.CREATED);
        transactions.setProfileType(ProfileType.PROFILE);
        transactions.setState(TransactionState.STATE_IN_PROGRESS);
        transactionRepository.save(transactions);

        return ApiResponse.ok(TransactionResponseDTO.toDTO(transactions));
    }

    private boolean isExpiredTime(Long createdTime, Map<String, Object> res, TransactionsEntity entity) {
        if (System.currentTimeMillis() - createdTime > time_expired) {
            log.warn("time expired id = {}", entity.getId());
            entity.setState(TransactionState.STATE_CANCELED);
            transactionRepository.save(entity);
            res.put("error", Map.of(
                    "code", INVALID_STATE.getCode(),
                    "message", "Invalid state"
            ));
            return false;
        }
        return true;
    }

    private TransactionsEntity isExistTransactionById(String transactionId, Map<String, Object> res) {
        Optional<TransactionsEntity> transactionOptional = transactionRepository.findById(transactionId);

        if (transactionOptional.isEmpty()) {
            // error param
            log.warn("Transaction not found id = {}", transactionId);
            res.put("error", Map.of(
                    "code", PaymeResponseStatus.TRANSACTION_NOT_FOUND.getCode(),
                    "message", "Transaction Not Found"
            ));
            return null;
        }
        return transactionOptional.get();
    }

    private TransactionsEntity isExistTransactionByPaymeId(String transactionId, Map<String, Object> res) {
        Optional<TransactionsEntity> transactionOptional = transactionRepository.findTop1ByPaymeTransactionsId(transactionId);
        if (transactionOptional.isEmpty()) {
            log.warn("Transaction not found id = {}", transactionId);
            PaymeResponseStatus invalidParams = PaymeResponseStatus.TRANSACTION_NOT_FOUND;
            res.put("error", Map.of("code", invalidParams.getCode(), "message", "Transaction Not Found"));
            return null;
        }
        return transactionOptional.get();
    }

    private boolean isEmptyOrderInAccount(PaymeCallbackParamsDTO params, Map<String, Object> res) {
        if (Optional.ofNullable(params).isEmpty()
                || params.getAccount().getOrDefault("order_id", "").equals("")
        ) {
            log.warn("Invalid params body = {}", params);
            res.put("error", Map.of(
                    "code", PaymeResponseStatus.INVALID_PARAMS.getCode(),
                    "message", "Order number not found!"
            ));
            return true;
        }
        return false;
    }

    /**
     * @param res            response for payme
     * @param amount         in tiyinax from payme
     * @param amountMerchant in tiyinax from merchant
     */
    private void CheckPerformTransaction(Map<String, Object> res, Long amount, Long amountMerchant) {
        log.info("CheckPerformTransaction amount={},amount merchant={}", amount, amountMerchant);
        if (!Objects.equals(amountMerchant, amount)) {
            log.warn("CheckPerformTransaction invalid amount");
            res.put("error", Map.of(
                    "code", INVALID_AMOUNT.getCode(),
                    "message", "Invalid Amount"
            ));
            return;
        }

        res.put("result", Map.of(
                "allow", true
        ));
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
        Instant instant = entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant();

        if (!entity.getState().equals(TransactionState.STATE_IN_PROGRESS)) {
            log.warn("CreateTransaction error invalid state merchantState={}", entity.getState());
            res.put("error", Map.of(
                    "code", INVALID_STATE.getCode(),
                    "message", "Invalid state"
            ));
            return;
        }
        // When subtracting the created time from the current time, an error will occur if the time_expired time is greater than the time_expired time
        if (isExpiredTime(instant.toEpochMilli(), res, entity)) {
            entity.setState(TransactionState.STATE_CANCELED);
            entity.setReason("4");
            transactionRepository.save(entity);
            return;
        }

        entity.setPaymeTransactionsId(paymeTransactionId);
        transactionRepository.save(entity);

        res.put("result", Map.of(
                "transaction", transactionId,
                "create_time", instant.toEpochMilli(),
                "state", entity.getState().getValue()
        ));
    }

    /**
     * @param res    response for payme
     * @param entity transactionEntity
     */
    private void PerformTransaction(Map<String, Object> res, TransactionsEntity entity) {
        log.info("PerformTransaction  transactionId={},paymeTransactionId={}", entity.getId(), entity.getPaymeTransactionsId());
        Instant instant = entity.getPerformTime().atZone(ZoneId.systemDefault()).toInstant();

        if (!entity.getState().equals(TransactionState.STATE_IN_PROGRESS)) {
            if (entity.getState().equals(TransactionState.STATE_DONE)) {
                res.put("result", Map.of(
                        "transaction", entity.getId(),
                        "perform_time", instant.toEpochMilli(),
                        "state", entity.getState().getValue()
                ));
                return;
            }
            log.warn("PerformTransaction time expired ");
            entity.setState(TransactionState.STATE_CANCELED);
            transactionRepository.save(entity);
            res.put("error", Map.of(
                    "code", INVALID_STATE.getCode(),
                    "message", "Invalid state"
            ));
            return;
        }

        // When subtracting the created time from the current time, an error will occur if the time_expired time is greater than the time_expired time
        if (isExpiredTime(instant.toEpochMilli(), res, entity)) return;

        profileService.fillStudentBalance(entity.getProfileId(), entity.getAmount());
        entity.setStatus(TransactionStatus.SUCCESS);
        entity.setPerformTime(LocalDateTime.now());
        entity.setState(TransactionState.STATE_DONE);
        transactionRepository.save(entity);


        res.put("result", Map.of(
                "transaction", entity.getId(),
                "perform_time", instant.toEpochMilli(),
                "state", entity.getState().getValue()
        ));
    }

    private void CancelTransaction(String reason, Map<String, Object> res, TransactionsEntity entity) {
        log.info("CancelTransaction id = {}", entity.getId());

        if (Objects.requireNonNull(entity.getState()) == TransactionState.STATE_DONE) {
            String profileId = entity.getProfileId();
            if (!profileService.checkBalance(profileId, entity.getAmount())) {
                log.warn("Profile amount not enough profileId={},id={}", profileId, entity.getId());
                res.put("error", Map.of(
                        "code", COULD_NOT_CANCEL.getCode(),
                        "message", "Not enough balance"
                ));
                return;
            }
            profileService.reduceFromBalance(profileId, entity.getAmount());
            entity.setState(TransactionState.STATE_POST_CANCELED);
        } else {
            entity.setState(TransactionState.STATE_CANCELED);
        }
        entity.setStatus(TransactionStatus.CANCELED);

        entity.setCancelTime(LocalDateTime.now());
        entity.setReason(reason);
        transactionRepository.save(entity);
        res.put("result", Map.of(
                "transaction", entity.getId(),
                "cancel_time", entity.getCancelTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                "state", entity.getState().getValue()
        ));
    }

    private void CheckTransaction(Map<String, Object> res, TransactionsEntity entity) {
        Long createdDateMilli = entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long performTimeMilli = entity.getPerformTime() == null ? 0L : entity.getPerformTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long cancelTimeMilli = entity.getCancelTime() == null ? 0L : entity.getCancelTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        res.put("result", Map.of(
                "create_time", createdDateMilli,
                "perform_time", performTimeMilli,
                "cancel_time", cancelTimeMilli,
                "transaction", entity.getId(),
                "state", entity.getState().getValue(),
                "reason", entity.getReason()
        ));
    }

    @Override
    public Map<String, Object> callBackPayme(PaymeCallBackRequestDTO body) {
        log.info("callBackPayme");
        Map<String, Object> res = new HashMap<>();
        res.put("id", body.getId());
        res.put("jsonrpc", body.getJsonrpc());
        res.put("method", body.getMethod());

        PaymeCallbackParamsDTO params = body.getParams();

        switch (body.getMethod()) {
            case "CheckPerformTransaction" -> {

                if (isEmptyOrderInAccount(params, res)) return res;

                String orderId = (String) body.getParams().getAccount().getOrDefault("order_id", "");

                TransactionsEntity transaction = isExistTransactionById(orderId, res);

                if (transaction == null) return res;

                CheckPerformTransaction(res, params.getAmount(), transaction.getAmount());
            } // done
            case "CreateTransaction" -> {
                if (isEmptyOrderInAccount(params, res)) return res;

                String orderId = (String) body.getParams().getAccount().getOrDefault("order_id", "");

                TransactionsEntity transaction = isExistTransactionById(orderId, res);

                if (transaction == null) return res;
                CreateTransaction(params.getTime(), orderId, params.getAmount(), params.getId(), res, transaction);
            }
            case "PerformTransaction" -> {
                TransactionsEntity transaction = isExistTransactionByPaymeId(params.getId(), res);
                if (transaction == null) return res;
                PerformTransaction(res, transaction);
            }
            case "CancelTransaction" -> {
                TransactionsEntity transaction = isExistTransactionByPaymeId(params.getId(), res);
                if (transaction == null) return res;
                CancelTransaction(params.getReason(), res, transaction);
            }
            case "CheckTransaction" -> {
                TransactionsEntity transaction = isExistTransactionByPaymeId(params.getId(), res);
                if (transaction == null) return res;
                CheckTransaction(res, transaction);
            }
            default -> {
                res.put("error", Map.of(

                ));
            }
        }

        return res;
    }
}
