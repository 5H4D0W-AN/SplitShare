package com.amitnehra.service;


import com.amitnehra.dto.MessageDTO;
import com.amitnehra.exceptions.MessagesException;
import com.amitnehra.models.Account;
import com.amitnehra.models.Message;
import com.amitnehra.models.enums.MessageStatus;
import com.amitnehra.repo.AccountRepo;
import com.amitnehra.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepo messageRepository;
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public void saveMessage(MessageDTO messageDTO) {
        System.out.println("messageDTO passed to service to save in DB: "+messageDTO);
        Message message = new Message();
        Account fromAccount = accountRepo.findUser(messageDTO.getSenderId());
        Account toAccount = accountRepo.findUser(messageDTO.getReceiverId());
        if(fromAccount == null || toAccount == null) {
            throw new MessagesException("Account could not be found to save message.");
        }
        message.setFromAccount(fromAccount); // Assuming Account is your user entity
        message.setToAccount(toAccount);
        message.setContent(messageDTO.getContent());
        message.setTimeStamp(messageDTO.getTimestamp());
        message.setStatus(MessageStatus.SENT);
        System.out.println("Message saved: "+message);
        messageRepository.save(message);
    }

    @Override
    public List<MessageDTO> getChatHistory(String senderId, String receiverId) {
        List<Message> messages = messageRepository.findBySenderAndReceiver(receiverId, senderId);
        if(messages == null) {
            return new ArrayList<>();
        }
        return messages.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Message getLastMessage(String id, String id1) {
        return messageRepository.getLastMessage(id, id1);
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setSenderId(message.getFromAccount().getId());
        dto.setReceiverId(message.getToAccount().getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimeStamp());
        return dto;
    }
}
