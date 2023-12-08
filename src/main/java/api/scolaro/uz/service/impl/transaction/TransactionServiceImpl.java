package api.scolaro.uz.service.impl.transaction;

import api.scolaro.uz.dto.ApiResponse;
import api.scolaro.uz.dto.transaction.PaymeCallBackRequestDTO;
import api.scolaro.uz.dto.transaction.PaymeCallbackParamsDTO;
import api.scolaro.uz.dto.transaction.TransactionResForPayme;
import api.scolaro.uz.dto.transaction.TransactionResponseDTO;
import api.scolaro.uz.dto.transaction.response.payme.*;
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
import java.util.List;
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
        transactions.setPaymentType("PAYME");
        transactionRepository.save(transactions);

        return ApiResponse.ok(TransactionResponseDTO.toDTO(transactions));
    }

    private boolean isExpiredTime(Long createdTime, PaymeResponseDTO res, TransactionsEntity entity) {
        if (System.currentTimeMillis() - createdTime > time_expired) {
            log.warn("time expired id = {}", entity.getId());
            res.setError(new PaymeResponseErrorDTO(
                    INVALID_STATE.getCode(),
                    "Invalid state"
            ));
            return true;
        }
        return false;
    }

    private TransactionsEntity isExistTransactionById(String transactionId, PaymeResponseDTO res) {
        Optional<TransactionsEntity> transactionOptional = transactionRepository.findById(transactionId);

        if (transactionOptional.isEmpty()) {
            // error param
            log.warn("Transaction not found id = {}", transactionId);
            res.setError(
                    new PaymeResponseErrorDTO(
                            TRANSACTION_NOT_FOUND.getCode(),
                            "Transaction not found!"
                    )
            );
            return null;
        }
        return transactionOptional.get();
    }

    private TransactionsEntity isExistTransactionByPaymeId(String transactionId, PaymeResponseDTO res) {
        Optional<TransactionsEntity> transactionOptional = transactionRepository.findTop1ByPaymeTransactionsId(transactionId);
        if (transactionOptional.isEmpty()) {
            log.warn("Transaction not found id = {}", transactionId);
            res.setError(
                    new PaymeResponseErrorDTO(
                            TRANSACTION_NOT_FOUND.getCode(),
                            "Transaction not found"
                    )
            );
            return null;
        }
        return transactionOptional.get();
    }

    private boolean isEmptyOrderInAccount(PaymeCallbackParamsDTO params, PaymeResponseDTO res) {
        if (Optional.ofNullable(params).isEmpty()
        ) {
            log.warn("Invalid params ");
            res.setError(new PaymeResponseErrorDTO(
                    PaymeResponseStatus.INVALID_PARAMS.getCode(),
                    "Order number not found!"
            ));
            return true;
        }
        if (params.getAccount().getOrDefault("order_id", "").equals("")) {
            log.warn("NOT_ENOUGH_PRIVILEGES body = {}", params);
            res.setError(
                    new PaymeResponseErrorDTO(
                            NOT_ENOUGH_PRIVILEGES.getCode(),
                            "Invalid params"
                    )
            );
            return true;
        }
        return false;
    }

    /**
     * @param res            response for payme
     * @param amount         in tiyinax from payme
     * @param amountMerchant in tiyinax from merchant
     */
    private void CheckPerformTransaction(PaymeResponseDTO res, Long amount, Long amountMerchant) {
        log.info("CheckPerformTransaction amount={},amount merchant={}", amount, amountMerchant);
        if (!Objects.equals(amountMerchant, amount)) {
            log.warn("CheckPerformTransaction invalid amount");
            res.setError(
                    new PaymeResponseErrorDTO(
                            INVALID_AMOUNT.getCode(),
                            "Invalid amount"
                    )
            );
            return;
        }

        res.setResult(
                new CheckPerformTransactionResult(true)
        );
    }

    /**
     * @param time               timestamp like this 1399114284039
     * @param transactionId      our transactionId
     * @param amount             in tiyinax
     * @param paymeTransactionId payme transactionId
     * @param res                response for payme
     * @param entity             transactionEntity
     */
    private void CreateTransaction(Long time, String transactionId, Long amount, String paymeTransactionId, PaymeResponseDTO res, TransactionsEntity entity) {
        log.info("create transaction time={},transactionId={},amount={},paymeTransactionId={}", time, transactionId, amount, paymeTransactionId);
        Instant instant = entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant();

        if (!Objects.equals(amount, entity.getAmount())) {
            log.warn("Invalid amount {}!={}", amount, entity.getAmount());
            res.setError(
                    new PaymeResponseErrorDTO(
                            INVALID_AMOUNT.getCode(),
                            "Invalid amount"
                    )
            );
            return;
        }

        if (!entity.getState().equals(TransactionState.STATE_IN_PROGRESS)) {
            log.warn("CreateTransaction error invalid state merchantState={}", entity.getState());
            res.setError(
                    new PaymeResponseErrorDTO(
                            INVALID_STATE.getCode(),
                            "Invalid state"
                    )
            );
            return;
        }
        // When subtracting the created time from the current time, an error will occur if the time_expired time is greater than the time_expired time
        if (isExpiredTime(time, res, entity)) {
            entity.setState(TransactionState.STATE_CANCELED);
            entity.setReason("4");
            transactionRepository.save(entity);
            return;
        }

        entity.setCreateTime(time);
        entity.setPaymeTransactionsId(paymeTransactionId);
        transactionRepository.save(entity);

        res.setResult(
                new CreateTransactionResult(
                        transactionId,
                        instant.toEpochMilli(),
                        entity.getState().getValue()
                )
        );
    }

    /**
     * @param res    response for payme
     * @param entity transactionEntity
     */
    private void PerformTransaction(PaymeResponseDTO res, TransactionsEntity entity) {
        log.info("PerformTransaction  transactionId={},paymeTransactionId={}", entity.getId(), entity.getPaymeTransactionsId());

        if (!entity.getState().equals(TransactionState.STATE_IN_PROGRESS)) {
            if (entity.getState().equals(TransactionState.STATE_DONE)) {
                Instant instant = entity.getPerformTime().atZone(ZoneId.systemDefault()).toInstant();
                res.setResult(
                        new PerformTransactionResult(
                                entity.getId(),
                                instant.toEpochMilli(),
                                entity.getState().getValue()
                        )
                );
                return;
            }
            log.warn("PerformTransaction time expired ");
            entity.setState(TransactionState.STATE_CANCELED);
            transactionRepository.save(entity);
            res.setError(
                    new PaymeResponseErrorDTO(
                            INVALID_STATE.getCode(),
                            "Invalid code"
                    )
            );
            return;
        }

        // When subtracting the created time from the current time, an error will occur if the time_expired time is greater than the time_expired time
        if (isExpiredTime(entity.getCreateTime(), res, entity)) {
            entity.setState(TransactionState.STATE_CANCELED);
            entity.setReason("4");
            transactionRepository.save(entity);
            return;
        }

        profileService.fillStudentBalance(entity.getProfileId(), entity.getAmount());
        entity.setStatus(TransactionStatus.SUCCESS);
        entity.setPerformTime(LocalDateTime.now());
        entity.setState(TransactionState.STATE_DONE);
        transactionRepository.save(entity);

        Instant instant = entity.getPerformTime().atZone(ZoneId.systemDefault()).toInstant();

        res.setResult(
                new PerformTransactionResult(
                        entity.getId(),
                        instant.toEpochMilli(),
                        entity.getState().getValue()
                )
        );
    }

    private void CancelTransaction(String reason, PaymeResponseDTO res, TransactionsEntity entity) {
        log.info("CancelTransaction id = {}", entity.getId());

        if (Objects.requireNonNull(entity.getState()) == TransactionState.STATE_DONE) {
            String profileId = entity.getProfileId();
            if (!profileService.checkBalance(profileId, entity.getAmount())) {
                log.warn("Profile amount not enough profileId={},id={}", profileId, entity.getId());
                res.setError(
                        new PaymeResponseErrorDTO(
                                COULD_NOT_CANCEL.getCode(),
                                "Not enough balance"
                        )
                );
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
        res.setResult(
                new CancelTransactionResult(
                        entity.getId(),
                        entity.getCancelTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                        entity.getState().getValue()
                )
        );
    }

    private void CheckTransaction(PaymeResponseDTO res, TransactionsEntity entity) {
        Long createdDateMilli = entity.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long performTimeMilli = entity.getPerformTime() == null ? 0L : entity.getPerformTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long cancelTimeMilli = entity.getCancelTime() == null ? 0L : entity.getCancelTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        res.setResult(
                new CheckTransactionResult(
                        createdDateMilli,
                        performTimeMilli,
                        cancelTimeMilli,
                        entity.getId(),
                        entity.getState().getValue(),
                        entity.getReason()
                )
        );
    }

    @Override
    public PaymeResponseDTO callBackPayme(PaymeCallBackRequestDTO body) {
        log.info("callBackPayme");
        PaymeResponseDTO response = new PaymeResponseDTO();
        response.setId(body.getId());
        response.setJsonrpc(body.getJsonrpc());
        response.setMethod(body.getMethod());

        PaymeCallbackParamsDTO params = body.getParams();

        switch (body.getMethod()) {
            case "CheckPerformTransaction" -> {

                if (isEmptyOrderInAccount(params, response)) return response;

                String orderId = (String) body.getParams().getAccount().getOrDefault("order_id", "");

                TransactionsEntity transaction = isExistTransactionById(orderId, response);

                if (transaction == null) return response;

                CheckPerformTransaction(response, params.getAmount(), transaction.getAmount());
            } // done
            case "CreateTransaction" -> {
                if (isEmptyOrderInAccount(params, response)) return response;

                String orderId = (String) body.getParams().getAccount().getOrDefault("order_id", "");

                TransactionsEntity transaction = isExistTransactionById(orderId, response);

                if (transaction == null) return response;
                CreateTransaction(params.getTime(), orderId, params.getAmount(), params.getId(), response, transaction);
            }       // done
            case "PerformTransaction" -> {
                TransactionsEntity transaction = isExistTransactionByPaymeId(params.getId(), response);
                if (transaction == null) return response;
                PerformTransaction(response, transaction);
            }      // done
            case "CancelTransaction" -> {
                if (Optional.ofNullable(params.getReason()).isEmpty()
                        || Optional.ofNullable(params.getId()).isEmpty()) {
                    log.warn("CancelTransaction invalid params");
                    response.setError(PaymeResponseErrorDTO.NOT_ENOUGH_PRIVILEGES());
                    return response;
                }
                TransactionsEntity transaction = isExistTransactionByPaymeId(params.getId(), response);
                if (transaction == null) return response;

                CancelTransaction(params.getReason(), response, transaction);
            }       // done
            case "CheckTransaction" -> {
                TransactionsEntity transaction = isExistTransactionByPaymeId(params.getId(), response);
                if (transaction == null) return response;
                CheckTransaction(response, transaction);
            }
            case "GetStatement" -> {
                if (Optional.ofNullable(params.getTo()).isEmpty() || Optional.ofNullable(params.getFrom()).isEmpty()) {
                    log.warn("CancelTransaction invalid params");
                    response.setResult(PaymeResponseErrorDTO.NOT_ENOUGH_PRIVILEGES());
                    return response;
                }

                List<TransactionResForPayme> listForResponse = transactionRepository
                        .findAllByCreateTimeBetweenAndPaymentType(
                                params.getFrom(),
                                params.getTo(),
                                "PAYME"
                        )
                        .stream()
                        .map(TransactionResForPayme::toDTO)
                        .toList();

                response.setResult(
                        new GetStatementResult(listForResponse)
                );
            }
            default -> response.setError(
                    new PaymeResponseErrorDTO(
                            -32601,
                            "Method not found!"
                    )
            );
        }

        return response;
    }
}
