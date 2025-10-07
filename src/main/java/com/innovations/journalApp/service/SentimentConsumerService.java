package com.innovations.journalApp.service;

import com.innovations.journalApp.model.SentimentData;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SentimentConsumerService {

    private final KafkaTemplate<String, SentimentData> consumer;

    private final EmailService emailService;

    @KafkaListener(topics = "sentimentAnalysisTopic", groupId = "weekly-sentiment-group")
    public void consume(SentimentData sentimentData) {
        sendMail(sentimentData);
    }


    public void sendMail(SentimentData sentimentData) {
        System.out.printf("SentimentData: %s\n", sentimentData);
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment Analysis For Last 7 Days", sentimentData.getSentiment());
    }
}
