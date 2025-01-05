package com.amitnehra.models;

import com.amitnehra.models.enums.TransactionStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PendingTransaction implements Comparable<PendingTransaction> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "fromAccount_id", nullable = false)
    private Account fromAccount;
    @OneToOne
    @JoinColumn(name = "toAccount_id", nullable = false)
    private Account toAccount;
    @OneToOne
    @JoinColumn(name = "authorAccount_id", nullable = false)
    private Account byAccount;
    private String reason;
    private Double amount;
    private LocalDate date;
    private TransactionStatus status;

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Account getByAccount() {
        return byAccount;
    }

    public void setByAccount(Account byAccount) {
        this.byAccount = byAccount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PendingTransaction(Long id, Account fromAccount, Account toAccount, Account byAccount, String reason, Double amount, LocalDate date, TransactionStatus status) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.byAccount = byAccount;
        this.reason = reason;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public PendingTransaction() {
    }

    @Override
    public String toString() {
        return "PendingTransaction{" +
                "id=" + id +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", byAccount=" + byAccount +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", status=" + status +
                "}\n";
    }

    @Override
    public int compareTo(PendingTransaction o) {
        return o.getDate().compareTo(this.getDate());
    }
}
