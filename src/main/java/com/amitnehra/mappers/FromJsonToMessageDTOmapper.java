package com.amitnehra.mappers;

import com.amitnehra.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class FromJsonToMessageDTOmapper {
    public static MessageDTO map(String message){
        JSONObject json = new JSONObject(message);
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSenderId(json.getString("senderId"));
        messageDTO.setReceiverId(json.getString("receiverId"));
        messageDTO.setContent(json.getString("content"));
        messageDTO.setTimestamp(LocalDateTime.now());
        return messageDTO;
    }
}
