package com.amitnehra.dto;

public class PaidDTO implements Comparable<PaidDTO> {
    private String id;
    private Double amount;

    public PaidDTO(String id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public PaidDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(PaidDTO o) {
        return o.getAmount().compareTo(this.getAmount());
    }
}
