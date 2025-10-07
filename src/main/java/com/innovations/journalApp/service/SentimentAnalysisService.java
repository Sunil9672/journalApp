package com.innovations.journalApp.service;

import org.springframework.stereotype.Component;

@Component
public class SentimentAnalysisService {

    public String sentimentAnalysis(String string){
        return "Happy";
    }
}
