package com.amitnehra.repo;

import com.amitnehra.models.Message;

import java.util.List;

public interface MessageRepo{
    List<Message> findBySenderAndReceiver(String receiverId, String senderId);

    void save(Message message);
}
