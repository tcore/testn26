package com.tcore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {
    private Double amount;

    private String type;

    private Long parentId;

    public Transaction(Double amount, String type) {
        this.amount = amount;
        this.type = type;
    }
}
