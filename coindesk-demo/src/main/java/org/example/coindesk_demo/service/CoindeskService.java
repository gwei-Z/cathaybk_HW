package org.example.coindesk_demo.service;

import org.example.coindesk_demo.dto.CoindeskRawResponse;
import org.example.coindesk_demo.dto.CoindeskTransformedResponse;
import org.example.coindesk_demo.dto.CurrencyRateDTO;
import org.example.coindesk_demo.entity.Currency;
import org.example.coindesk_demo.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CoindeskService {
    private static final String URL =
            "https://kengp3.github.io/blog/coindesk.json";
    private static final DateTimeFormatter IN_FMT  =
            DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter OUT_FMT =
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepo;

    public CoindeskService(RestTemplate restTemplate,
                           CurrencyRepository currencyRepo) {
        this.restTemplate  = restTemplate;
        this.currencyRepo = currencyRepo;
    }

    public CoindeskRawResponse fetchRaw() {
        return restTemplate.getForObject(URL, CoindeskRawResponse.class);
    }

    public CoindeskTransformedResponse transform() {
        CoindeskRawResponse raw = fetchRaw();
        OffsetDateTime odt = OffsetDateTime
                .parse(raw.getTime().getUpdatedISO(), IN_FMT);
        String formattedTime = odt.format(OUT_FMT);

        List<CurrencyRateDTO> list = new ArrayList<>();
        for (Map.Entry<String, CoindeskRawResponse.BpiInfo> e
                : raw.getBpi().entrySet()) {
            String code = e.getKey();
            double rate = e.getValue().getRateFloat();
            String cn = currencyRepo.findById(code)
                    .map(Currency::getChineseName)
                    .orElse("未知");
            list.add(new CurrencyRateDTO(code, cn, rate));
        }
        return new CoindeskTransformedResponse(formattedTime, list);
    }
}
