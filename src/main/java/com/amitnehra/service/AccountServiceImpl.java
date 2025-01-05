package com.amitnehra.service;

import com.amitnehra.models.Account;
import com.amitnehra.models.Profile;
import com.amitnehra.repo.AccountRepo;
import com.amitnehra.repo.AccountRepoImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LogManager.getLogger(AccountRepoImpl.class.getName());
    @Autowired
    private AccountRepo accountRepo;

    @Override
    public Account findUser(String userId) {
        return accountRepo.findUser(userId);
    }

    @Override
    public boolean save(Account account) {
        return accountRepo.save(account);
    }
    @Override
    public void update(Account account){
        accountRepo.update(account);
    }

    @Override
    public void merge(Account account) {
        accountRepo.merge(account);
    }

    @Override
    public boolean checkForEmail(String email) {
        return accountRepo.checkForEmail(email);
    }

    @Override
    public boolean checkForId(String id) {
        return accountRepo.checkForId(id);
    }

    @Override
    public List<Profile> searchProfiles(String query) {
        return accountRepo.findProfiles(query);
    }


}
