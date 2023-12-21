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
    @Column(name = "profile_id")
    private String profileId; // Profile, consulting

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_type")
    private ProfileType profileType; // PROFILE,CONSULTING
    @Column(name = "amount")
    private Long amount = 0L; // as tiyn 1sum = 100 tiyin
    @Column(name = "payme_transactions_id")
    private String paymeTransactionsId;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType; // DEBIT, CREDIT

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;  // to enum
    @Column(name = "payment_type")
    private String paymentType; // PAYME
    @Column(name = "create_time")
    private Long createTime; // create time in the payme
    @Column(name = "perform_time")
    private LocalDateTime performTime;
    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;
    @Column(name = "reason")
    private Integer reason;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private TransactionState state;
    @Column(name = "transform_id")
    private String transformId;
    @Column(name = "transform_order")
    private Integer transformOrder;

}