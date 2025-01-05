package com.amitnehra.service;

import com.amitnehra.dto.TransactionDTO;
import com.amitnehra.models.Account;
import com.amitnehra.models.PendingTransaction;

import java.util.List;
import java.util.Map;

public interface PendingTransactionService {
    static TransactionDTO getTransactionDTO(PendingTransaction pt){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(pt.getId());
        transactionDTO.setById(pt.getByAccount().getId());
        transactionDTO.setFromId(pt.getFromAccount().getId());
        transactionDTO.setToId(pt.getToAccount().getId());
        transactionDTO.setAmount(pt.getAmount());
        transactionDTO.setReason(pt.getReason());
        transactionDTO.setDate(pt.getDate().toString());
        transactionDTO.setStatus(pt.getStatus().toString());
        return transactionDTO;
    }

    List<PendingTransaction> findPendingTransactions(Account account);

    List<PendingTransaction> createTransactions(List<Map<String, Object>> payers, String reason, List<String> participants, String id);

    void saveTransactions(List<PendingTransaction> transactions);
}
