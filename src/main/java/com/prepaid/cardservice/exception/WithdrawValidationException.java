package com.prepaid.cardservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class WithdrawValidationException extends Exception {
    private Integer code;

    public WithdrawValidationException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
