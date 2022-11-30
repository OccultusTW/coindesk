package com.cathaybank.coindesk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CurrencyReq {
    @JsonProperty("code")
    @NotBlank
    @Size(max = 30)
    private String code;
    @JsonProperty("codeZh")
    @Size(max = 255)
    private String codeZh;
}
