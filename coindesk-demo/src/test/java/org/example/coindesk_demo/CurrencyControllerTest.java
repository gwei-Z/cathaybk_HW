package org.example.coindesk_demo;

import org.example.coindesk_demo.entity.Currency;
import org.example.coindesk_demo.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CurrencyRepository repo;

    @BeforeEach
    void setup() {
        repo.deleteAll();
        repo.save(new Currency("USD","美元"));
    }

    @Test
    void testGetAll() throws Exception {
        mvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("USD"));
    }

    @Test
    void testCrudFlow() throws Exception {
        // Create
        mvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"code\":\"JPY\",\"chineseName\":\"日圓\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("JPY"));

        // Read
        mvc.perform(get("/api/currencies/JPY"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chineseName").value("日圓"));

        // Update
        mvc.perform(put("/api/currencies/JPY")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"chineseName\":\"日本圓\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chineseName").value("日本圓"));

        // Delete
        mvc.perform(delete("/api/currencies/JPY"))
                .andExpect(status().isNoContent());
    }
}