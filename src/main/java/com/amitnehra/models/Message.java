package com.amitnehra.models;

import com.amitnehra.models.enums.MessageStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "fromAccount_id", nullable = false)
    private Account fromAccount;
    @OneToOne
    @JoinColumn(name = "toAccount_id", nullable = false)
    private Account toAccount;
    @NotBlank
    private String content;

    private LocalDateTime timeStamp;
    @Enumerated
    private MessageStatus status;

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime date) {
        this.timeStamp = timeStamp;
    }

    public Message(Long id, Account fromAccount, Account toAccount, String content, LocalDateTime date, MessageStatus status) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.content = content;
        this.timeStamp = date;
        this.status = status;
    }

    public Message() {
    }

    @Override
    public int compareTo(Message o) {
        return o.getTimeStamp().compareTo(this.getTimeStamp());
    }
}
