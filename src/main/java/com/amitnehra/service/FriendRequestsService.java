package com.amitnehra.service;

import com.amitnehra.models.Account;

import java.util.List;

public interface FriendRequestsService {
    List<Account> findPendingRequests(Account account);

    void save(String id, String id1);

    void setStatusWithdrawn(String id, String id1);

    List<Account> findSuggestions(String id);

    List<Account> findPendingRequestsTo(String id);

    void acceptRequest(String id, String id1);

    void rejectRequest(String id, String id1);

    List<Account> findFriends(String id);

    void unfriend(String id, String id1);
}
