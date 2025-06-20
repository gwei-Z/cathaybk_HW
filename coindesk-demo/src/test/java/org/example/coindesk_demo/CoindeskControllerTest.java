package org.example.coindesk_demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoindeskControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setup() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void testRawEndpoint() throws Exception {
        String json = "{\"time\":{\"updatedISO\":\"2025-06-19T12:00:00+00:00\"},\"bpi\":{}}";
        mockServer.expect(requestTo("https://kengp3.github.io/blog/coindesk.json"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        mvc.perform(get("/api/coindesk/raw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.time.updatedISO").value("2025-06-19T12:00:00+00:00"));
    }

    @Test
    void testTransformedEndpoint() throws Exception {
        String json = "{\n" +
                "  \"time\": { \"updatedISO\": \"2025-06-19T12:00:00+00:00\" },\n" +
                "  \"bpi\": { \"USD\": { \"rate_float\": 65000.5 } }\n" +
                "}";
        mockServer.expect(requestTo("https://kengp3.github.io/blog/coindesk.json"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        mvc.perform(get("/api/coindesk"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updateTime").value("2025/06/19 12:00:00"))
                .andExpect(jsonPath("$.currencies[0].code").value("USD"));
    }
}