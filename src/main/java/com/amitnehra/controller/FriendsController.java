package com.amitnehra.controller;

import com.amitnehra.dto.FriendDTO;
import com.amitnehra.models.Account;
import com.amitnehra.service.FriendRequestsService;
import net.sf.jasperreports.engine.util.JRStyledText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class FriendsController {
    @Autowired
    private FriendRequestsService friendRequestsService;

    @RequestMapping("/unfriend")
    @ResponseBody()
    public ResponseEntity<Void> unfriend(@RequestParam String id, HttpSession session){
        Account account = (Account) session.getAttribute("account");
        if(id == null || account == null) return ResponseEntity.notFound().build();
        try {
            friendRequestsService.unfriend(id, account.getId());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Unfriended!");
        return ResponseEntity.ok().build();
    }
    @PostMapping("/addFriend")
    @ResponseBody()
    public ResponseEntity<Void> addFriend(@RequestParam String id, HttpSession session){
        System.out.println("friend request sent to : " + id);
        Account account  = (Account) session.getAttribute("account");

        if(account == null || id == null || id.length() == 0) return ResponseEntity.notFound().build();

        friendRequestsService.save(account.getId(), id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdrawFriendRequest")
    @ResponseBody()
    public ResponseEntity<Void> withdrawFriend(@RequestParam String id, HttpSession session){
        Account account  = (Account) session.getAttribute("account");

        if(account == null || id == null || id.length() == 0) return ResponseEntity.notFound().build();
        friendRequestsService.setStatusWithdrawn(account.getId(), id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/acceptRequest")
    @ResponseBody()
    public ResponseEntity<Void> acceptRequest(@RequestParam String id, HttpSession session){
        Account toaccount  = (Account) session.getAttribute("account");

        if(toaccount == null || id == null || id.length() == 0) return ResponseEntity.notFound().build();
        friendRequestsService.acceptRequest(id, toaccount.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rejectRequest")
    @ResponseBody()
    public ResponseEntity<Void> rejectRequest(@RequestParam String id, HttpSession session){
        Account toaccount  = (Account) session.getAttribute("account");

        if(toaccount == null || id == null || id.length() == 0) return ResponseEntity.notFound().build();
        friendRequestsService.rejectRequest(id, toaccount.getId());
        return ResponseEntity.ok().build();
    }


    @RequestMapping("/friends")
    public String returnFriend(HttpSession session, Model model){
        Account account = (Account) session.getAttribute("account");
        List<Account> friendsAccounts = friendRequestsService.findFriends(account.getId());
        List<FriendDTO> friends = new ArrayList<>();
        for (Account account1 : friendsAccounts) {
            if (account1.getProfile().getPhotobytes() != null) {
                FriendDTO friendDTO = new FriendDTO();
                friendDTO.setId(account1.getId());
                friendDTO.setName(account1.getProfile().getName());
                friendDTO.setBase64photo(Base64.getEncoder().encodeToString(account1.getProfile().getPhotobytes()));
                friends.add(friendDTO);
            }
        }
        model.addAttribute("friends", friends);
        return "friendsPage";
    }

    @RequestMapping("sentRequests")
    public String sentRequests(HttpSession session, Model model){
        Account account = (Account) session.getAttribute("account");
        if(account == null) return "error";
        List<Account> sentRA = friendRequestsService.findPendingRequests(account);
        model.addAttribute("sentRequests", sentRA);
        return "sentRequestsPage";
    }
}
