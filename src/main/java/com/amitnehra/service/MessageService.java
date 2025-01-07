package com.amitnehra.service;

import com.amitnehra.dto.MessageDTO;
import com.amitnehra.models.Message;

import java.util.List;

public interface MessageService {
    void saveMessage(MessageDTO messageDTO);

    List<MessageDTO> getChatHistory(String receiverId, String senderId);

    Message getLastMessage(String id, String id1);
}
