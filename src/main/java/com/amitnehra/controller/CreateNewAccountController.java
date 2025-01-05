package com.amitnehra.controller;


import com.amitnehra.models.Account;
import com.amitnehra.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@Validated
@MultipartConfig
public class CreateNewAccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/createNewAccount")
    public String createAccount(Model model){
        model.addAttribute("account", new Account());
        return "createAccount";
    }

    @PostMapping("/createNewAccount")
    public String sendSuccessFail(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult,
                                  Model model){

//        System.out.println("here 1");

        boolean b1 = accountService.checkForEmail(account.getEmail());
        boolean b2 = accountService.checkForId(account.getId());
        if(b1){
            model.addAttribute("message", "Email already Exist.");
        }
        if(b2){
            model.addAttribute("message2", "UserId must be unique.");
        }

        if(bindingResult.hasErrors() || b1 || b2) {
            if (bindingResult.hasErrors()) {
                bindingResult.getFieldErrors().forEach(error ->
                        System.out.println("Error in field: " + error.getField() + " - " + error.getDefaultMessage())
                );
                return "createAccount";
            }
        }


//        System.out.println("here 2");

        try {
             // Save photo if present
            if (account.getProfile().getPhoto() != null && !account.getProfile().getPhoto().isEmpty()) {
                account.getProfile().setPhotobytes(account.getProfile().getPhoto().getBytes());
            }

                // Save account to the database
            accountService.save(account);
            return "success"; // Redirect to success page
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "createAccount"; // Reload the form with error
        }

    }
}
