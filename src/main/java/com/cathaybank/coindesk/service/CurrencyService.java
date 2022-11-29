package com.cathaybank.coindesk.service;

import com.cathaybank.coindesk.dto.CurrencyReq;
import com.cathaybank.coindesk.dto.CurrencyRes;
import com.cathaybank.coindesk.dto.MergeCurrencyRes;

public interface CurrencyService {
    CurrencyRes query(String code);

    void create(CurrencyReq currencyReq);

    CurrencyRes update(CurrencyReq currencyReq);

    void delete(String code);

    MergeCurrencyRes mergeCurrencyDataFromCoinDeskAPI();
}
