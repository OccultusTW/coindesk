package com.cathaybank.coindesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyRes {
    @JsonProperty("code")
    private String code;
    @JsonProperty("codeZh")
    private String codeZh;
}
