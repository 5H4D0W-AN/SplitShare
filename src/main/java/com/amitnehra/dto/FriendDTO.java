package com.amitnehra.dto;

import com.amitnehra.models.Account;
import com.amitnehra.models.Message;

import java.io.Serializable;
import java.util.Base64;
import java.util.prefs.BackingStoreException;

public class FriendDTO implements Serializable {
    private String id;
    private String name;
    private String base64photo;
    private Message lastMessage;

    public static FriendDTO getFromAccount(Account account) {
        FriendDTO friendDTO = new FriendDTO();
        friendDTO.setId(account.getId());
        friendDTO.setName(account.getProfile().getName());
        friendDTO.setBase64photo(Base64.getEncoder().encodeToString(account.getProfile().getPhotobytes()));
        return friendDTO;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBase64photo() {
        return base64photo;
    }

    public void setBase64photo(String base64photo) {
        this.base64photo = base64photo;
    }

    public FriendDTO(String id, String name, String base64photo) {
        this.id = id;
        this.name = name;
        this.base64photo = base64photo;
    }

    public FriendDTO() {
    }
}
