package com.amitnehra.controller;

import com.amitnehra.dto.FriendDTO;
import com.amitnehra.models.Account;
import com.amitnehra.models.Post;
import com.amitnehra.service.AccountService;
import com.amitnehra.service.FriendRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private FriendRequestsService friendRequestsService;


    @GetMapping("/")
    public String login1(){
        return "login";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("userId") String userId, @RequestParam("password") String password,
                              Model model, HttpSession httpSession){
        System.out.println("in post of login controller.");
        Account account = accountService.findUser(userId);
        if(account == null){
            model.addAttribute("message", "User not found. Sign Up for New Account.");
        }else{
            if(account.getPassword().equals(password)){
                List<Account> friendSuggestions = friendRequestsService.findSuggestions(account.getId());
                for (Account account1 : friendSuggestions) {
                    if (account1.getProfile().getPhotobytes() != null) {
                        account1.getProfile().setBase64photo(Base64.getEncoder().encodeToString(account1.getProfile().getPhotobytes()));
                    }
                }
                List<Account> pendingRequests = friendRequestsService.findPendingRequestsTo(account.getId());
                for (Account account1 : pendingRequests) {
                    if (account1.getProfile().getPhotobytes() != null) {
                        account1.getProfile().setBase64photo(Base64.getEncoder().encodeToString(account1.getProfile().getPhotobytes()));
                    }
                }
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
                if(account.getProfile().getPhotobytes()!=null) {
                    account.getProfile().setBase64photo(Base64.getEncoder().encodeToString(account.getProfile().getPhotobytes()));
                }


                List<Post> posts = new ArrayList<>();
                model.addAttribute("posts", posts);
                model.addAttribute("account", account);
                model.addAttribute("friendSuggestions", friendSuggestions);
                model.addAttribute("pendingRequests", pendingRequests);
                httpSession.setAttribute("friends", friends);
                httpSession.setAttribute("account", account);

                System.out.println("View Home Returned...");
                return "home";
            }else{
                model.addAttribute("message", "Invalid Password.");
            }
        }
        return "login";
    }
}
