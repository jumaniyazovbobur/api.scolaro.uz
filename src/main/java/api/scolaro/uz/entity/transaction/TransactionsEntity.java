package api.scolaro.uz.entity.transaction;

import api.scolaro.uz.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsEntity extends BaseEntity {
    private String profileId; // Profile consulting
    private String profileType; // PROFILE,CONSULTING  to enum
    private Long amount; // tiyn
    private String paymeTransactionsId;
    private String transactionType; // DEBIT, CREDIT to enum
    private String status;  // to enum
    private String paymentType; // PAYME,


    // TODO
    //1.  balance in profile and consulting
}
