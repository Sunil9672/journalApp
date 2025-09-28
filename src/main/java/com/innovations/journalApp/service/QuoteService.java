package com.innovations.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovations.journalApp.entity.QuoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteService {

    private String apiUrl = "https://api.viewbits.com/v1/zenquotes?mode=today";

    private final RestTemplate restTemplate;

    public String getQuoteOfTheDay(){

        ResponseEntity<List<QuoteResponse>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<QuoteResponse>>() {}
        );

        if(!response.getStatusCode().is2xxSuccessful() || response.getBody().size()==0) {
            return null;
        }

        return response.getBody().get(0).getQuote();

    }
}
