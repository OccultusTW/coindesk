package com.cathaybank.coindesk.controller;

import com.cathaybank.coindesk.dto.telegram.CoinDeskRes;
import com.cathaybank.coindesk.service.CoinDeskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coinDesk")
public class CoinDeskController {
    private CoinDeskService coindeskService;

    public CoinDeskController(CoinDeskService coindeskService) {
        this.coindeskService = coindeskService;
    }

    @GetMapping
    public CoinDeskRes callCoinDeskApi() {
        return coindeskService.callCoinDeskAPI();
    }
}
