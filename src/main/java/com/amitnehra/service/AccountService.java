package com.amitnehra.service;

import com.amitnehra.models.Account;
import com.amitnehra.models.Profile;

import java.util.List;

public interface AccountService {
    Account findUser(String userId);
    boolean save(Account account);
    boolean checkForEmail(String email);
    boolean checkForId(String id);
    List<Profile> searchProfiles(String query);

    void update(Account account);

    void merge(Account account);
}
