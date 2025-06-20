package org.example.coindesk_demo;

import org.example.coindesk_demo.entity.Currency;
import org.example.coindesk_demo.repository.CurrencyRepository;
import org.example.coindesk_demo.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CurrencyServiceTest {

    @Autowired
    private CurrencyRepository repo;

    @Test
    void testCrud() {
        CurrencyService svc = new CurrencyService(repo);

        // Create
        Currency c = new Currency("TWD", "新台幣");
        svc.save(c);
        assertThat(svc.findByCode("TWD")).isPresent();

        // Read
        Optional<Currency> opt = svc.findByCode("TWD");
        assertThat(opt.get().getChineseName()).isEqualTo("新台幣");

        // Update
        c.setChineseName("台幣");
        svc.save(c);
        assertThat(svc.findByCode("TWD").get().getChineseName()).isEqualTo("台幣");

        // Delete
        svc.deleteByCode("TWD");
        assertThat(svc.findByCode("TWD")).isNotPresent();
    }
}