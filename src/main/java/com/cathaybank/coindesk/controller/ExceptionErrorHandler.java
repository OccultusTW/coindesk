package com.cathaybank.coindesk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionErrorHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> onRequestException(Exception e) {
        ResponseEntity<String> entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        log.error(e.getMessage(), e);
        return entity;
    }
}
