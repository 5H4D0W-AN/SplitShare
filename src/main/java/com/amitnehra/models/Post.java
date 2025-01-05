package com.amitnehra.models;



import org.hibernate.annotations.SortNatural;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
public class Post implements Comparable<Post> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountId; // foreign key
    @Transient
    private MultipartFile photo;
    @Lob
    private byte[] photobytes;
    private LocalDate dateTime;
    private String caption;
    @Transient
    private TreeSet<Comments> comments;
    @Transient
    private TreeSet<Like> likes;


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public byte[] getPhotobytes() {
        return photobytes;
    }

    public void setPhotobytes(byte[] photobytes) {
        this.photobytes = photobytes;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public TreeSet<Comments> getComments() {
        return comments;
    }

    public void setComments(TreeSet<Comments> comments) {
        this.comments = comments;
    }

    public TreeSet<Like> getLikes() {
        return likes;
    }

    public void setLikes(TreeSet<Like> likes) {
        this.likes = likes;
    }

    public Post(Long id, String accountId, String caption, byte[] image, MultipartFile photo, LocalDate dateTime, TreeSet<Comments> comments, TreeSet<Like> likes) {
        this.id = id;
        this.accountId = accountId;
        this.photo = photo;
        this.photobytes = image;
        this.dateTime = dateTime;
        this.comments = comments;
        this.likes = likes;
        this.caption = caption;
    }

    public Post() {
    }

    @Override
    public int compareTo(Post o) {
        return o.dateTime.compareTo(this.dateTime);
    }
}
