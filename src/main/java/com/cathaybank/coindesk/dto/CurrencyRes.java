package com.cathaybank.coindesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyRes {
    @JsonProperty("code")
    private String code;
    @JsonProperty("codeZh")
    private String codeZh;
}
