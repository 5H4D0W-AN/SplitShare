package com.amitnehra.service;

import com.amitnehra.exceptions.NoRequestFound;
import com.amitnehra.models.Account;
import com.amitnehra.models.FriendRequests;
import com.amitnehra.models.enums.RequestStatus;
import com.amitnehra.repo.AccountRepo;
import com.amitnehra.repo.FriendRequestsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class FriendRequestsServiceImpl implements FriendRequestsService{

    @Autowired
    private FriendRequestsRepo friendRequestsRepo;

    @Autowired
    private AccountRepo accountRepo;

    private boolean notfound(Account account, String id){
        FriendRequests friendRequests0 = friendRequestsRepo.findRequest(account.getId(), id);
        FriendRequests friendRequests1 = friendRequestsRepo.findRequest(id, account.getId());

        if(friendRequests0 == null && friendRequests1 == null) return true;
        if(friendRequests0 != null){
            if(friendRequests0.getStatus() == RequestStatus.WITHDRAWN || friendRequests0.getStatus() == RequestStatus.REJECTED){
                return true;
            }else{
                return false;
            }
        }
        if(friendRequests1 != null){
            if(friendRequests1.getStatus() == RequestStatus.WITHDRAWN || friendRequests1.getStatus() == RequestStatus.REJECTED ||
                friendRequests1.getStatus() == RequestStatus.UNFRIENDED){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Account> findPendingRequests(Account account) {
        List<FriendRequests> list = friendRequestsRepo.findPendingRequestsBy(account.getId());
        List<Account> ret = new ArrayList<>();
        for(FriendRequests fr: list){
            Account account1 = accountRepo.findUser(fr.getToAccount());
            if(account1 != null){
                if(account1.getProfile().getPhotobytes() != null){
                    account1.getProfile().setBase64photo(Base64.getEncoder().encodeToString(account1.getProfile().getPhotobytes()));
                }
                ret.add(account1);
            }
        }
        return ret;
    }

    @Override
    @Transactional
    public void save(String id, String id1) {
        FriendRequests friendRequests0 = friendRequestsRepo.findRequest(id, id1);
        if(friendRequests0 != null){
            friendRequestsRepo.remove(friendRequests0);
            friendRequests0.setStatus(RequestStatus.PENDING);
            friendRequestsRepo.save(friendRequests0);
            return;
        }
        FriendRequests friendRequests = new FriendRequests();
        friendRequests.setFromAccount(id);
        friendRequests.setToAccount(id1);
        friendRequests.setTimeStamp(LocalDate.now());
        friendRequests.setStatus(RequestStatus.PENDING);
        friendRequestsRepo.save(friendRequests);
    }

    @Override
    @Transactional
    public void setStatusWithdrawn(String id, String id1) {
        FriendRequests friendRequests = friendRequestsRepo.findRequest(id, id1);
        if(friendRequests == null) throw new RuntimeException("Request not found while withdrawing Friend Request.");
        friendRequestsRepo.remove(friendRequests);
        friendRequests.setStatus(RequestStatus.WITHDRAWN);
        friendRequestsRepo.save(friendRequests);
    }

    @Override
    public List<Account> findSuggestions(String id) {
        List<Object[]> suggestions = friendRequestsRepo.findFriendSuggestions(id);
        HashSet<Account> retlist = new HashSet<>();
        for (Object[] suggestion : suggestions) {
            String suggestedFriend = (String) suggestion[0];
            Account account = accountRepo.findUser(suggestedFriend);
            if(account!=null && notfound(account, id)) retlist.add(account);
        }
        if(retlist.size()<10){
            List<Account> rem = accountRepo.getAccountsForFriends(20);
            for(Account account: rem){
                if(!account.getId().equals(id)){
                    if(notfound(account, id) && !retlist.contains(account)) retlist.add(account);
                }
            }
        }
        return new ArrayList<>(retlist);
    }

    @Override
    public List<Account> findPendingRequestsTo(String id) {
        List<FriendRequests> ids = friendRequestsRepo.findPendingRequestsTo(id);
        HashSet<Account> retlist = new HashSet<>();
        for (FriendRequests requests: ids) {
            Account account = accountRepo.findUser(requests.getFromAccount());
            if(account!=null) retlist.add(account);
        }
        return new ArrayList<>(retlist);
    }

    @Override
    @Transactional
    public void acceptRequest(String id, String id1) {
        FriendRequests friendRequests = friendRequestsRepo.findRequest(id, id1);
        if(friendRequests == null) throw new RuntimeException("Request not found while accepting Friend Request.");
        friendRequestsRepo.remove(friendRequests);
        friendRequests.setStatus(RequestStatus.ACCEPTED);
        friendRequestsRepo.save(friendRequests);
    }

    @Override
    @Transactional
    public void rejectRequest(String id, String id1) {
        FriendRequests friendRequests = friendRequestsRepo.findRequest(id, id1);
        if(friendRequests == null) throw new RuntimeException("Request not found while rejecting Friend Request.");
        friendRequestsRepo.remove(friendRequests);
        friendRequests.setStatus(RequestStatus.REJECTED);
        friendRequestsRepo.save(friendRequests);
    }

    @Override
    public List<Account> findFriends(String id) {
        List<FriendRequests> friendRequests = friendRequestsRepo.findAcceptedRequests(id);
        List<Account> retlist = new ArrayList<>();
        for(FriendRequests fr: friendRequests){
            String findid = fr.getFromAccount();
            if(findid.equals(id)) findid = fr.getToAccount();
            Account account = accountRepo.findUser(findid);
            if(account != null) retlist.add(account);
        }
        return retlist;
    }

    @Override
    @Transactional
    public void unfriend(String id, String id1) {
        FriendRequests friendRequests = friendRequestsRepo.findRequest(id, id1);
        if(friendRequests == null) friendRequests = friendRequestsRepo.findRequest(id1, id);

        if(friendRequests == null) throw new NoRequestFound("No friend Request found. Developer failed being a developer.");

        friendRequestsRepo.remove(friendRequests);
        friendRequests.setStatus(RequestStatus.UNFRIENDED);
        friendRequestsRepo.save(friendRequests);
    }
}
