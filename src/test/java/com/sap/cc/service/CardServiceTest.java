package com.sap.cc.service;

import com.sap.cc.dto.CardDto;
import com.sap.cc.exception.CardNumberInvalidException;
import com.sap.cc.model.CreditCard;
import com.sap.cc.repository.CardRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSuccessProcessNewCard() {
        CardDto cardDto = new CardDto();
        cardDto.setName("user1");
        cardDto.setCardNumber("4485913128692407");
        cardDto.setLimit(10000.00);
        cardService.processNewCard(cardDto);
        verify(cardRepository).save(CreditCard.builder().name("user1").cardNumber("4485913128692407").cardLimit(10000.00).build());
    }

    @Test
    public void testFailureProcessNewCard() {
        exception.expect(CardNumberInvalidException.class);
        exception.expectMessage("Card number 4485913128692408 is not in align with luhn 10 algorithm");
        CardDto cardDto = new CardDto();
        cardDto.setName("user1");
        cardDto.setCardNumber("4485913128692408");
        cardDto.setLimit(10000.00);
        cardService.processNewCard(cardDto);
        verify(cardRepository, never()).save(CreditCard.builder().name("user1").cardNumber("4485913128692407").cardLimit(10000.00).build());
    }

    @Test
    public void testGetAllCreditCards() {
        CreditCard card1 = CreditCard.builder().cardNumber("cardNum1").cardLimit(20000.00).name("user1").build();
        CreditCard card2 = CreditCard.builder().cardNumber("cardNum2").cardLimit(10000.00).name("user2").build();
        when(cardRepository.findAll()).thenReturn(Arrays.asList(card1, card2));
        List<CreditCard> actualResponse = cardService.getAllCards();
        Assert.assertEquals(Arrays.asList(card1, card2), actualResponse);

    }
}
