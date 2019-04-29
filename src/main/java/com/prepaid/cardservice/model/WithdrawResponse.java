package com.prepaid.cardservice.model;

import lombok.Data;

@Data
public class WithdrawResponse {
    private String action;
    private String code;
    private String authorizationCode;
}
