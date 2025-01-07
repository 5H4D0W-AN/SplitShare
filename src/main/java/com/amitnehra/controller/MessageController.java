package com.amitnehra.controller;


import com.amitnehra.dto.FriendDTO;
import com.amitnehra.dto.MessageDTO;
import com.amitnehra.models.Account;
import com.amitnehra.service.FriendRequestsService;
import com.amitnehra.service.MessageService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


@Controller
public class MessageController {
    @Autowired
    private FriendRequestsService friendRequestsService;
    @Autowired
    private MessageService messageService;


    @GetMapping("/chat")
    public String returnChat(HttpSession session, Model model){
        Account account = (Account) session.getAttribute("account");
        if(account == null) return "error";
        List<Account> friendsAccounts = friendRequestsService.findFriends(account.getId());
        List<FriendDTO> friends = new ArrayList<>();
        for (Account account1 : friendsAccounts) {
            if (account1.getProfile().getPhotobytes() != null) {
                FriendDTO friendDTO = new FriendDTO();
                friendDTO.setId(account1.getId());
                friendDTO.setName(account1.getProfile().getName());
                friendDTO.setBase64photo(Base64.getEncoder().encodeToString(account1.getProfile().getPhotobytes()));
                friendDTO.setLastMessage(messageService.getLastMessage(account.getId(), account1.getId()));
                friends.add(friendDTO);
            }
        }
        model.addAttribute("friends", friends);
        return "message";
    }

    @GetMapping("/fetchChatHistory/{friendId}")
    @ResponseBody
    public ResponseEntity<List<MessageDTO>> fetchChatHistory(@PathVariable String friendId, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null || friendId == null) return ResponseEntity.notFound().build();
        List<MessageDTO> list = messageService.getChatHistory(account.getId(), friendId);
        if(list == null) list = new ArrayList<>();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(list);
    }


}
