package com.amitnehra.models;


import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.SortedSet;

@Embeddable
public class Profile {
    @NotNull(message = "Name is Required.")
    @Size(min = 2, message = "Should be at least 2 characters.")
    private String name;
    @Transient
    private MultipartFile photo;
    @Transient
    private String base64photo;
    @Lob
    private byte[] photobytes;
    @Valid
    @Embedded
    private Address address;

    @Embedded
    private Ledger ledger = new Ledger();

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public String getBase64photo() {
        return base64photo;
    }

    public void setBase64photo(String base64photo) {
        this.base64photo = base64photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhotobytes() {
        return photobytes;
    }

    public void setPhotobytes(byte[] photobytes) {
        this.photobytes = photobytes;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Ledger getLedger() {
        return ledger;
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public Profile(String name, byte[] photo, Address address, Ledger ledger) {
        this.name = name;
        this.photobytes = photo;
        this.address = address;
        this.ledger = ledger;
    }

    public Profile() {
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", ledger=" + ledger +
                '}';
    }
}
