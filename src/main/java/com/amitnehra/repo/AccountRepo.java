package com.amitnehra.repo;

import com.amitnehra.models.Account;
import com.amitnehra.models.Profile;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.List;

public interface AccountRepo {
    Account findUser(String userId);

    boolean save(Account account);

    boolean checkForEmail(String email);

    boolean checkForId(String id);

    List<Profile> findProfiles(String query);

    List<Account> getAccountsForFriends(Integer a);

    void update(Account account);

    void merge(Account account);
}
