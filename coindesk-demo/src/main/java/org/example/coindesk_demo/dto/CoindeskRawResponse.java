package org.example.coindesk_demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindeskRawResponse {
    private Time time;
    private Map<String, BpiInfo> bpi;

    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }
    public Map<String, BpiInfo> getBpi() { return bpi; }
    public void setBpi(Map<String, BpiInfo> bpi) { this.bpi = bpi; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Time {
        @JsonProperty("updatedISO")
        private String updatedISO;
        public String getUpdatedISO() { return updatedISO; }
        public void setUpdatedISO(String updatedISO) { this.updatedISO = updatedISO; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BpiInfo {
        @JsonProperty("rate_float")
        private double rateFloat;
        public double getRateFloat() { return rateFloat; }
        public void setRateFloat(double rateFloat) { this.rateFloat = rateFloat; }
    }
}
