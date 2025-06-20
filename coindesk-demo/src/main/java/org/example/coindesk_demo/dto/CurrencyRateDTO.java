package org.example.coindesk_demo.dto;

public class CurrencyRateDTO {
    private String code;
    private String chineseName;
    private double rate;

    public CurrencyRateDTO() {}

    public CurrencyRateDTO(String code,
                           String chineseName,
                           double rate) {
        this.code = code;
        this.chineseName = chineseName;
        this.rate = rate;
    }

    public String getCode() { return code; }
    public String getChineseName() { return chineseName; }
    public double getRate() { return rate; }
}
