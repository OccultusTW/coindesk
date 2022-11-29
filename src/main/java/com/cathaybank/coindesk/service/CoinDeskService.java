package com.cathaybank.coindesk.service;

import com.cathaybank.coindesk.dto.telegram.CoinDeskRes;

public interface CoinDeskService {
    CoinDeskRes callCoinDeskAPI();
}
