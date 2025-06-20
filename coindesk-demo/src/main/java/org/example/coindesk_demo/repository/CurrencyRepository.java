package org.example.coindesk_demo.repository;

import org.example.coindesk_demo.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String>{
}
