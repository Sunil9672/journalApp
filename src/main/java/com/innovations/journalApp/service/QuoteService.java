package com.innovations.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovations.journalApp.constants.Placeholders;
import com.innovations.journalApp.cache.AppCache;
import com.innovations.journalApp.entity.QuoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteService {

    @Value("${quote.service.mode}")
    private String mode;

    private final RedisService redisService;
    private final RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;
    @Autowired
    private ObjectMapper objectMapper;

    public String getQuoteOfTheDay() {
        try {

            String dateStr = LocalDateTime.now().toLocalDate().toString() + "cometo dubai";

            System.out.println("Quote of day: " + dateStr);

            QuoteResponse quoteResponse = redisService.get(dateStr, QuoteResponse.class);

            System.out.println("Quote resp: " + quoteResponse);

            if (Objects.isNull(quoteResponse)) {
                String apiUrl = appCache.getCache().get(AppCache.Keys.quote_api.toString()).replace(Placeholders.MODE, mode);

                ResponseEntity<List<QuoteResponse>> response = restTemplate.exchange(
                        apiUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<QuoteResponse>>() {
                        }
                );

                if (!response.getStatusCode().is2xxSuccessful() || Objects.requireNonNull(response.getBody()).isEmpty()) {
                    return null;
                }

                QuoteResponse returnedQuoteResponse = response.getBody().get(0);
                String quoteStr = objectMapper.writeValueAsString(returnedQuoteResponse);

                redisService.set(dateStr, quoteStr);

                System.out.println("Quotestring : " + quoteStr);

                return returnedQuoteResponse.getQuote();
            } else {
                System.out.println("Strored quote : " + quoteResponse.getQuote());
                return quoteResponse.getQuote();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
