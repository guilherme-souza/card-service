package com.prepaid.cardservice.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document(collection = "transaction")
public class Transaction implements Serializable {
    private String action;

    private LocalDateTime date;

    private BigDecimal amount;

    private String authorizationCode;
}
