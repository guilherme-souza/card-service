package com.prepaid.cardservice.controller;

import com.prepaid.cardservice.exception.WithdrawValidationException;
import com.prepaid.cardservice.model.WithdrawRequest;
import com.prepaid.cardservice.model.WithdrawResponse;
import com.prepaid.cardservice.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WithdrawController {
    private static final String ACTION = "withdraw";
    private final WithdrawService withdrawService;

    @Autowired
    public WithdrawController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @MessageMapping("/withdraw")
    @SendTo("/topic/result")
    public WithdrawResponse withdrawal(WithdrawRequest request) {

        WithdrawResponse response;

        try {
            response = this.withdrawService.withdrawal(request);
        } catch (WithdrawValidationException e) {
            response = new WithdrawResponse();
            response.setAction(ACTION);
            response.setCode(e.getCode().toString());
        }

        return response;
    }
}
