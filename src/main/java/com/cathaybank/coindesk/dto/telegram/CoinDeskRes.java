package com.cathaybank.coindesk.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CoinDeskRes {
    @JsonProperty("time")
    private CoinDeskUpdatedTime time;
    @JsonProperty("disclaimer")
    private String disclaimer;
    @JsonProperty("chartName")
    private String chartName;
    @JsonProperty("bpi")
    private Map<String,CoinDeskBpi> bpi;
}
