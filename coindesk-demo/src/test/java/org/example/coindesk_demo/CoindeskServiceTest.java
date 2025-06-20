package org.example.coindesk_demo;

import org.example.coindesk_demo.dto.CoindeskTransformedResponse;
import org.example.coindesk_demo.dto.CurrencyRateDTO;
import org.example.coindesk_demo.entity.Currency;
import org.example.coindesk_demo.repository.CurrencyRepository;
import org.example.coindesk_demo.service.CoindeskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
class CoindeskServiceTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private CoindeskService coindeskService;

    @Mock
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        // 使用真实 RestTemplate 并绑定 MockRestServiceServer
        this.restTemplate = new RestTemplate();
        this.mockServer  = MockRestServiceServer.bindTo(restTemplate).build();
        // 将 mock 的 repo 注入到 service
        this.coindeskService = new CoindeskService(restTemplate, currencyRepository);
    }

    @Test
    void testTransform() {
        // 准备 stub：当查询 USD 时返回中文名 “美元”
        when(currencyRepository.findById("USD"))
                .thenReturn(Optional.of(new Currency("USD", "美元")));

        // 模拟 Coindesk 原始 JSON 响应
        String json = "{"
                + "\"time\":{\"updatedISO\":\"2025-06-19T12:00:00+00:00\"},"
                + "\"bpi\":{\"USD\":{\"rate_float\":65000.5}}"
                + "}";

        mockServer.expect(requestTo("https://kengp3.github.io/blog/coindesk.json"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        // 调用 transform() 方法
        CoindeskTransformedResponse result = coindeskService.transform();

        // 核心断言：更新时间格式正确
        assertThat(result.getUpdateTime()).isEqualTo("2025/06/19 12:00:00");

        // 核心断言：币种列表包含一条 USD
        assertThat(result.getCurrencies()).hasSize(1);
        CurrencyRateDTO dto = result.getCurrencies().get(0);
        assertThat(dto.getCode()).isEqualTo("USD");
        assertThat(dto.getChineseName()).isEqualTo("美元");
        assertThat(dto.getRate()).isEqualTo(65000.5);

        // 验证 Mock Server 被调用
        mockServer.verify();
    }
}
