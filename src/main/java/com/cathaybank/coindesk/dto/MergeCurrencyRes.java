package com.cathaybank.coindesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MergeCurrencyRes {
    @JsonProperty("updatedTime")
    private String updatedTime;
    @JsonProperty("currencyDetails")
    private List<CurrencyDetail> currencyDetailList;
}
