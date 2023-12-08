package api.scolaro.uz.entity.transaction;

import api.scolaro.uz.entity.BaseEntity;
import api.scolaro.uz.enums.transaction.ProfileType;
import api.scolaro.uz.enums.transaction.TransactionState;
import api.scolaro.uz.enums.transaction.TransactionStatus;
import api.scolaro.uz.enums.transaction.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsEntity extends BaseEntity {
    private String profileId; // Profile consulting

    @Enumerated(EnumType.STRING)
    private ProfileType profileType; // PROFILE,CONSULTING

    private Long amount = 0L; // as tiyn 1sum = 100 tiyin

    private String paymeTransactionsId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // DEBIT, CREDIT

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;  // to enum

    private String paymentType; // PAYME

    private Long createTime; // create time in the payme
    private LocalDateTime performTime;
    private LocalDateTime cancelTime;
    @Column(columnDefinition = "text")
    private String reason;

    @Enumerated(EnumType.STRING)
    private TransactionState state; //
}