package api.scolaro.uz.repository.transaction;

import api.scolaro.uz.entity.transaction.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author 'Mukhtarov Sarvarbek' on 04.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
public interface TransactionRepository extends JpaRepository<TransactionsEntity, String> {
    Optional<TransactionsEntity> findTop1ByPaymeTransactionsId(String paymeTransactionsId);

    List<TransactionsEntity> findAllByCreatedDateBetweenAndPaymentType(LocalDateTime from, LocalDateTime to, String paymentType);
    List<TransactionsEntity> findAllByCreateTimeBetweenAndPaymentType(Long createTime, Long createTime2, String paymentType);
}
