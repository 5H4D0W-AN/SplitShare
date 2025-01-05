package com.amitnehra.service;

import com.amitnehra.dto.PaidDTO;
import com.amitnehra.models.Account;
import com.amitnehra.models.PendingTransaction;
import com.amitnehra.models.enums.TransactionStatus;
import com.amitnehra.repo.AccountRepo;
import com.amitnehra.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
public class PendingTransactionServiceImpl implements PendingTransactionService{
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private TransactionRepo transactionRepo;


    private PendingTransaction getNewTransaction(double amount, String fromId, String toId, String byId, String reason){
        PendingTransaction transaction = new PendingTransaction();

        Account byAccount = accountRepo.findUser(byId);
        Account toAccount = accountRepo.findUser(toId);
        Account fromAccount = accountRepo.findUser(fromId);
        if(byAccount == null || toAccount == null || fromAccount == null){
            throw new RuntimeException("Account not found while creating Transactions for the record you entered. In PendingTransaction Service.");
        }
        transaction.setDate(LocalDate.now());
        transaction.setAmount(amount);
        transaction.setByAccount(byAccount);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setReason(reason);
        return transaction;
    }
    @Override
    public List<PendingTransaction> findPendingTransactions(Account account) {
        return transactionRepo.findPendingTransactions(account.getId());
    }

    @Override
    public List<PendingTransaction> createTransactions(List<Map<String, Object>> payers, String reason, List<String> participants, String id) {
        List<PendingTransaction> list = new ArrayList<>();
        TreeSet<PaidDTO> allps = new TreeSet<>();
        int total = 0, cnt = 0;
        for(Map<String, Object> mp: payers){
            Integer val = (Integer) mp.get("amount");
            String id1 = (String) mp.get("id");
            total += val; cnt++;
            allps.add(new PaidDTO(id1, 1.0*val));
        }
        for(String s: participants){
            allps.add(new PaidDTO(s, 0.0));
            cnt++;
        }
        List<PaidDTO> allParticipants = new ArrayList<>(allps);

        double equalSection = 1.0*total/cnt;

        int it1 = 0, it2 = allParticipants.size()-1;
        while(it1 < it2){
            PaidDTO paidDTO2 = allParticipants.get(it2);
            PaidDTO paidDTO1 = allParticipants.get(it1);
            double paid = paidDTO2.getAmount();
            if(paid+0.5 < equalSection){
                PendingTransaction transaction = getNewTransaction(equalSection-paid, paidDTO2.getId(), paidDTO1.getId(), id, reason);
                list.add(transaction);
                it2--;
                paidDTO1.setAmount(paidDTO1.getAmount()-(equalSection-paid));
                if(paidDTO1.getAmount()-0.5 < equalSection){
                    it1++;
                }
            }
        }
        return list;
    }

    @Override
    @Transactional
    public void saveTransactions(List<PendingTransaction> transactions) {
        for(PendingTransaction pt: transactions){
            transactionRepo.save(pt);
        }
    }
}
