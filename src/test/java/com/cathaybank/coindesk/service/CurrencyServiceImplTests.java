package com.cathaybank.coindesk.service;

import com.cathaybank.coindesk.component.CathayBankAPIComponent;
import com.cathaybank.coindesk.dto.CurrencyRes;
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

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CurrencyServiceImplTests {
    @InjectMocks
    private CurrencyServiceImpl currencyServiceImpl;
    @Mock
    private CurrencyRepo currencyRepo;
    @Mock
    private CathayBankAPIComponent cathayBankAPIComponent;

    @Test
    public void query_notFoundId_throwException() throws Exception {
        when(currencyRepo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(RuntimeException.class, ()-> currencyServiceImpl.query(""));
    }
}
