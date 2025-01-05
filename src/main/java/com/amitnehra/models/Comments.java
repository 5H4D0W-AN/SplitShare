package com.amitnehra.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Comments implements Comparable<Comments> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId;
    private String postId;  // foreign key
    private String content;
    private LocalDate date;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String post) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Comments(Long id, String post, String content, LocalDate date, String accountId) {
        this.id = id;
        this.accountId = accountId;
        this.postId = post;
        this.content = content;
        this.date = date;
    }

    public Comments() {
    }


    @Override
    public int compareTo(Comments other) {
        return other.date.compareTo(this.date);
    }
}
