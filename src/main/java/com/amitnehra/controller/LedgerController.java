package com.amitnehra.controller;


import com.amitnehra.dto.FriendDTO;
import com.amitnehra.dto.TransactionDTO;
import com.amitnehra.models.Account;
import com.amitnehra.models.PendingTransaction;
import com.amitnehra.service.PendingTransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class LedgerController {
    @Autowired
    private PendingTransactionService pendingTransactionService;


    @RequestMapping("/ledger")
    public String returnLedger(HttpSession session, Model model){
        Account account = (Account) session.getAttribute("account");
        if(account == null){
            return "error";
        }
        FriendDTO accountDTO = FriendDTO.getFromAccount(account);
        List<FriendDTO> friends = (List<FriendDTO>) session.getAttribute("friends");
        List<PendingTransaction> pendingTransactions = pendingTransactionService.findPendingTransactions(account);
        List<TransactionDTO> ptDtos = new ArrayList<>();
        for(PendingTransaction pt: pendingTransactions){
            ptDtos.add(PendingTransactionService.getTransactionDTO(pt));
        }
        // Convert objects to JSON strings
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String friendsJson = objectMapper.writeValueAsString(friends);
            String accountJson = objectMapper.writeValueAsString(accountDTO);
            String pendingTransactionsJson = objectMapper.writeValueAsString(ptDtos);
            model.addAttribute("friendsJson", friendsJson);
            model.addAttribute("accountJson", accountJson);
            model.addAttribute("pendingTransactionsJson", pendingTransactionsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("Number of pending Transactions associated with the account: "+pendingTransactions.size());
        return "ledger";
    }


    @PostMapping("/createTransactions")
    @ResponseBody()
    public ResponseEntity<List<PendingTransaction>> createTransactions(@RequestBody Map<String, Object> payload, HttpSession session){
        String reason = (String) payload.get("reason");
        List<Map<String, Object>> payers = (List<Map<String, Object>>) payload.get("payers");
        List<String> participants  = (List<String>) payload.get("participants");
        Account account = (Account) session.getAttribute("account");
        if(account == null){
            throw new RuntimeException("No account in session. Login again.");
        }
        List<PendingTransaction> createdTransactions = pendingTransactionService.createTransactions(payers, reason, participants, account.getId());
        if(createdTransactions == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(createdTransactions);
    }

    @PostMapping("/saveTransactions")
    @ResponseBody()
    public ResponseEntity<Void> saveTransactions(@RequestBody List<PendingTransaction> transactions){
        try {
            pendingTransactionService.saveTransactions(transactions);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
