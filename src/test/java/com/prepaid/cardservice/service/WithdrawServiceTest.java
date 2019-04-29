package com.prepaid.cardservice.service;

import com.prepaid.cardservice.domain.PrepaidCard;
import com.prepaid.cardservice.domain.PrepaidCardRepository;
import com.prepaid.cardservice.exception.WithdrawValidationException;
import com.prepaid.cardservice.model.WithdrawRequest;
import com.prepaid.cardservice.model.WithdrawResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class WithdrawServiceTest {
    @Mock
    private PrepaidCardRepository repository;

    @InjectMocks
    private WithdrawService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(repository.findById(anyLong())).thenReturn(getPrepaidCardWithValues());
    }

    @Test
    public void shouldWithdrawFromCard() throws WithdrawValidationException {
        WithdrawRequest request = new WithdrawRequest();
        request.setAction("withdraw");
        request.setAmount("500");

        WithdrawResponse response = service.withdrawal(request);
        assertNotNull(response);
        assertEquals(response.getCode(), "00");
    }

    @Test(expected = WithdrawValidationException.class)
    public void shouldThrowExceptionWhenBalanceIsInsufficient() throws WithdrawValidationException {
        WithdrawRequest request = new WithdrawRequest();
        request.setAction("withdraw");
        request.setAmount("501");
        service.withdrawal(request);
    }

    @Test(expected = WithdrawValidationException.class)
    public void shouldThrowExceptionWhenActionIsInvalid() throws WithdrawValidationException {
        WithdrawRequest request = new WithdrawRequest();
        request.setAction("balance");
        request.setAmount("500");
        service.withdrawal(request);
    }

    @Test(expected = WithdrawValidationException.class)
    public void shouldThrowExceptionWhenValueIsNotANumber() throws WithdrawValidationException {
        WithdrawRequest request = new WithdrawRequest();
        request.setAction("withdraw");
        request.setAmount("ABC");
        service.withdrawal(request);
    }

    @Test(expected = WithdrawValidationException.class)
    public void shouldThrowExceptionWhenCardNumberIsNotProvided() throws WithdrawValidationException {
        WithdrawRequest request = new WithdrawRequest();
        request.setAction("withdraw");
        request.setAmount("");
        service.withdrawal(request);
    }

    private Optional<PrepaidCard> getPrepaidCardWithValues() {
        PrepaidCard prepaidCard = new PrepaidCard();
        prepaidCard.setAvailableAmount(new BigDecimal("500"));
        prepaidCard.setCardNumber(1234567890L);
        return Optional.of(prepaidCard);
    }
}
