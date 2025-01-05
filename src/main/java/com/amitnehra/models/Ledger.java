package com.amitnehra.models;

import javax.persistence.Embeddable;
import java.util.TreeSet;

@Embeddable
public class Ledger {

    private int owe = 0;
    private int lent = 0;

    private TreeSet<PendingTransaction> pendingTransactions;



    public TreeSet<PendingTransaction> getPendingTransactions() {
        return pendingTransactions;
    }

    public void setPendingTransactions(TreeSet<PendingTransaction> pendingTransactions) {
        this.pendingTransactions = pendingTransactions;
    }

    public int getOwe() {
        return owe;
    }

    public void setOwe(int owe) {
        this.owe = owe;
    }

    public int getLent() {
        return lent;
    }

    public void setLent(int lent) {
        this.lent = lent;
    }

    public Ledger(int owe, int lent, TreeSet<PendingTransaction> pendingTransactions) {
        this.owe = owe;
        this.lent = lent;
        this.pendingTransactions = pendingTransactions;
    }

    public Ledger() {
        pendingTransactions = new TreeSet<>();
    }
}
