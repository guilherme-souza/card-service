package com.prepaid.cardservice.transform;

import com.prepaid.cardservice.model.Transaction;
import com.prepaid.cardservice.util.FormatterUtils;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionTransform {
    public static Transaction transformFromDomainToModel(com.prepaid.cardservice.domain.Transaction domain) {
        return new Transaction(domain.getDate().toString(),
                FormatterUtils.formatBigDecimalToString(domain.getAmount()));
    }

    public static List<Transaction> transformFromDomainsToModels(
            List<com.prepaid.cardservice.domain.Transaction> domains) {
        if(domains != null) {
            return domains.stream().map(d -> transformFromDomainToModel(d)).collect(Collectors.toList());
        }

        return null;
    }
}
