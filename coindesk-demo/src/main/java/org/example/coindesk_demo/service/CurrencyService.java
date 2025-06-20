package org.example.coindesk_demo.service;


import org.example.coindesk_demo.entity.Currency;
import org.example.coindesk_demo.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CurrencyService {
    private final CurrencyRepository repo;

    public CurrencyService(CurrencyRepository repo) {
        this.repo = repo;
    }

    public List<Currency> findAll() {
        return repo.findAll();
    }

    public Optional<Currency> findByCode(String code) {
        return repo.findById(code);
    }

    public Currency save(Currency c) {
        return repo.save(c);
    }

    public void deleteByCode(String code) {
        repo.deleteById(code);
    }
}
