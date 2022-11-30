package com.cathaybank.coindesk.service;

import com.cathaybank.coindesk.component.CathayBankAPIComponent;
import com.cathaybank.coindesk.dto.CurrencyReq;
import com.cathaybank.coindesk.dto.telegram.CoinDeskBpi;
import com.cathaybank.coindesk.dto.telegram.CoinDeskRes;
import com.cathaybank.coindesk.dto.telegram.CoinDeskUpdatedTime;
import com.cathaybank.coindesk.entity.Currency;
import com.cathaybank.coindesk.repository.CurrencyRepo;
import com.cathaybank.coindesk.service.impl.CurrencyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CurrencyServiceImplTests {
    @InjectMocks
    private CurrencyServiceImpl currencyServiceImpl;
    @Mock
    private CurrencyRepo currencyRepo;
    @Mock
    private CathayBankAPIComponent cathayBankAPIComponent;

    @Test
    void query_idNotExist_throwException() {
        when(currencyRepo.findById(Mockito.anyString())).thenReturn(Optional.empty());

        RuntimeException re = Assertions.assertThrows(RuntimeException.class, () -> currencyServiceImpl.query(""));
        Assertions.assertEquals("Currency not exist", re.getMessage());
    }

    @Test
    void query_idExist_returnResult() {
        Currency currency = new Currency();
        currency.setCode("USD");
        currency.setCodeZh("美金");

        when(currencyRepo.findById(Mockito.anyString())).thenReturn(Optional.of(currency));

        Assertions.assertDoesNotThrow(() -> currencyServiceImpl.query(""));
    }

    @Test
    void create_idExist_throwException() {
        when(currencyRepo.existsById(Mockito.anyString())).thenReturn(true);
        CurrencyReq currencyReq = new CurrencyReq();
        currencyReq.setCode("USD");
        RuntimeException re = Assertions.assertThrows(RuntimeException.class, () -> currencyServiceImpl.create(currencyReq));
        Assertions.assertEquals("Currency already exist", re.getMessage());
    }

    @Test
    void create_idNotExist_success() {
        when(currencyRepo.existsById(Mockito.anyString())).thenReturn(false);
        CurrencyReq currencyReq = new CurrencyReq();
        currencyReq.setCode("USD");
        currencyReq.setCodeZh("美金");

        Assertions.assertDoesNotThrow(() -> currencyServiceImpl.create(currencyReq));
    }

    @Test
    void update_idNotExist_throwException() {
        when(currencyRepo.existsById(Mockito.anyString())).thenReturn(false);

        CurrencyReq currencyReq = new CurrencyReq();
        currencyReq.setCode("USD");
        RuntimeException re = Assertions.assertThrows(RuntimeException.class, () -> currencyServiceImpl.update(currencyReq));
        Assertions.assertEquals("Currency not exist", re.getMessage());
    }

    @Test
    void update_idExist_success() {
        when(currencyRepo.existsById(Mockito.anyString())).thenReturn(true);

        CurrencyReq currencyReq = new CurrencyReq();
        currencyReq.setCode("USD");
        currencyReq.setCodeZh("美金");

        when(currencyRepo.save(Mockito.any())).thenReturn(new Currency());

        Assertions.assertDoesNotThrow(() -> currencyServiceImpl.update(currencyReq));
    }

    @Test
    void delete_success() {
        Assertions.assertDoesNotThrow(() ->  currencyServiceImpl.delete(""));
    }

    @Test
    void mergeCurrencyDataFromCoinDeskAPI_listIsEmpty_success() {
        CoinDeskRes coinDeskRes = new  CoinDeskRes();
        coinDeskRes.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org");
        coinDeskRes.setChartName("Bitcoin");

        CoinDeskUpdatedTime coinDeskUpdatedTime = new CoinDeskUpdatedTime();
        coinDeskUpdatedTime.setUpdated("Nov 30, 2022 01:16:00 UTC");
        coinDeskUpdatedTime.setUpdatedISO("2022-11-30T01:16:00+00:00");
        coinDeskUpdatedTime.setUpdateduk("Nov 30, 2022 at 01:16 GMT");
        coinDeskRes.setTime(coinDeskUpdatedTime);

        Map<String, CoinDeskBpi> bpi = new HashMap<>();
        CoinDeskBpi coinDeskBpiUSD = new CoinDeskBpi();
        coinDeskBpiUSD.setCode("USD");
        coinDeskBpiUSD.setSymbol("&#36;");
        coinDeskBpiUSD.setRate("16,873.6496");
        coinDeskBpiUSD.setDescription("United States Dollar");
        coinDeskBpiUSD.setRateFloat(16873.6496);
        bpi.put("USD", coinDeskBpiUSD);

        CoinDeskBpi coinDeskBpiGBP = new CoinDeskBpi();
        coinDeskBpiGBP.setCode("GBP");
        coinDeskBpiGBP.setSymbol("&pound;");
        coinDeskBpiGBP.setRate("14,099.4866");
        coinDeskBpiGBP.setDescription("British Pound Sterling");
        coinDeskBpiGBP.setRateFloat(14099.4866);
        bpi.put("GBP", coinDeskBpiGBP);

        coinDeskRes.setBpi(bpi);

        when(cathayBankAPIComponent.callCoinDesk()).thenReturn(coinDeskRes);

        List<Currency> list = new ArrayList<>();
        Currency currency = new Currency();
        currency.setCode("USD");
        currency.setCodeZh("美金");
        list.add(currency);

        when(currencyRepo.findByCodeIn(Mockito.anySet())).thenReturn(list);

        Assertions.assertDoesNotThrow(() -> currencyServiceImpl.mergeCurrencyDataFromCoinDeskAPI());
    }
}
