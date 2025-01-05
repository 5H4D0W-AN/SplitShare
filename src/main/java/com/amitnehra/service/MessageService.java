package com.amitnehra.service;

import com.amitnehra.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    void saveMessage(MessageDTO messageDTO);

    List<MessageDTO> getChatHistory(String receiverId, String senderId);
}
