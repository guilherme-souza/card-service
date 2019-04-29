package com.prepaid.cardservice.model;

import lombok.Data;

@Data
public class WithdrawRequest {
    private String action;
    private String cardNumber;
    private String amount;
}
