package com.prepaid.cardservice.transform;

import com.prepaid.cardservice.domain.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionTransformTest {

    @Test
    public void shouldTransformDomainToModel() {
        com.prepaid.cardservice.model.Transaction transactionModel = TransactionTransform.
                transformFromDomainToModel(getTransactionDomain());

        assertNotNull(transactionModel);
        assertEquals(transactionModel.getAmount(), "100,00");
    }

    @Test
    public void shouldTransformDomainsToModels() {
        List<com.prepaid.cardservice.model.Transaction> transactionsModel = TransactionTransform.
                transformFromDomainsToModels(getTransactionsDomain());

        assertNotNull(transactionsModel);
        assertEquals(transactionsModel.size(), 2);
    }

    private Transaction getTransactionDomain() {
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(new BigDecimal("100"));
        transaction.setAction("withdraw");

        return transaction;
    }

    private List<Transaction> getTransactionsDomain() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(getTransactionDomain());
        transactions.add(getTransactionDomain());

        return transactions;
    }
}
