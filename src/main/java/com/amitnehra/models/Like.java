package com.amitnehra.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Like implements Comparable<Like> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private String postId;
    private LocalDate date;


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Like(Long id, String account, LocalDate date, String post) {
        this.id = id;
        this.postId = post;
        this.accountId = account;
        this.date = date;
    }

    public Like() {
    }

    @Override
    public int compareTo(Like o) {
        return o.date.compareTo(this.date);
    }
}
