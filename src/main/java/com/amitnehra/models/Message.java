package com.amitnehra.models;

import com.amitnehra.models.enums.MessageStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
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

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Message(Long id, Account fromAccount, Account toAccount, String content, LocalDateTime timeStamp, MessageStatus status) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.content = content;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public Message() {
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", content='" + content + '\'' +
                ", timeStamp=" + timeStamp +
                ", status=" + status +
                '}';
    }

    @Override
    public int compareTo(Message o) {
        return o.getTimeStamp().compareTo(this.getTimeStamp());
    }
}
