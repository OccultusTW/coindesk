package com.cathaybank.coindesk.service.impl;

import com.cathaybank.coindesk.component.CathayBankAPIComponent;
import com.cathaybank.coindesk.dto.telegram.CoinDeskRes;
import com.cathaybank.coindesk.service.CoinDeskService;
import org.springframework.stereotype.Service;

@Service
public class CoinDeskServiceImpl implements CoinDeskService {
    private CathayBankAPIComponent cathayBankAPIComponent;

    public CoinDeskServiceImpl(CathayBankAPIComponent cathayBankAPIComponent) {
        this.cathayBankAPIComponent = cathayBankAPIComponent;
    }

    @Override
    public CoinDeskRes callCoinDeskAPI() {
        return cathayBankAPIComponent.callCoinDesk();
    }
}
