package com.prepaid.cardservice.service;

import com.prepaid.cardservice.domain.PrepaidCard;
import com.prepaid.cardservice.domain.PrepaidCardRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class StatementServiceTest {
    @Mock
    private PrepaidCardRepository repository;

    @InjectMocks
    private StatementService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetStatementWhenCardNumberIsProvided() {
        when(repository.findById(anyLong())).thenReturn(getPrepaidCardWithValues());

        Optional<PrepaidCard> prepaidCard = service.getStatement(1234567890L, 10);
        assertNotNull(prepaidCard);
        assertTrue(prepaidCard.isPresent());
        assertNotNull(prepaidCard.get());
    }

    @Test
    public void shouldGetEmptyStatementWhenCardNumberIsProvidedButNotFound() {
        when(repository.findById(anyLong())).thenReturn(getPrepaidCardEmpty());

        Optional<PrepaidCard> prepaidCard = service.getStatement(1234567890L, 10);
        assertNotNull(prepaidCard);
        assertFalse(prepaidCard.isPresent());
    }

    private Optional<PrepaidCard> getPrepaidCardWithValues() {
        PrepaidCard prepaidCard = new PrepaidCard();
        return Optional.of(prepaidCard);
    }

    private Optional<PrepaidCard> getPrepaidCardEmpty() {
        return Optional.empty();
    }
}
