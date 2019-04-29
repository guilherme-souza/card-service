package com.prepaid.cardservice;

import com.prepaid.cardservice.domain.PrepaidCard;
import com.prepaid.cardservice.domain.PrepaidCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Data loader is used to initialize the database with the first two cards for testing purpose.
 */
@Component
public class DataLoader implements ApplicationRunner {
    private PrepaidCardRepository repository;

    @Autowired
    public DataLoader(PrepaidCardRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("INICIALIZANDO A BASE");
        PrepaidCard cardOne = new PrepaidCard();
        cardOne.setCardNumber(1234567890L);
        cardOne.setAvailableAmount(new BigDecimal(1000.0d));
        PrepaidCard cardTwo = new PrepaidCard();
        cardTwo.setCardNumber(9876543210L);
        cardTwo.setAvailableAmount(new BigDecimal(1000.0d));

        this.repository.save(cardOne);
        this.repository.save(cardTwo);
        System.out.println("BASE INICIALIZADA COM OS DOIS CARTÕES");
    }
}
