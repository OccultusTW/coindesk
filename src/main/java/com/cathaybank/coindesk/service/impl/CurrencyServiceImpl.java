package com.cathaybank.coindesk.service.impl;

import com.cathaybank.coindesk.component.CathayBankAPIComponent;
import com.cathaybank.coindesk.config.Constant;
import com.cathaybank.coindesk.dto.CurrencyDetail;
import com.cathaybank.coindesk.dto.CurrencyReq;
import com.cathaybank.coindesk.dto.CurrencyRes;
import com.cathaybank.coindesk.dto.MergeCurrencyRes;
import com.cathaybank.coindesk.dto.telegram.CoinDeskBpi;
import com.cathaybank.coindesk.dto.telegram.CoinDeskRes;
import com.cathaybank.coindesk.entity.Currency;
import com.cathaybank.coindesk.repository.CurrencyRepo;
import com.cathaybank.coindesk.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private CurrencyRepo currencyRepo;
    private CathayBankAPIComponent cathayBankAPIComponent;

    public CurrencyServiceImpl(CurrencyRepo currencyRepo, CathayBankAPIComponent cathayBankAPIComponent) {
        this.currencyRepo = currencyRepo;
        this.cathayBankAPIComponent = cathayBankAPIComponent;
    }

    @Override
    public CurrencyRes query(String code) {
        Currency currency = currencyRepo.findById(code).orElseThrow(() -> new RuntimeException("Currency not exist"));

        CurrencyRes currencyRes = new CurrencyRes();
        currencyRes.setCode(currency.getCode());
        currencyRes.setCodeZh(currency.getCodeZh());
        return currencyRes;
    }

    @Override
    public void create(CurrencyReq currencyReq) {
        if(currencyRepo.existsById(currencyReq.getCode())) {
            throw new RuntimeException("Currency already exist");
        }

        Currency currency = new Currency();
        currency.setCode(currencyReq.getCode());
        currency.setCodeZh(currencyReq.getCodeZh());

        currencyRepo.save(currency);
    }

    @Override
    public CurrencyRes update(CurrencyReq currencyReq) {
        if(!currencyRepo.existsById(currencyReq.getCode())) {
            throw new RuntimeException("Currency not exist");
        }

        Currency currency = new Currency();
        currency.setCode(currencyReq.getCode());
        currency.setCodeZh(currencyReq.getCodeZh());

        currency = currencyRepo.save(currency);

        CurrencyRes currencyRes = new CurrencyRes();
        currencyRes.setCode(currency.getCode());
        currencyRes.setCodeZh(currency.getCodeZh());
        return currencyRes;
    }

    @Override
    public void delete(String code) {
        currencyRepo.deleteById(code);
    }

    @Override
    public MergeCurrencyRes mergeCurrencyDataFromCoinDeskAPI() {
        CoinDeskRes coinDeskRes = cathayBankAPIComponent.callCoinDesk();
        Map<String,CoinDeskBpi> bpi = coinDeskRes.getBpi();
        List<Currency> currencyList = currencyRepo.findByCodeIn(bpi.keySet());

        MergeCurrencyRes mergeCurrencyRes = new MergeCurrencyRes();
        List<CurrencyDetail> currencyDetailList = new ArrayList<>();

        for(Currency currency : currencyList) {
            CurrencyDetail currencyDetail = new CurrencyDetail();
            CoinDeskBpi coinDeskBpi = bpi.get(currency.getCode());

            currencyDetail.setCode(coinDeskBpi.getCode());
            currencyDetail.setCodeZh(currency.getCodeZh());
            currencyDetail.setRate(coinDeskBpi.getRate());

            currencyDetailList.add(currencyDetail);
            bpi.remove(currency.getCode());
        }

        for(CoinDeskBpi coinDeskBpi : bpi.values()) {
            CurrencyDetail currencyDetail = new CurrencyDetail();

            currencyDetail.setCode(coinDeskBpi.getCode());
            currencyDetail.setRate(coinDeskBpi.getRate());

            currencyDetailList.add(currencyDetail);
        }
        LocalDateTime isoDate = LocalDateTime.parse(coinDeskRes.getTime().getUpdatedISO(), DateTimeFormatter.ISO_DATE_TIME);
        mergeCurrencyRes.setUpdatedTime(isoDate.format(Constant.FORMATTER_YYYY_MM_DD_HH_MM_SS));
        mergeCurrencyRes.setCurrencyDetailList(currencyDetailList);

        return mergeCurrencyRes;
    }
}
