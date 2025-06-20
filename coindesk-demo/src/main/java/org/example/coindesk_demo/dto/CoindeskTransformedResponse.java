package org.example.coindesk_demo.dto;

import java.util.List;

public class CoindeskTransformedResponse {
    private String updateTime;
    private List<CurrencyRateDTO> currencies;

    public CoindeskTransformedResponse() {}

    public CoindeskTransformedResponse(String updateTime,
                                       List<CurrencyRateDTO> currencies) {
        this.updateTime = updateTime;
        this.currencies = currencies;
    }

    public String getUpdateTime() { return updateTime; }
    public List<CurrencyRateDTO> getCurrencies() { return currencies; }
}
