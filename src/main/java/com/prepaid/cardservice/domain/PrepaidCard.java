package com.prepaid.cardservice.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Document(collection = "card")
public class PrepaidCard implements Serializable {
    @Id
    private Long cardNumber;

    private BigDecimal availableAmount;

    private List<Transaction> transactions;
}
