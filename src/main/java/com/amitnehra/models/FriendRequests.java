package com.amitnehra.models;


import com.amitnehra.models.enums.RequestStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class FriendRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;
    private String fromAccount;
    private String toAccount;
    private LocalDate timeStamp;

    private RequestStatus status;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public FriendRequests(Long requestId, String fromAccount, String toAccount, LocalDate timeStamp, RequestStatus status) {
        this.requestId = requestId;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.timeStamp = timeStamp;
        this.status = status;
    }

    public FriendRequests() {
    }
}
