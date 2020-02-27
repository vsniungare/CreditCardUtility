package com.sap.cc.service;

import com.sap.cc.dto.CardDto;
import com.sap.cc.exception.CardNumberInvalidException;
import com.sap.cc.model.CreditCard;
import com.sap.cc.repository.CardRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Synchronized
    public void processNewCard(CardDto cardDto) {
        if (isCardNumberLuhnApplicable(cardDto.getCardNumber())) {
            cardRepository.save(CreditCard.builder()
                    .name(cardDto.getName())
                    .cardNumber(cardDto.getCardNumber())
                    .cardLimit(cardDto.getLimit()).build());
        } else {
            throw new CardNumberInvalidException(cardDto.getCardNumber());
        }
    }

    private boolean isCardNumberLuhnApplicable(String cardNumber) {
        int nDigits = cardNumber.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--) {

            int d = cardNumber.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    public List<CreditCard> getAllCards() {
        return cardRepository.findAll();
    }
}
