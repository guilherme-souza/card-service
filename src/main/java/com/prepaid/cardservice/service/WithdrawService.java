package com.prepaid.cardservice.service;

import com.prepaid.cardservice.domain.PrepaidCard;
import com.prepaid.cardservice.domain.PrepaidCardRepository;
import com.prepaid.cardservice.domain.Transaction;
import com.prepaid.cardservice.exception.WithdrawValidationException;
import com.prepaid.cardservice.model.WithdrawRequest;
import com.prepaid.cardservice.model.WithdrawResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WithdrawService {

    private final static String ACTION_WITHDRAW = "withdraw";
    private final static String SUCCESS_OPERATION = "00";
    private final static Integer INVALID_ACCOUNT = 14;
    private final static Integer INSUFFICIENT_FUNDS = 51;
    private final static Integer PROCESSING_ERROR = 96;

    private final PrepaidCardRepository repository;

    @Autowired
    public WithdrawService(PrepaidCardRepository repository) {
        this.repository = repository;
    }

    public WithdrawResponse withdrawal(WithdrawRequest request) throws WithdrawValidationException {
        /*
         * Validação dos campos
         */
        if(!ACTION_WITHDRAW.equals(request.getAction())) {
            throw new WithdrawValidationException(PROCESSING_ERROR, "Ação inválida");
        }

        if(request.getCardNumber() == null || request.getCardNumber().isEmpty()) {
            throw new WithdrawValidationException(PROCESSING_ERROR, "Cartão é Obrigatório");
        }

        if(request.getAmount() == null || request.getAmount().isEmpty()) {
            throw new WithdrawValidationException(PROCESSING_ERROR, "Valor para débito é Obrigatório");
        } else {
            try {
                double withdrawAmount = Double.parseDouble(request.getAmount());

                if(withdrawAmount <= 0) {
                    throw new WithdrawValidationException(PROCESSING_ERROR, "Valor para débito não pode ser negativo ou zero");
                }
            } catch (NumberFormatException e) {
                throw new WithdrawValidationException(PROCESSING_ERROR, "Valor para débito precisa ser numérico");
            }
        }

        /*
         * Operação de débito
         */
        synchronized (this) {
            Optional<PrepaidCard> cardOptional = repository.findById(new Long(request.getCardNumber()));
            if (cardOptional.isPresent()) {
                PrepaidCard prepaidCard = cardOptional.get();

                double actualBalance = prepaidCard.getAvailableAmount().doubleValue();
                double withdrawAmount = Double.parseDouble(request.getAmount());

                if(actualBalance < withdrawAmount) {
                    throw new WithdrawValidationException(INSUFFICIENT_FUNDS, "Saldo Insuficiente");
                } else {
                    double newBalance = (actualBalance - withdrawAmount);

                    prepaidCard.setAvailableAmount(new BigDecimal(newBalance));

                    List<Transaction> transactions = prepaidCard.getTransactions();
                    if(transactions == null) {
                        transactions = new ArrayList<>();
                    }

                    Transaction newTransaction = new Transaction();
                    newTransaction.setAction(ACTION_WITHDRAW);
                    newTransaction.setAmount(new BigDecimal(withdrawAmount));
                    newTransaction.setDate(LocalDateTime.now());
                    transactions.add(newTransaction);
                    prepaidCard.setTransactions(transactions);

                    repository.save(prepaidCard);
                }

                WithdrawResponse response = new WithdrawResponse();
                response.setAction(ACTION_WITHDRAW);
                response.setCode(SUCCESS_OPERATION);
                response.setAuthorizationCode(UUID.randomUUID().toString());

                return response;
            } else {
                throw new WithdrawValidationException(INVALID_ACCOUNT, "Conta inválida");
            }
        }
    }
}
