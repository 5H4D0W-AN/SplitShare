package com.amitnehra.controller;



import com.amitnehra.dto.FriendDTO;
import com.amitnehra.dto.TransactionDTO;
import com.amitnehra.models.*;
import com.amitnehra.repo.AccountRepoImpl;
import com.amitnehra.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;

@Controller
public class HomeController {

    private static final Logger logger = LogManager.getLogger(HomeController.class.getName());
    @Autowired
    private AccountService accountService;


    @GetMapping("/searchProfiles")
    @ResponseBody()
    public ResponseEntity<List<Profile>> searchProfiles(@RequestParam String query) {
        System.out.println("Received query: " + query);
        if (query == null || query.trim().isEmpty()) {
            System.out.println("Query is empty or null");
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<Profile> profiles = accountService.searchProfiles(query);
        if (profiles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(profiles);
    }


    @RequestMapping("/createPost")
    public String createPost(){

        return "createPost";
    }

    @RequestMapping("/logout")
    public String createPost(HttpSession session, Model model){
        model.addAttribute("logoutMessage", "Logged out successfully!");
        session.invalidate();
        return "login";
    }
}
