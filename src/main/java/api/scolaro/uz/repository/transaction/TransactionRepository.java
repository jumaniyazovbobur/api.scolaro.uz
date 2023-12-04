package api.scolaro.uz.repository.transaction;

import api.scolaro.uz.entity.transaction.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 'Mukhtarov Sarvarbek' on 04.12.2023
 * @project api.scolaro.uz
 * @contact @sarvargo
 */
public interface TransactionRepository extends JpaRepository<TransactionsEntity, String> {

}
