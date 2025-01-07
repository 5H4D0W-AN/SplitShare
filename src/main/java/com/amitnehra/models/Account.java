package com.amitnehra.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;

@Entity
public class Account implements Comparable<Account> {
    @NotNull(message = "Email is Required.")
    @Email(message = "Invalid Email Format.")
    private String email;
    @Id
    private String id;
    @NotBlank
    @Size(min = 8, message = "Password should be at least 8 characters long.")
    private String password;
    @Embedded
    @Valid
    private Profile profile = new Profile();
    @Size(min = 10, max = 10, message = "Mobile number must be of length 10.")
    private String mobile;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Account(String email, String id, String password, Profile profile, String mobile) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.profile = profile;
        this.mobile = mobile;
    }

    public Account() {

    }

    @Override
    public int compareTo(Account o) {
        return this.profile.getName().compareTo(o.profile.getName());
    }
}
