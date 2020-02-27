package com.sap.cc.exception;

public class CardNumberInvalidException extends RuntimeException {
    public CardNumberInvalidException(String cardNumber) {
        super("Card number "+cardNumber+" is not in align with luhn 10 algorithm");
    }
}
