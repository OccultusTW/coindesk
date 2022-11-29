package com.cathaybank.coindesk.component;

import com.cathaybank.coindesk.dto.telegram.CoinDeskRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class CathayBankAPIComponent {
    private RestTemplate restTemplate;

    @Value("${CoinDeskAPIUrl}")
    private String coinDeskAPIUrl;

    public CathayBankAPIComponent(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CoinDeskRes callCoinDesk() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(coinDeskAPIUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<CoinDeskRes>() {}).getBody();
    }
}
