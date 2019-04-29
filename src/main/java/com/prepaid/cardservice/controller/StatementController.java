package com.prepaid.cardservice.controller;

import com.prepaid.cardservice.domain.PrepaidCard;
import com.prepaid.cardservice.model.Statement;
import com.prepaid.cardservice.service.StatementService;
import com.prepaid.cardservice.transform.TransactionTransform;
import com.prepaid.cardservice.util.FormatterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class StatementController {

    private final StatementService statementService;

    @Autowired
    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }

    @RequestMapping("/statements/{cardNumber}")
    public @ResponseBody Statement get(@PathVariable(value="cardNumber") Long cardNumber,
                  @RequestParam(value="limit", required = false, defaultValue = "10") Integer limitOfTransactions) {
        Optional<PrepaidCard> prepaidCardOptional = statementService.getStatement(cardNumber, limitOfTransactions);

        if(prepaidCardOptional.isPresent()) {
            PrepaidCard prepaidCard = prepaidCardOptional.get();
            return new Statement(prepaidCard.getCardNumber().toString(),
                    FormatterUtils.formatBigDecimalToString(prepaidCard.getAvailableAmount()),
                    TransactionTransform.transformFromDomainsToModels(prepaidCard.getTransactions()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
