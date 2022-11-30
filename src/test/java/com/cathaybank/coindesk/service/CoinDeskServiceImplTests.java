package com.cathaybank.coindesk.service;

import com.cathaybank.coindesk.component.CathayBankAPIComponent;
import com.cathaybank.coindesk.dto.telegram.CoinDeskBpi;
import com.cathaybank.coindesk.dto.telegram.CoinDeskRes;
import com.cathaybank.coindesk.dto.telegram.CoinDeskUpdatedTime;
import com.cathaybank.coindesk.service.impl.CoinDeskServiceImpl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CoinDeskServiceImplTests {
    @InjectMocks
    private CoinDeskServiceImpl coinDeskServiceImpl;
    @Mock
    private CathayBankAPIComponent cathayBankAPIComponent;

    @Test
    void callCoinDeskAPI_isSuccess_returnResult() {
        CoinDeskRes coinDeskRes = new CoinDeskRes();
        coinDeskRes.setChartName("Bitcoin");
        coinDeskRes.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org");

        CoinDeskUpdatedTime coinDeskUpdatedTime = new CoinDeskUpdatedTime();
        coinDeskUpdatedTime.setUpdated("Nov 29, 2022 06:49:00 UTC");
        coinDeskUpdatedTime.setUpdatedISO("2022-11-29T06:49:00+00:00");
        coinDeskUpdatedTime.setUpdateduk("Nov 29, 2022 at 06:49 GMT");
        coinDeskRes.setTime(coinDeskUpdatedTime);

        Map<String,CoinDeskBpi> map = new HashMap<>();
        CoinDeskBpi coinDeskBpi = new CoinDeskBpi();
        coinDeskBpi.setCode("USD");
        coinDeskBpi.setSymbol("&#36;");
        coinDeskBpi.setRate("16,455.1569");
        coinDeskBpi.setDescription("United States Dollar");
        coinDeskBpi.setRateFloat(16455.1569);
        map.put("USD", coinDeskBpi);
        coinDeskRes.setBpi(map);

        when(cathayBankAPIComponent.callCoinDesk()).thenReturn(coinDeskRes);

        Assertions.assertDoesNotThrow(() -> coinDeskServiceImpl.callCoinDeskAPI());
    }

    @Test
    void callCoinDeskAPI_occurError_throwException() {
        when(cathayBankAPIComponent.callCoinDesk()).thenThrow(new RuntimeException(""));

        Assertions.assertThrows(RuntimeException.class, () -> coinDeskServiceImpl.callCoinDeskAPI());
    }
}
