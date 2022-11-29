package com.cathaybank.coindesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MergeCurrencyRes {
    @JsonProperty("updatedTime")
    private String updatedTime;
    @JsonProperty("currencyDetails")
    private List<CurrencyDetail> currencyDetailList;
}
