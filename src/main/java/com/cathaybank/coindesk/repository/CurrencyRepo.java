package com.cathaybank.coindesk.repository;

import com.cathaybank.coindesk.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CurrencyRepo extends JpaRepository<Currency,String> {
    List<Currency> findByCodeIn(Set<String> codes);
}
