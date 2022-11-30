package com.cathaybank.coindesk.controller;

import com.cathaybank.coindesk.dto.CurrencyReq;
import com.cathaybank.coindesk.dto.CurrencyRes;
import com.cathaybank.coindesk.dto.MergeCurrencyRes;
import com.cathaybank.coindesk.exception.CurrencyException;
import com.cathaybank.coindesk.service.CurrencyService;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{code}")
    public CurrencyRes query(@PathVariable("code") String code) {
        return currencyService.query(code);
    }

    @PostMapping
    public void create(@Valid @RequestBody CurrencyReq currencyReq, Errors errors) {
        validation(errors);
        currencyService.create(currencyReq);
    }

    @PutMapping
    public CurrencyRes update(@Valid @RequestBody CurrencyReq currencyReq, Errors errors) {
        validation(errors);
        return currencyService.update(currencyReq);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable("code") String code) {
        currencyService.delete(code);
    }

    @GetMapping("/mergeCoinDeskAPIData")
    public MergeCurrencyRes mergeCurrencyDataFromCoinDeskAPI() {
        return currencyService.mergeCurrencyDataFromCoinDeskAPI();
    }

    private void validation(Errors errors) {
        if(errors.hasFieldErrors()) {
            throw new CurrencyException("Input data invalid");
        }
    }
}
