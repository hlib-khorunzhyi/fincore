package com.hlibkhorunzhyi.fincore.transfer.entity;

import com.hlibkhorunzhyi.fincore.account.entity.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transfer_id_seq")
    @SequenceGenerator(name = "transfer_id_seq", sequenceName = "transfer_id_seq", allocationSize = 50)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    @Column(name = "source_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal sourceAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id", nullable = false)
    private Account destinationAccount;

    @Column(name = "destination_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal destinationAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private TransferStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Transfer() {
    }

    public Transfer(Long id, Account sourceAccount, BigDecimal sourceAmount, Account destinationAccount, BigDecimal destinationAmount, TransferStatus status, Instant createdAt) {
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.sourceAmount = sourceAmount;
        this.destinationAccount = destinationAccount;
        this.destinationAmount = destinationAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public BigDecimal getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(BigDecimal sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getDestinationAmount() {
        return destinationAmount;
    }

    public void setDestinationAmount(BigDecimal destinationAmount) {
        this.destinationAmount = destinationAmount;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
        if (status == null)
            status = TransferStatus.COMPLETED;
    }
}
