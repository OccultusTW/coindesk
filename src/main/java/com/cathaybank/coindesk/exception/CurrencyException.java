package com.cathaybank.coindesk.exception;

import lombok.Getter;

@Getter
public class CurrencyException extends RuntimeException {
    public CurrencyException(String message) {
        super(message);
    }
}
