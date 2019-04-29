package com.prepaid.cardservice.service;

import com.prepaid.cardservice.domain.PrepaidCard;
import com.prepaid.cardservice.domain.PrepaidCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatementService {
    private PrepaidCardRepository repository;

    @Autowired
    public StatementService(PrepaidCardRepository repository) {
        this.repository = repository;
    }

    public Optional<PrepaidCard> getStatement(Long cardNumber, Integer limitOfTransactions) {
        return repository.findById(cardNumber);
    }
}
