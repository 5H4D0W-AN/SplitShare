package com.amitnehra.configuration;


import com.amitnehra.dto.MessageDTO;
import com.amitnehra.mappers.FromJsonToMessageDTOmapper;
import com.amitnehra.repo.AccountRepoImpl;
import com.amitnehra.service.MessageService;
import com.amitnehra.service.MessageServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/chat/{userId}")
public class ChatEndPoint {
    private static final ConcurrentHashMap<String, Session> clients = new ConcurrentHashMap<>();
    private static final Logger logger = LogManager.getLogger(AccountRepoImpl.class.getName());

    @Autowired
    private MessageService messageService = SpringContext.getBean(MessageService.class);;

    public ChatEndPoint() {
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        System.out.println("Connection opened: " + session.getId());
        clients.put(userId, session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received: " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        MessageDTO messageDTO = null;
        try {
            messageDTO = FromJsonToMessageDTOmapper.map(message);
            messageService.saveMessage(messageDTO);
            System.out.println("Message Saved in DB.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            logger.error("Problem mapping message to messageDTO");
            System.out.println("Unable to save or send message.");
            return;
        }

        Session receiverSession = clients.get(messageDTO.getReceiverId());
        if (receiverSession != null) {
            receiverSession.getAsyncRemote().sendText(message);
        }else{
            System.out.println("Receiver : "+messageDTO.getReceiverId() +" is offline.");
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        System.out.println("Session closed: "+ session.getId());
        clients.remove(userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
        throwable.printStackTrace();
    }
}
