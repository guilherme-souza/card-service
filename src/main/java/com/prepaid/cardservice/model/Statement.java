package com.prepaid.cardservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Statement {
    private String cardNumber;
    private String amount;
    private List<Transaction> transactions;
}
